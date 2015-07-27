package dcc.agent.server.service.swget.multithread;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;
import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.config.AgentServerProperties;
import dcc.agent.server.service.delegate.AgentDelegate;
import dcc.agent.server.service.script.runtime.ScriptState;
import dcc.agent.server.service.swget.console.arguments.CommandOption;
import dcc.agent.server.service.swget.console.arguments.NumericalOption;
import dcc.agent.server.service.swget.console.arguments.StringOption;
import dcc.agent.server.service.swget.nmpg.NamedMultiPointedGraph;
import dcc.agent.server.service.swget.regExpression.RegExprManager;
import dcc.agent.server.service.swget.regExpression.automaton.State;
import dcc.agent.server.service.swget.regExpression.automaton.StateMachine;
import dcc.agent.server.service.swget.regExpression.automaton.Transition;
import dcc.agent.server.service.swget.regExpression.parser.ParseException;
import dcc.agent.server.service.swget.regExpression.parser.TokenMgrError;
import dcc.agent.server.service.swget.utils.Constants;
import dcc.agent.server.service.swget.utils.NavigationHistory;
import dcc.agent.server.service.swget.utils.URIData;
import dcc.agent.server.service.swget.utils.URIPair;
import org.json.JSONException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class Navigator implements NavigatorIF {

    // visited links
    private final Map<String, URIData> visitedLinks = Collections
            .synchronizedMap(new HashMap<String, URIData>());

    // visited models
    private final Map<String, Model> uri_model_table = Collections
            .synchronizedMap(new HashMap<String, Model>());

    private final Map<String, Model> blank_node_models = Collections
            .synchronizedMap(new HashMap<String, Model>());


    private ExecutorService execService;
    private RegExprManager reg_expr_manager;
    private RegExprManager reg_expr_manager0;
    private NetworkManager net_manager;
    private HashSet<String> final_results;
    private HashSet<String> already_printed;
    private HashSet<String> error_uris;
    private ArrayList List_Query;
    private NavigationHistory nav_history;
    private NamedMultiPointedGraph partial_graph;
    private Model result_model;
    private ThreadPoolExecutor threadGroup;
    private int deref_count = 0;
    private int to_deref = 1;
    static int count;
    private String jobId;
    private String email;
    private AgentServerProperties config;
    /*
     * The following variables handle the options
     */
    private ScriptState scriptState;
    private int BUDGET = -1;
    private int NUM_THREAD = 5;// default value
    private boolean VERBOSE = false;
    private boolean WRITE_FILE = false;
    private boolean RECONSTRUCT = false;
    private boolean STREAM_OUTPUT = false;
    private boolean NOT_SAVE_MODELS = false;
    private String OUTPUT_FILE = "output_swget.rdf";
    private String DIRECTORY = config.DEFAULT_PERSISTENT_STORE_DIR + File.separator;
    private String INPUT_REGEX = "";
    private String SEED = "";
    private String COMMENT = "";

    private String enableEndpoint=null;
    private Boolean enableAgent=false;
    public Navigator() {
        net_manager = new NetworkManager(this);
        final_results = new HashSet<String>();
        nav_history = new NavigationHistory();
        partial_graph = new NamedMultiPointedGraph();
        error_uris = new HashSet<String>();

    }

    @Override
    public synchronized void addBlankModel(String link, Model model) {
        blank_node_models.put(link, model);

    }

    public synchronized void addResult(String res) {

        constructNewQuery(res);
        writeResult(res);

        final_results.add(res);
        partial_graph.setAsEndingNode(res);
    }


    public synchronized void addToNavHistory(URIData current_lookup_pair) {
        nav_history.add(current_lookup_pair.getUrl().toString(),
                current_lookup_pair.getState(),
                reg_expr_manager.isFinal(current_lookup_pair.getState()));
    }

    @Override
    public synchronized void addVisitedModel(String link, Model model) {
        uri_model_table.put(link, model);

    }

    @Override
    public synchronized void addVisitedTriple(String sbj, String pred,
                                              String obj) {
        partial_graph.addEdge(sbj, obj, pred);

    }

    @Override
    public void printAutomata() {

        StateMachine automaton = reg_expr_manager0.pMachine;


        String f_automaton = this.OUTPUT_FILE.substring(0,
                OUTPUT_FILE.lastIndexOf("persistent_store"))
                + "_automaton.txt";

        PrintWriter automps = null;

        try {
            automps = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(f_automaton), "UTF-8"));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        automps.println(SEED + " " + Constants.REG_EXPR_PREDICATE + " "
                + this.INPUT_REGEX);
        automps.println(COMMENT);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        automaton.printMachine(ps);
        String content = null;
        try {
            content = baos.toString("UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        automps.println(content);
        automps.flush();
        automps.close();
    }

    public void writeResult(String rest) {
        NavigationHistory nh = nav_history;

        String f_automaton = this.OUTPUT_FILE.substring(0,
                OUTPUT_FILE.lastIndexOf("persistent_store"))
                + "_automatoninitial.txt";

        String f_history = this.OUTPUT_FILE.substring(0,
                OUTPUT_FILE.lastIndexOf("persistent_store"))
                + rest.substring(rest.lastIndexOf("/"), rest.length()) + ".txt";

        StateMachine automaton = reg_expr_manager.pMachine;
        PrintWriter history = null;
        try {
            history = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(f_history), "UTF-8"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HashSet<String> states = new HashSet<String>();

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(f_automaton));
            String line = br.readLine();
            line = br.readLine();
            line = br.readLine();
            StringTokenizer st = new StringTokenizer(line, " )(");
            st.nextToken();
            st.nextToken();
            while (st.hasMoreTokens()) {
                states.add(st.nextToken());
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String state : states) {
            State st = automaton.getState(state);
            history.print(st.getID());
            HashSet<String> uris = nh.containsState(st);

            history.print(uris.toString() + "\n");
        }

        history.flush();
        history.close();

    }

    private void closeExecution() {
        /*
		 * 
		 */
        if (RECONSTRUCT) {
            reconstruct();

        }

        if (WRITE_FILE)

        {
			/*
			 * VISITED SEMANTICS
			 */
			/*
			 * Model result = ModelFactory.createDefaultModel(); for(Model
			 * m:uri_model_table.values()) result=result.union(m); try {
			 * result.write(new OutputStreamWriter(new FileOutputStream(
			 * OUTPUT_FILE), "UTF-8")); } catch (IOException e) {
			 * e.printStackTrace();
			 */
        }

        NavigationHistory nh = nav_history;
        StateMachine automaton = reg_expr_manager.pMachine;

        String f_automaton = this.OUTPUT_FILE.substring(0,
                OUTPUT_FILE.lastIndexOf("persistent_store"))
                + "_automatoninitial.txt";

        PrintWriter automps = null;

        try {
            automps = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(f_automaton), "UTF-8"));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        automps.println(SEED + " " + Constants.REG_EXPR_PREDICATE + " "
                + this.INPUT_REGEX);
        automps.println(COMMENT);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        automaton.printMachine(ps);
        String content = null;
        try {
            content = baos.toString("UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        automps.println(content);
        automps.flush();
        automps.close();

        String f_history = this.OUTPUT_FILE.substring(0,
                OUTPUT_FILE.lastIndexOf("persistent_store"))
                + "_hystory.txt";

        PrintWriter history = null;
        try {
            history = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(f_history), "UTF-8"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HashSet<String> states = new HashSet<String>();

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(f_automaton));
            String line = br.readLine();
            line = br.readLine();
            line = br.readLine();
            StringTokenizer st = new StringTokenizer(line, " )(");
            st.nextToken();
            st.nextToken();
            while (st.hasMoreTokens()) {
                states.add(st.nextToken());
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String state : states) {
            State st = automaton.getState(state);
            history.print(st.getID());
            HashSet<String> uris = nh.containsState(st);

            history.print(uris.toString() + "\n");
        }

        history.flush();
        history.close();

    }

    public synchronized boolean containsNavigationalHistory(String uri,
                                                            State state) {
        return nav_history.urlReachebleAtState(uri, state);
    }

    public synchronized void decToDeref() {
        to_deref--;
    }

    public synchronized int getActiveThreadCount() {
        return threadGroup.getActiveCount();
    }

    /**
     * COMMAND OPTIONS
     */
    private CommandOption[] getArguments(LinkedList<String> args) {

        if (args == null) {
            return null;
        }

        LinkedList<CommandOption> arguments = new LinkedList<CommandOption>();

        try {
            String[] argmts = new String[args.size()];
            args.toArray(argmts);

            arguments.add(new StringOption(Constants.SEED, argmts[0]));
            this.SEED = argmts[0];
            partial_graph.setAsStartingNode(SEED);

            for (int argp = 1; argp < argmts.length; argp++) {
                if (argmts[argp].equals(Constants.BUDGET)) {
                    argp++;
                    if (argp < argmts.length) {
                        this.BUDGET = Integer.parseInt(argmts[argp].trim());

                        arguments.add(new NumericalOption(Constants.BUDGET,
                                BUDGET));

                        STREAM_OUTPUT = true;
                        already_printed = new HashSet<String>();

                    }

                } else if (argmts[argp].equals(Constants.WRITE_OUTPUT_FILE)) {
                    argp++;
                    if (argp < argmts.length) {
                        this.OUTPUT_FILE = argmts[argp];
                        if (!OUTPUT_FILE.contains("."))
                            OUTPUT_FILE = OUTPUT_FILE + ".rdf";
                        System.out.println(OUTPUT_FILE);
                        WRITE_FILE = true;

                        arguments.add(new StringOption(
                                Constants.WRITE_OUTPUT_FILE, OUTPUT_FILE));
                    }
                } else if (argmts[argp].equals(Constants.DIRECTORY)) {
                    argp++;
                    if (argp < argmts.length) {
                        this.DIRECTORY = argmts[argp];
                        if (!DIRECTORY.endsWith(File.separator))
                            DIRECTORY = DIRECTORY + File.separator;

                        arguments.add(new StringOption(Constants.DIRECTORY,
                                DIRECTORY));
                    }

                } else if (argmts[argp].equals(Constants.STREAM_OUTPUT)) {
                    STREAM_OUTPUT = true;
                    already_printed = new HashSet<String>();

                } else if (argmts[argp].equals(Constants.NUM_THREADS)) {
                    argp++;
                    if (argp < argmts.length)

                        this.NUM_THREAD = Integer.parseInt(argmts[argp].trim());

                    arguments.add(new NumericalOption(Constants.NUM_THREADS,
                            NUM_THREAD));
                }

                // FILTER PREDICATES
                else if (argmts[argp].equals(Constants.REG_EXPR_PREDICATE)) {
                    argp++;

                    this.INPUT_REGEX = "";

                    while (argp < argmts.length
                            && argmts[argp].charAt(0) != '-') {
                        INPUT_REGEX = INPUT_REGEX + argmts[argp] + " ";
                        argp++;
                    }
                    argp--;

                    for (int i = 0; i < Constants.namespaces_keys.length; i++) {

                        INPUT_REGEX = INPUT_REGEX.replaceAll(
                                Constants.namespaces_keys[i],
                                Constants.namespaces_values[i]);

                    }

                    arguments.add(new StringOption(
                            Constants.REG_EXPR_PREDICATE, INPUT_REGEX));

                } else if (argmts[argp].equals(Constants.VERBOSE)) {

                    arguments.add(new StringOption(Constants.VERBOSE,
                            Constants.VERBOSE));

                    this.VERBOSE = true;

                } else if (argmts[argp].equals(Constants.RECONSTRUCT)) {

                    arguments.add(new StringOption(Constants.RECONSTRUCT,
                            Constants.RECONSTRUCT));

                    this.RECONSTRUCT = true;

                } else if (argmts[argp].equals(Constants.NOT_SAVE_MODELS)) {

                    arguments.add(new StringOption(Constants.NOT_SAVE_MODELS,
                            Constants.NOT_SAVE_MODELS));

                    this.NOT_SAVE_MODELS = true;

                } else if (argmts[argp].equals(Constants.NOT_SAVE_MODELS)) {

                    arguments.add(new StringOption(Constants.NOT_SAVE_MODELS,
                            Constants.NOT_SAVE_MODELS));
                }

            }

            CommandOption[] ret = new CommandOption[arguments.size()];
            arguments.toArray(ret);

            this.OUTPUT_FILE = this.DIRECTORY + this.OUTPUT_FILE;
            return ret;

        } catch (ArrayIndexOutOfBoundsException e) {

        }

        return null;

    }

    public synchronized int getDerefCount() {
        return deref_count;
    }

    @Override
    public synchronized Model getExistingBlankModel(String link) {
        return blank_node_models.get(link);
    }

    @Override
    public synchronized Model getExistingModel(String link) {
        return uri_model_table.get(link);
    }

    public synchronized HashSet<String> getFinalResults() {
        return final_results;
    }

    @Override
    public Model getGraph() {
        return result_model;
    }

    @Override
    public NavigationHistory getNavigationHistory() {
        return nav_history;
    }

    public synchronized NetworkManager getNetWorkManager() {
        return net_manager;
    }

    public synchronized RegExprManager getRegExpManager() {
        return reg_expr_manager;
    }

    @Override
    public synchronized int getToDeref() {
        // TODO Auto-generated method stub
        return to_deref;
    }

    /**
     * Tokenizes the input from the console
     *
     * @param input
     * @return
     */
    private LinkedList<String> getTokenizedInput(String input) {
        String[] tokens = null;
        try {
            tokens = input.split(" ");
        } catch (NullPointerException e) {

        }
        LinkedList<String> args = new LinkedList<String>();

        for (int i = 0; i < tokens.length; i++) {
            if (!tokens[i].equals("")) {
                args.add(tokens[i]);
            }
        }
        return args;
    }

    /**
     * Increment the number of dereferenced URIs
     */
    public synchronized void incrementDerefCOunt() {
        deref_count++;
    }

    /**
     * Increment the number of URIs still to be dereferenced
     */
    public synchronized void incToDeref(int value) {
        // TODO Auto-generated method stub

        to_deref = to_deref + value;

    }

    /**
     * Starts the evaluation of the expression
     *
     * @throws Exception
     */
    public void navigate() throws Exception {
        printRunningConfiguration();

        startNewThread(new URIData(SEED, reg_expr_manager.pMachine.getInitial()));
    }

    @Override
    public void queueLink(URIData link) throws Exception {
        //count++;
        //System.out.print("Cantidad:"+count);
        startNewThread(link);
    }

    /**
     * Returns the value of the curent budget (in terms of dereferecing
     * operations)
     */
    public synchronized int getBUDGET() {
        return BUDGET;
    }

    /**
     * Decrements the budget
     */
    public synchronized void decBUDGET() {
        BUDGET--;
    }

    /**
     * Prints the configuration for the current command
     */
    private void printRunningConfiguration() {
        System.out
                .println("------------swget current configuration------------");
        System.out.println("#max threads (-t num)=" + NUM_THREAD);
        System.out.println("streamOutput (-stream)=" + STREAM_OUTPUT);
        if (BUDGET == -1)
            System.out.println("budget #max deref (-b num)=unbounded");
        else
            System.out.println("budget #max deref operations (-b num)="
                    + BUDGET);
        System.out.println("reconstructGraph (-recon)=" + RECONSTRUCT);
        System.out.println("--------------------------------------");


    }

    /**
     * Reconstruct the graph according to the successful semantics
     */
    private void reconstruct() {

        HashSet<URIPair> m_r = new HashSet<URIPair>();
        result_model = ModelFactory.createDefaultModel();

        LinkedList<URIPair> toCheck = nav_history.getFinals();

        while (toCheck.size() > 0) {
            URIPair up = toCheck.remove(0);
            if (!m_r.contains(up)) {
                m_r.add(up);

                LinkedList<Transition> trans = reg_expr_manager
                        .getIncomingTransition(up.getState());

                for (Transition t : trans) {
                    if (t.getSymbol().getLanguageDescription().startsWith("[") || t.getSymbol().getLanguageDescription().startsWith("ACT[") || t.getSymbol().isNullSymbol()) {
                        toCheck.add(new URIPair(up.getUrl(), t.getStartState()));
                    } else if (t.getSymbol().isWildcardSymbol()) {
                        Hashtable<String, HashSet<String>> nodes = partial_graph.getIncomingNodes(up.getUrl());
                        if (nodes != null)
                            for (String label : nodes.keySet()) {
                                for (String node : nodes.get(label)) {
                                    if (nav_history.urlReachebleAtState(node,
                                            t.getStartState())) {
                                        toCheck.add(new URIPair(node, t
                                                .getStartState()));
                                        Statement s = new StatementImpl(
                                                new ResourceImpl(node),
                                                new PropertyImpl(label),
                                                new ResourceImpl(up.getUrl()));
                                        result_model.add(s);
                                    }
                                }

                            }
                    } else {
                        String pred = t.getSymbol().getLanguageDescription().substring(
                                1,
                                t.getSymbol().getLanguageDescription()
                                        .length() - 1);
                        HashSet<String> nodes = partial_graph.getIncomingNodes(
                                up.getUrl(), pred);
                        if (nodes != null)
                            for (String node : nodes) {
                                if (nav_history.urlReachebleAtState(node,
                                        t.getStartState())) {
                                    toCheck.add(new URIPair(node, t
                                            .getStartState()));
                                    Statement s = new StatementImpl(
                                            new ResourceImpl(node),
                                            new PropertyImpl(pred),
                                            new ResourceImpl(up.getUrl()));
                                    result_model.add(s);
                                }

                            }
                    }
                }

            }
        }

        for (String s : partial_graph.getEndingNodes()) {
            Statement st = new StatementImpl(new ResourceImpl(s),
                    new PropertyImpl(
                            "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                    new ResourceImpl("http://nmpg.org/ending"));
            result_model.add(st);
        }

        for (String s : partial_graph.getStartingNodes()) {
            Statement st = new StatementImpl(new ResourceImpl(s),
                    new PropertyImpl(
                            "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                    new ResourceImpl("http://nmpg.org/starting"));
            result_model.add(st);
        }
    }

    private void EnableEndPoint(URIData uriData) {
        {
            String current_URI = uriData.getUrl();
            if (current_URI.toString().contains("#")) {
                current_URI = current_URI.substring(0, current_URI.indexOf("#"));
            }
            try {
                URL tempUrl = new URL(current_URI);
                String host = tempUrl.getHost();
                for (Endpoint endpoint : Endpoint.values()) {
                    if (endpoint.getGraph().equals(host)) {
                        enableEndpoint = endpoint.getHost();
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }

    private void EnableAgent(URIData uriData) {
         String address = scriptState.agentInstance.addresses.toString();
        if (enableEndpoint != null) {
            if (enableEndpoint.equals(address)) {
                enableAgent = true;
            }
        }
    }

    /**
     * @param command
     * @throws ParseException
     */
    private String reconstructcommand(String command) throws ParseException {

        CommandOption[] optionss = getArguments(getTokenizedInput(command));
        reg_expr_manager0 = new RegExprManager(INPUT_REGEX);
        reg_expr_manager0.buildAutomaton();

        StateMachine automaton = reg_expr_manager0.pMachine;
        printAutomata();

        List_Query = automaton.Query();
        if (List_Query.size() > 0) {
            String new_command = optionss[0].toString() + " -p " + List_Query.get(0).toString();
            return new_command;
        }
        return null;
    }

    private void constructNewQuery(String res) {
        String newcommand = null;
        for (int i = 0; i < List_Query.size(); i++) {

        }
        if (List_Query.size() == 0) {
            newcommand = null;
        } else if (List_Query.size() == 1) {
            newcommand = res + " -p " + List_Query.get(0).toString();
        } else if (List_Query.size() == 2) {
            newcommand = res + " -p " + List_Query.get(1).toString();
          } else if (List_Query.size() == 3) {
            newcommand = res + " -p " + List_Query.get(1).toString() + "/" + List_Query.get(2).toString();
        } else if (List_Query.size() == 4) {
            newcommand = res + " -p " + List_Query.get(1).toString() + "/" + List_Query.get(2).toString() + "/" + List_Query.get(3).toString();
        } else if (List_Query.size() == 5) {
            newcommand = res + " -p " + List_Query.get(1).toString() + "/" + List_Query.get(2).toString() + "/" + List_Query.get(3).toString() + "/" + List_Query.get(4).toString();
        } else if (List_Query.size() == 6) {
            newcommand = res + " -p " + List_Query.get(1).toString() + "/" + List_Query.get(2).toString() + "/" + List_Query.get(3).toString() + "/" + List_Query.get(4).toString() + "/" + List_Query.get(5).toString();
        } else if (List_Query.size() == 7) {
            newcommand = res + " -p " + List_Query.get(1).toString() + "/" + List_Query.get(2).toString() + "/" + List_Query.get(3).toString() + "/" + List_Query.get(4).toString() + "/" + List_Query.get(5).toString() + "/" + List_Query.get(6).toString();
        }
        try {
            if (newcommand != null) {

                AgentInstance agentS = scriptState.agentServer.getAgentInstance(scriptState.agentInstance.name,true);

                AgentInstance agentR=scriptState.agentServer.getAgentInstanceAddress(res,"");

                AgentDelegate.doNautiLOD(scriptState, agentS, agentR, newcommand);
            }
        } catch (AgentServerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point of the program
     *
     * @param command
     * @param comment
     * @return
     * @throws ParseException
     * @throws TokenMgrError
     */
    public String[] runCommand(ScriptState scriptState, String command, String comment)
            throws ParseException, TokenMgrError {
        this.scriptState = scriptState;

        String new_command = reconstructcommand(command);
        if (new_command == null) {
            new_command = command;
        }
        LinkFinderThread.resetTimer();
        String[] results = new String[2];
        CommandOption[] options = getArguments(getTokenizedInput(new_command));
        this.COMMENT = comment;

        reg_expr_manager = new RegExprManager(INPUT_REGEX);
        reg_expr_manager.buildAutomaton();

        /**
         * UGLY; must be done here because the variable num_thread must be first
         * read
         */

        threadGroup = new ThreadPoolExecutor(NUM_THREAD, NUM_THREAD, 5000,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        execService = threadGroup;

        /**
         * END UGLY
         */

        try {
            navigate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    public void setGUI(String jobId, String email) {

        this.jobId = jobId;
        this.email = email;
    }

    /**
     * Terminates the execution of the evaluation. Only called by the last
     * thread
     */

    public synchronized void shutdown() {

        execService.shutdownNow();
        closeExecution();
        Collection<String> r = getFinalResults();
        System.out.println("#res=" + r.size() + " #deref=" + getDerefCount());


    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    /**
     * Start a new thread with a new URI to handle
     *
     * @param link
     * @throws Exception
     */
    private void startNewThread(URIData link) throws Exception {
        try {
            if (BUDGET != -1)
                execService.execute(new LinkFinderThread(link, this, STREAM_OUTPUT, true));

            execService.execute(new LinkFinderThread(link, this, STREAM_OUTPUT,
                    false));
        } catch (RejectedExecutionException e) {

            // closeExecution();
            System.out.println("ee" + e);
            System.exit(0);
        }

    }

    /**
     * Returns the set of already printed results inc ase of results streaming
     */
    public synchronized HashSet<String> getAlreadyPrintedResults() {
        return already_printed;
    }

    /**
     * Add a URIs to the set of already printed results
     */
    public synchronized void addToAlreadyPrintedResult(String res) {

        already_printed.add(res);
    }

    /**
     * Add to the set of URIs that caused problems
     */
    public synchronized void addErrorUri(String link) {
        this.error_uris.add(link);
    }

    /**
     * Checks is a URI caused problems
     */
    public synchronized boolean isErrorUri(String link) {
        return error_uris.contains(link);

    }


    @Override
    public boolean isNotSaveModels() {
        return NOT_SAVE_MODELS;
    }


    public synchronized void printOnGUI(String s) {
        //System.out.println(s);

        final_results.add(s);

    }
}