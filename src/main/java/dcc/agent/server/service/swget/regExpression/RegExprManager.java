package dcc.agent.server.service.swget.regExpression;

import dcc.agent.server.service.swget.regExpression.automaton.RegularExpression;
import dcc.agent.server.service.swget.regExpression.automaton.State;
import dcc.agent.server.service.swget.regExpression.automaton.StateMachine;
import dcc.agent.server.service.swget.regExpression.automaton.Transition;
import dcc.agent.server.service.swget.regExpression.parser.ParseException;
import dcc.agent.server.service.swget.regExpression.parser.TokenMgrError;

import java.util.HashSet;
import java.util.LinkedList;

public class RegExprManager {

	public static final String noTriggers = "[*]";
	public static final String noActions = "ACT[*]";

	String regExpr = null;

	RegularExpression regularExpression = new RegularExpression();
	public StateMachine pMachine = null;

	public RegExprManager() {
	}

	public RegExprManager(String regExpr) throws ParseException, TokenMgrError {
		this.regExpr = regExpr;
		regularExpression.setRegularExpression(regExpr);
	}

	public String buildAutomaton() {
		pMachine = regularExpression.generateStateMachine();

		return regularExpression.getLanguageDescription();
	}

	public LinkedList<String> getActions(State state) {

		return pMachine.getActions(state);

	}

	public HashSet<State> getEpsilonReachableStates(State state) {
		return pMachine.getNullReachableStates(state);

	}

	public LinkedList<State> getIncomingStateTriggersAndActions(State state) {

		return pMachine.getIncomingStateTriggersAndActions(state);

	}

	public LinkedList<Transition> getIncomingTransition(State state) {
		return pMachine.getIncomingTransition(state);

	}

	public LinkedList<Transition> getOutgoingTransition(State state) {
		return pMachine.getOutgoingTransition(state);

	}

	public State getInitial() {
		if (pMachine == null)
			return null;
		return pMachine.getInitial();
	}

	public State getNext(State state, String pred) {
		if (state == null)
			return null;
		return pMachine.getNext(state, pred);
	}

	public RegularExpression getRegularExpression() {
		return regularExpression;
	}

	public State getState(String id) {
		return pMachine.getState(id);
	}

	public LinkedList<String> getTriggers(State state) {

		return pMachine.getTriggers(state);

	}

	public boolean hasOutgoingTransition(State state) {
		return pMachine.hasOutgoingTransitions(state);

	}

	public boolean isFinal(State state) {

		return pMachine.isFinal(state);

	}

}