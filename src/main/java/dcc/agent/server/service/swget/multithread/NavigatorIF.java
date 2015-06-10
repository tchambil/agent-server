package dcc.agent.server.service.swget.multithread;

import dcc.agent.server.service.swget.gui.GUI;
import dcc.agent.server.service.swget.regExpression.RegExprManager;
import dcc.agent.server.service.swget.regExpression.automaton.State;
import dcc.agent.server.service.swget.utils.NavigationHistory;
import dcc.agent.server.service.swget.utils.URIData;

import java.util.HashSet;

import com.hp.hpl.jena.rdf.model.Model;

public interface NavigatorIF {

	/**
	 * Marks this link as visited
	 * 
	 * @param link
	 */
	public int getBUDGET();

	public HashSet<String> getAlreadyPrintedResults();

	public void addToAlreadyPrintedResult(String res);

	public void decBUDGET();

	public void incToDeref(int value);

	public boolean isNotSaveModels();

	public void decToDeref();

	public int getToDeref();

	public void addVisitedModel(String link, Model model);

	public void addBlankModel(String link, Model model);

	public void addErrorUri(String link);

	public boolean isErrorUri(String link);

	public void setGUI(GUI gui, String jobId, String email);
	
	public void printOnGUI(String s);

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

	public void shutdown();

	public void addToNavHistory(URIData current_lookup_pair);

	public boolean containsNavigationalHistory(String uri, State state);

	public void addVisitedTriple(String sbj, String pred, String obj);

	/**
	 * Places the link in the queue
	 * 
	 * @param link
	 * @throws Exception
	 */
	public void queueLink(URIData link) throws Exception;

	/**
	 * Returns the number of visited links
	 * 
	 * @return
	 */
	public int size();

}