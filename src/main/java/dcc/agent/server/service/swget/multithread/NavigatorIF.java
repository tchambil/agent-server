package dcc.agent.server.service.swget.multithread;

import com.hp.hpl.jena.rdf.model.Model;
import dcc.agent.server.service.swget.regExpression.RegExprManager;
import dcc.agent.server.service.swget.regExpression.automaton.State;
import dcc.agent.server.service.swget.utils.NavigationHistory;
import dcc.agent.server.service.swget.utils.URIData;

import java.util.HashSet;

public interface NavigatorIF {

	/**
	 * Marks this link as visited
	 * 
	 *
	 */
	public int getBUDGET();

	public HashSet<String> getAlreadyPrintedResults();

	public void addToAlreadyPrintedResult(String res);

	public void decBUDGET();

	public void incToDeref(int value);

	public boolean isNotSaveModels();
    public boolean EnableEndPoints(String s);
	public void decToDeref();

	public int getToDeref();
public String getCommand();
	public void addVisitedModel(String link, Model model);

	public void addBlankModel(String link, Model model);

	public void addErrorUri(String link);

	public boolean isErrorUri(String link);
    public boolean isValidURL(String url);
	public Model getGraph();

	public Model getExistingModel(String link);

	public Model getExistingBlankModel(String link);

	public NetworkManager getNetWorkManager();

	public RegExprManager getRegExpManager();

	public NavigationHistory getNavigationHistory();

	public void incrementDerefCOunt();

	public int getDerefCount();

	public void addResult(String res);

	public int getActiveThreadCount();

	public void shutdown() ;

	public void addToNavHistory(URIData current_lookup_pair);

	public boolean containsNavigationalHistory(String uri, State state);

	public void addVisitedTriple(String sbj, String pred, String obj);
    public void printOnGUI(String s);
	/**
	 * Places the link in the queue
	 * 
	 * @param link
	 * @throws Exception
	 */

	public void queueLink(URIData link) throws Exception;
    public void printAutomata();
	/**
	 * Returns the number of visited links
	 * 
	 * @return
	 */
	public int size();

}