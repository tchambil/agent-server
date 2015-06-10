package dcc.agent.server.service.swget.multithread;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;
import dcc.agent.server.service.swget.actions.sendemail.SendEmailAction;
import dcc.agent.server.service.swget.regExpression.RegExprManager;
import dcc.agent.server.service.swget.regExpression.automaton.State;
import dcc.agent.server.service.swget.regExpression.automaton.Transition;
import dcc.agent.server.service.swget.utils.Constants;
import dcc.agent.server.service.swget.utils.URIData;
import org.apache.commons.lang3.StringUtils;

import javax.mail.MessagingException;
import java.util.*;

public class LinkFinderThread implements Runnable {

	private String current_automaton_state = "";
	private Hashtable<String, LinkedList<String>> action_results;
	private ArrayList<URIData> extracted;
	private LinkedList<URIData> to_expand;
	private URIData starting_uri;
	private NavigatorIF NautiLODManager;
	private boolean fire_action;
	private boolean stream_res = false;
	private boolean withBudget = false;

	private static long t0 = System.currentTimeMillis();
	
	public static void resetTimer(){
		t0 = System.currentTimeMillis();
	}

	public LinkFinderThread(URIData seed, NavigatorIF handler,
			boolean stream_res, boolean withBudget) {

		this.action_results = new Hashtable<String, LinkedList<String>>();
		this.to_expand = new LinkedList<URIData>();
		this.extracted = new ArrayList<URIData>();
		this.starting_uri = seed;
		this.NautiLODManager = handler;
		this.stream_res = stream_res;
		this.withBudget = withBudget;
	}

	/**
	 * This method implements the navigation in a recursive way. Each thread is
	 * assigned a starting_uri. Then it launches a new thread for each "valid"
	 * outgoing link obtained from the datasource associated to starting_uri The
	 * program terminates when no more threads are active and all the links to
	 * be expanded have been expanded
	 * 
	 * @param starting_uri
	 */
	private void expandExpression(URIData starting_uri) {

		NautiLODManager.decToDeref();

		String current_URI = starting_uri.getUrl().toString();

		// TRANSFORM TO 303 URI
		if (current_URI.toString().contains("#")) {
			current_URI = current_URI.substring(0, current_URI.indexOf("#"));
		}

		if (!NautiLODManager.containsNavigationalHistory(starting_uri.getUrl(),	starting_uri.getState())&& !NautiLODManager.isErrorUri(current_URI)) {
			if (new ResourceImpl(current_URI).isResource()) {
				try {
					HashSet<State> hs = NautiLODManager.getRegExpManager().getEpsilonReachableStates(starting_uri.getState());

					NautiLODManager.printOnGUI("Handling URI_1 "+ starting_uri.getUrl() + "\n");
					for (State s : hs) {
						to_expand.add(new URIData(starting_uri.getUrl(),starting_uri.getDepth(), s, starting_uri.getProvenanceAuthority(), starting_uri.getTypeOfLink()));
					}

					if (NautiLODManager.getRegExpManager()
							.hasOutgoingTransition(starting_uri.getState())) {

						extracted = extractLinks(starting_uri);

					}

					for (URIData current : extracted) {

						if (current != null
								&& !NautiLODManager.containsNavigationalHistory(starting_uri.getUrl().toString(),starting_uri.getState())) {

							to_expand.add(current);

						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			NautiLODManager.addToNavHistory(starting_uri);

			/**
			 * RESULTS
			 */
			if (NautiLODManager.getRegExpManager().isFinal(
					starting_uri.getState())) {
				String res = starting_uri.getUrl().toString();

				// if streaming is active
				if (stream_res) {
					if (!NautiLODManager.getAlreadyPrintedResults().contains(
							res)) {
						System.out.println("@res" + ": " + res);

						NautiLODManager.addToAlreadyPrintedResult(res);
					}

				}

				NautiLODManager.addResult(res);

			}

			NautiLODManager.incToDeref(to_expand.size());

			for (URIData uri : to_expand) { 
				try {
					NautiLODManager.queueLink(uri);
					System.out.println("Resultado : " + uri.getUrl());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		/**
		 * Termination condition
		 */
		if (NautiLODManager.getActiveThreadCount() == 1
				&& NautiLODManager.getToDeref() <= 0) {

			System.out.println("\nTotal time="
					+ ((System.currentTimeMillis() - t0)) + " ms");
			NautiLODManager.printOnGUI("\nTotal time="
					+ ((System.currentTimeMillis() - t0)) + " ms\n");

			NautiLODManager.shutdown();

		}

	}

	/**
	 * Extract outgoing links according to starting_uri (the URI associated to
	 * the current thread)
	 * 
	 * @param uri_to_expand
	 * @return
	 */
	public ArrayList<URIData> extractLinks(URIData uri_to_expand) {

		ArrayList<URIData> links_to_expand = new ArrayList<URIData>();

		String current_URI = uri_to_expand.getUrl();

		// TRANSFORM TO 303 URI
		if (current_URI.toString().contains("#")) {
			current_URI = current_URI.substring(0, current_URI.indexOf("#"));

		}

		// *** HANDLE ACTIONS

		LinkedList<String> actions = NautiLODManager.getRegExpManager()
				.getActions(uri_to_expand.getState());

		links_to_expand.addAll(handleActions(actions, uri_to_expand));

		// *** HANDLE TESTS

		LinkedList<String> tests = NautiLODManager.getRegExpManager()
				.getTriggers(uri_to_expand.getState());

		links_to_expand.addAll(handleTests(tests, uri_to_expand));

		// *** HANDLE STANDARD TRANSITIONS

		Model current_model = NautiLODManager.getExistingModel(current_URI);

		if (current_model == null) {

			if (uri_to_expand.getUrl().endsWith("_BLANK")) {
				current_model = NautiLODManager
						.getExistingBlankModel(current_URI);
			} else {

				current_model = NautiLODManager.getNetWorkManager().getModel(
						current_URI);

				// if the size is zero there if no point in DEREF it another
				// time
				if (current_model.size() != 0) {
					// HERE WE ARE SURE THAT WE HAVE A NEW MODEL MEANING A NEW
					// DEREF
					// HAS BEEN DONE
					if (withBudget) {
						NautiLODManager.decBUDGET();
						if (NautiLODManager.getBUDGET() == 0) {
							System.err
									.println("Stopping execution: budget of deref operations is over");
							NautiLODManager.shutdown();
						}

					}

					//
					if (!NautiLODManager.isNotSaveModels())
						NautiLODManager.addVisitedModel(current_URI,
								current_model);
					NautiLODManager.incrementDerefCOunt();
					handleBlanks(current_model, current_URI);
				} else {
					NautiLODManager.addErrorUri(current_URI);
				}
			}
		}

		// //ALL the following is needed only if the model is not empty!

		if (current_model != null && current_model.size() != 0) {

			LinkedList<Transition> trans = NautiLODManager.getRegExpManager()
					.getOutgoingTransition(uri_to_expand.getState());

			String pred;
			ResultSet results = null;
			for (Transition t : trans) {

				pred = t.getSymbol().getLanguageDescription();

				String queryString = "SELECT ?x WHERE " + "{ <"
						+ uri_to_expand.getUrl().toString() + "> " + pred
						+ "  ?x.}";

				Query query = QueryFactory.create(queryString);

				QueryExecution qexec = QueryExecutionFactory.create(query,
						current_model);

				results = qexec.execSelect();

				for (; results.hasNext();) {
					QuerySolution soln = results.next();

					RDFNode in_node = soln.get("x"); // "x" is a variable in the
														// query
					URIData new_pair = null;
					String u = null;

					// If you need to test the thing returned
					if (in_node.isResource()) {
						Resource res_node = (Resource) in_node;

						if (res_node.isAnon()) {

							u = current_URI + "_" + res_node.toString()
									+ "_BLANK";
						} else {
							u = res_node.getURI();
						}
						new_pair = new URIData(u, uri_to_expand.getDepth(),
								NautiLODManager.getRegExpManager().getNext(
										uri_to_expand.getState(), pred),
								uri_to_expand.getProvenanceAuthority(),
								uri_to_expand.getTypeOfLink());

						links_to_expand.add(new_pair);
						NautiLODManager.addVisitedTriple(uri_to_expand.getUrl()
								.toString(), pred.substring(1,
								pred.length() - 1), u);
					}
					if (in_node.isLiteral()) {
						new_pair = new URIData(in_node.toString(),
								uri_to_expand.getDepth(),
								NautiLODManager.getRegExpManager().getNext(
										uri_to_expand.getState(), pred),
								uri_to_expand.getProvenanceAuthority(),
								uri_to_expand.getTypeOfLink());

						links_to_expand.add(new_pair);
						NautiLODManager.addVisitedTriple(uri_to_expand.getUrl()
								.toString(), pred.substring(1,
								pred.length() - 1), in_node.toString());
					}

				}

				queryString = "SELECT ?x WHERE { ?x " + pred + " <"
						+ uri_to_expand.getUrl().toString() + "> .}";

				query = QueryFactory.create(queryString);

				qexec = QueryExecutionFactory.create(query, current_model);

				results = qexec.execSelect();

				for (; results.hasNext();) {
					QuerySolution soln = results.next();

					RDFNode out_node = soln.get("x"); // "x" is a variable in
														// the
														// query
					URIData new_pair = null;
					String u;

					// If you need to test the thing returned
					if (out_node.isResource()) {
						Resource res_node = (Resource) out_node;

						if (res_node.isAnon()) {

							u = current_URI + "_" + res_node.toString()
									+ "_BLANK";
						} else {
							u = res_node.getURI();
						}

						new_pair = new URIData(u, uri_to_expand.getDepth(),
								NautiLODManager.getRegExpManager().getNext(
										uri_to_expand.getState(), pred),
								uri_to_expand.getProvenanceAuthority(),
								uri_to_expand.getTypeOfLink());

						links_to_expand.add(new_pair);
						NautiLODManager.addVisitedTriple(uri_to_expand.getUrl()
								.toString(), pred.substring(1,
								pred.length() - 1), u);
					}
					if (out_node.isLiteral()) {
						new_pair = new URIData(out_node.toString(),
								uri_to_expand.getDepth(),
								NautiLODManager.getRegExpManager().getNext(
										uri_to_expand.getState(), pred),
								uri_to_expand.getProvenanceAuthority(),
								uri_to_expand.getTypeOfLink());
						NautiLODManager.addVisitedTriple(uri_to_expand.getUrl()
								.toString(), pred.substring(1,
								pred.length() - 1), out_node.toString());
					}

				}
			}

		}

		return links_to_expand;
	}

	/**
	 * Handles blank nodes
	 * 
	 * @param model
	 * @param uri
	 */
	private void handleBlanks(Model model, String uri) {

		if (model != null) {
			// list the statements in the graph
			StmtIterator iter = model.listStatements();

			// iterates over the statements
			while (iter.hasNext()) {

				Statement stmt = iter.nextStatement(); // get next statement
				Resource subject = stmt.getSubject(); // get the subject
				RDFNode object = stmt.getObject(); // get the object

				String u = "";

				// OBJECT
				if (object.isResource()) {

					// create the resource
					Resource objectR = ResourceFactory.createResource(object
							.toString());

					if (object.isAnon()) {

						u = uri + "_" + objectR.toString() + "_BLANK";

						Resource temp = ResourceFactory.createResource(u);

						Model m = ModelFactory.createDefaultModel();

						StmtIterator it1 = model.listStatements(
								(Resource) object, null, (RDFNode) null);

						Statement temp_stat;

						while (it1.hasNext()) {
							temp_stat = it1.next();

							m.add(new StatementImpl(temp, temp_stat
									.getPredicate(), temp_stat.getObject()));
						}

						StmtIterator it2 = model.listStatements(null, null,
								(RDFNode) object);

						while (it2.hasNext()) {
							temp_stat = it2.next();

							m.add(new StatementImpl(temp_stat.getSubject(),
									temp_stat.getPredicate(), temp));
						}
						NautiLODManager.addBlankModel(u, m);

					}// end if blank node
				}

				if (subject.isAnon()) {

					u = uri + "_" + subject.getLocalName() + "_BLANK";

					Resource temp = ResourceFactory.createResource(u);

					Model m = ModelFactory.createDefaultModel();

					StmtIterator it1 = model.listStatements((Resource) subject,
							null, (RDFNode) null);

					Statement temp_stat;

					while (it1.hasNext()) {
						temp_stat = it1.next();

						m.add(new StatementImpl(temp, temp_stat.getPredicate(),
								temp_stat.getObject()));
					}

					StmtIterator it2 = model.listStatements(null, null,
							(RDFNode) subject);

					while (it2.hasNext()) {
						temp_stat = it2.next();

						m.add(new StatementImpl(temp_stat.getSubject(),
								temp_stat.getPredicate(), temp));
					}

					NautiLODManager.addBlankModel(u, m);

				}
			}
		}

	}

	/**
	 * Handles tests, that is, ASK queries on the current Jena Model (i.e., the
	 * data source associated to starting_uri)
	 * 
	 * @param tests
	 * @param uri_to_expand
	 * @return
	 */
	private ArrayList<URIData> handleTests(LinkedList<String> tests,
			URIData uri_to_expand) {

		ArrayList<URIData> res = new ArrayList<URIData>();

		for (String query : tests) {
			URIData new_pair;

			if (!query.equalsIgnoreCase(RegExprManager.noTriggers)) {
				String new_q = query.substring(1, query.length() - 1);

				boolean resq = false;

				try {

					Query q = QueryFactory.create(new_q);

					QueryExecution qexec = QueryExecutionFactory.create(q,
							NautiLODManager.getExistingModel(uri_to_expand
									.getUrl().toString()));

					resq = qexec.execAsk();

					qexec.close();

				} catch (Exception e)

				{
					e.printStackTrace();
				}

				if (resq) {

					new_pair = new URIData(uri_to_expand.getUrl(),
							uri_to_expand.getDepth(), NautiLODManager
									.getRegExpManager().getNext(
											uri_to_expand.getState(), query),
							uri_to_expand.getProvenanceAuthority(),
							uri_to_expand.getTypeOfLink());

					res.add(new_pair);

				}

			}

		}

		return res;
	}

	/**
	 * Handles actions
	 * 
	 * @param actions
	 * @param uri_to_expand
	 * @return
	 */
	private ArrayList<URIData> handleActions(LinkedList<String> actions,
			URIData uri_to_expand) {
		ArrayList<URIData> res = new ArrayList<URIData>();

		if (actions.size() > 1
				|| (actions.size() == 1 && !actions.get(0).equals(
						RegExprManager.noActions))) {

			if (current_automaton_state.length() == 0) {
				current_automaton_state = uri_to_expand.getState().getID();
			} else {
				if (Integer.parseInt(uri_to_expand.getState().getID()) > Integer
						.parseInt(current_automaton_state)) {
					current_automaton_state = "";
					fire_action = true;
				}
			}

			for (String action : actions) {
				URIData new_pair;

				if (!action.equalsIgnoreCase(RegExprManager.noActions)) {
					String command = action.substring(action.indexOf("::") + 2,
							action.length() - 1);

					/**
					 * BEGIN: HANDLING ACTION RESULTS
					 */
					LinkedList<String> partial_action_res;
					// insert in the hashtable of results
					if (action_results.get(action) == null) {
						partial_action_res = new LinkedList<String>();
						action_results.put(action, partial_action_res);

					} else {
						partial_action_res = action_results.get(action);

					}
					/**
					 * END: HANDLING ACTION RESULTS
					 */

					/**
					 * Checks the KIND of command
					 */
					if (command.startsWith(Constants.EMAIL_COMMAND)) {
						String cmd = action.substring(action.indexOf("::") + 2,
								action.length() - 1);
						String action_test = action.substring(4,
								action.indexOf("::"));
						String address = StringUtils.remove(cmd,
								Constants.EMAIL_COMMAND);
						address = address.substring(0, address.indexOf(")"));

						ResultSet query_result = null;

						QueryExecution qexec = null;

						Query q = QueryFactory.create(action_test);
						try {
							qexec = QueryExecutionFactory.create(q,
									NautiLODManager
											.getExistingModel(uri_to_expand
													.getUrl().toString()));

							query_result = qexec.execSelect();

							qexec.close();

						} finally {
							qexec.close();
						}

						for (; query_result.hasNext();) {
							partial_action_res.add(query_result.next()
									.toString());

						}

						if (fire_action) {

							SendEmailAction send_email = SendEmailAction
									.getInstance();

							try {
								send_email.sendEmail(address,
										Constants.SWGET_EMAIL_SUBJECT,
										NautiLODManager.getRegExpManager()
												.getRegularExpression()
												.getLanguageDescription(),
										action, partial_action_res,
										//"nautilod.swget@gmail.com");
										"antony_epist@hotmail.com");
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							action_results.remove(action);

							fire_action = false;

						}

					} else {
						// HANDLE Action active but the command DOES NOT
						// EXIST");
					}

					new_pair = new URIData(uri_to_expand.getUrl(),
							uri_to_expand.getDepth(), NautiLODManager
									.getRegExpManager().getNext(
											uri_to_expand.getState(), action),
							uri_to_expand.getProvenanceAuthority(),
							uri_to_expand.getTypeOfLink());

					res.add(new_pair);
				}
			}
		} else {
			Set<String> keys = action_results.keySet();
			for (String key : keys) {
				String cmd = key.substring(key.indexOf("::") + 2,
						key.length() - 1);
				if (cmd.startsWith(Constants.EMAIL_COMMAND)) {

					String address = StringUtils.remove(cmd,
							Constants.EMAIL_COMMAND);
					address = address.substring(0, address.indexOf(")"));
					SendEmailAction send_email = SendEmailAction.getInstance();

					try {
						send_email.sendEmail(address,
								Constants.SWGET_EMAIL_SUBJECT, NautiLODManager
										.getRegExpManager()
										.getRegularExpression()
										.getLanguageDescription(), key,
								action_results.get(key),
								Constants.SWGET_EMAIL_FROM);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// action_results.remove(key);

					fire_action = false;

				}

			}
			action_results = new Hashtable<String, LinkedList<String>>();
		}

		return res;
	}

	@Override
	public void run() {
		expandExpression(starting_uri);
	}
}