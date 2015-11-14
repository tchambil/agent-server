package dcc.agent.server.service.swget.regExpression.automaton;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class StateMachine {
    public static StateMachine createIntersection(StateMachine m1,
                                                  StateMachine m2) {
        StateMachine rc = new StateMachine();
        {
            Element pElem = rc.m_states.elementAt(0);
            rc.m_states.removeElement(pElem);
        }

        int totalStateCount = m1.m_states.getSize() + m2.m_states.getSize();
        State[] pStates = new State[totalStateCount];

        // The states in m1 and m2 should be sequential starting from 0

        for (int i = 0; i < m1.m_states.getSize(); i++) {
            pStates[i] = new State(i);
            rc.m_states.addElement(pStates[i]);
        }

        // Create a map of m2 states from (0,m2.Q.count-1) to (m1.Q.count+1,
        // m1.Q.count+m2.Q.count)
        int[] m2StateMap = new int[m2.m_states.getSize()];
        for (int i = 0; i < m2.m_states.getSize(); i++) {
            m2StateMap[i] = i + m1.m_states.getSize();
            pStates[i + m1.m_states.getSize()] = new State(i
                    + m1.m_states.getSize());
            rc.m_states.addElement(pStates[i + m1.m_states.getSize()]);
        }

        // Add mapped m2.A to rc.A
        for (int i = 0; i < m2.m_acceptingStates.getSize(); i++) {
            State pAccepting = (State) m2.m_acceptingStates.elementAt(i);
            int index = m2StateMap[Integer.valueOf(pAccepting.getID())
                    .intValue()];
            rc.m_acceptingStates.addElement(new State(pStates[index]));
        }

        // Add union of m1.alphabet and m2.alphabet to rc.alphabet
        /*
		 * rc.m_alphabet.Union( m1.m_alphabet ); rc.m_alphabet.Union(
		 * m2.m_alphabet );
		 */
        // Add mapped m1.transitions and mapped m2.transitions to rc.
        for (int i = 0; i < m1.m_stateTransitions.getSize(); i++) {
            Transition pTrans = (Transition) m1.m_stateTransitions.elementAt(i);
            int start = Integer.valueOf(pTrans.getStartState().getID())
                    .intValue();
            int end = Integer.valueOf(pTrans.getFinalState().getID())
                    .intValue();
            rc.addTransition(new Transition(pStates[start], pStates[end],
                    pTrans.getSymbol()));
        }
        for (int i = 0; i < m2.m_stateTransitions.getSize(); i++) {
            Transition pTrans = (Transition) m2.m_stateTransitions.elementAt(i);
            int start = m2StateMap[Integer.valueOf(
                    pTrans.getStartState().getID()).intValue()];
            int end = m2StateMap[Integer
                    .valueOf(pTrans.getFinalState().getID()).intValue()];
            rc.addTransition(new Transition(pStates[start], pStates[end],
                    pTrans.getSymbol()));
        }

        // Set rc.q0 to m1.q0
        int q0_1 = Integer.valueOf(m1.m_pStartingState.getID()).intValue();
        rc.m_pStartingState = pStates[q0_1];

        // Create null transitions from m1.A to mapped m2.q0.
        RegExpPred NULLSymbol = new RegExpPred();
        int q0_2 = m2StateMap[Integer.valueOf(m2.m_pStartingState.getID())
                .intValue()];
        for (int i = 0; i < m1.m_acceptingStates.getSize(); i++) {
            State pAccepting = (State) m1.m_acceptingStates.elementAt(i);
            int index = Integer.valueOf(pAccepting.getID()).intValue();
            rc.addTransition(new Transition(pStates[index], pStates[q0_2],
                    NULLSymbol));
        }

        pStates = null;

        m2StateMap = null;

        return rc;
    }

    public static StateMachine createPossiblyMissing(StateMachine m1) {
        StateMachine rc = new StateMachine();
        {
            Element pElem = rc.m_states.elementAt(0);
            rc.m_states.removeElement(pElem);
        }

        // The states in m1 should be sequential starting from 0

        int totalStateCount = 1 + m1.m_states.getSize();
        State[] pStates = new State[totalStateCount];
        pStates[0] = new State(0);
        rc.m_states.addElement(pStates[0]);

        // Create a map of m1 states from (0,m1.Q.count-1) to (1,m1.Q.count)
        int[] m1StateMap = new int[m1.m_states.getSize()];
        for (int i = 0; i < m1.m_states.getSize(); i++) {
            m1StateMap[i] = i + 1;
            pStates[i + 1] = new State(i + 1);
            rc.m_states.addElement(pStates[i + 1]);
        }

        // Add m1.transitions to rc.
        for (int i = 0; i < m1.m_stateTransitions.getSize(); i++) {
            Transition pTrans = (Transition) m1.m_stateTransitions.elementAt(i);
            int start = m1StateMap[Integer.valueOf(
                    pTrans.getStartState().getID()).intValue()];
            int end = m1StateMap[Integer
                    .valueOf(pTrans.getFinalState().getID()).intValue()];
            rc.addTransition(new Transition(pStates[start], pStates[end],
                    pTrans.getSymbol()));
        }

        // Set rc.q0 to m1.q0
        rc.m_pStartingState = pStates[0];

        // Add new q0 to A and m1.A to rc.A
        rc.m_acceptingStates.addElement(new State(rc.m_pStartingState));
        for (int i = 0; i < m1.m_acceptingStates.getSize(); i++) {
            State pAccepting = (State) m1.m_acceptingStates.elementAt(i);
            int index = m1StateMap[Integer.valueOf(pAccepting.getID())
                    .intValue()];
            rc.m_acceptingStates.addElement(new State(pStates[index]));
        }

        // Create null transitions from q0 to m1.q0
        RegExpPred NULLSymbol = new RegExpPred();

        int q0_1 = m1StateMap[Integer.valueOf(m1.m_pStartingState.getID())
                .intValue()];
        rc.addTransition(new Transition(pStates[0], pStates[q0_1], NULLSymbol));

        return rc;

    }

    public static StateMachine createRepeat(StateMachine m1, boolean zero) {
        StateMachine rc = new StateMachine();
        {
            Element pElem = rc.m_states.elementAt(0);
            rc.m_states.removeElement(pElem);
        }

        // The states in m1 should be sequential starting from 0

        int totalStateCount = 1 + m1.m_states.getSize();
        State[] pStates = new State[totalStateCount];
        pStates[0] = new State(0);
        rc.m_states.addElement(pStates[0]);

        // Create a map of m1 states from (0,m1.Q.count-1) to (1,m1.Q.count)
        int[] m1StateMap = new int[m1.m_states.getSize()];
        for (int i = 0; i < m1.m_states.getSize(); i++) {
            m1StateMap[i] = i + 1;
            pStates[i + 1] = new State(i + 1);
            rc.m_states.addElement(pStates[i + 1]);
        }

        // Add m1.transitions to rc.
        for (int i = 0; i < m1.m_stateTransitions.getSize(); i++) {
            Transition pTrans = (Transition) m1.m_stateTransitions.elementAt(i);
            int start = m1StateMap[Integer.valueOf(
                    pTrans.getStartState().getID()).intValue()];
            int end = m1StateMap[Integer
                    .valueOf(pTrans.getFinalState().getID()).intValue()];
            rc.addTransition(new Transition(pStates[start], pStates[end],
                    pTrans.getSymbol()));
        }

        // Set rc.q0 to m1.q0
        rc.m_pStartingState = pStates[0];

        // if zero repeat Add new q0 to A else add m1.A to rc.A
        if (zero)
            rc.m_acceptingStates.addElement(new State(rc.m_pStartingState));
        else
            for (int i = 0; i < m1.m_acceptingStates.getSize(); i++) {
                State pAccepting = (State) m1.m_acceptingStates.elementAt(i);
                int index = m1StateMap[Integer.valueOf(pAccepting.getID())
                        .intValue()];
                rc.m_acceptingStates.addElement(new State(pStates[index]));
            }

        // Create null transitions from m1.A to q0 and q0 to m1.q0
        RegExpPred NULLSymbol = new RegExpPred();
        for (int i = 0; i < m1.m_acceptingStates.getSize(); i++) {
            State pAccepting = (State) m1.m_acceptingStates.elementAt(i);
            int index = m1StateMap[Integer.valueOf(pAccepting.getID())
                    .intValue()];
            rc.addTransition(new Transition(pStates[index], pStates[0],
                    NULLSymbol));
        }

        int q0_1 = m1StateMap[Integer.valueOf(m1.m_pStartingState.getID())
                .intValue()];
        rc.addTransition(new Transition(pStates[0], pStates[q0_1], NULLSymbol));

        m1StateMap = null;

        pStates = null;

        return rc;
    }

    public static StateMachine createUnion(StateMachine m1, StateMachine m2) {

        // The states in m1 and m2 should be sequential starting from 0
        StateMachine rc = new StateMachine();
        {
            Element pElem = rc.m_states.elementAt(0);
            rc.m_states.removeElement(pElem);
        }

        int totalStateCount = 1 + m1.m_states.getSize() + m2.m_states.getSize();
        State[] pStates = new State[totalStateCount];
        pStates[0] = new State(0);
        rc.m_states.addElement(pStates[0]);

        // Create a map of m1 states from (0,m1.Q.count-1) to (1,m1.Q.count)
        int[] m1StateMap = new int[m1.m_states.getSize()];
        for (int i = 0; i < m1.m_states.getSize(); i++) {
            m1StateMap[i] = i + 1;
            pStates[i + 1] = new State(i + 1);
            rc.m_states.addElement(pStates[i + 1]);
        }

        // Create a map of m2 states from (0,m2.Q.count-1) to (m1.Q.count+1,
        // m1.Q.count+m2.Q.count)
        int[] m2StateMap = new int[m2.m_states.getSize()];
        for (int i = 0; i < m2.m_states.getSize(); i++) {
            m2StateMap[i] = i + m1.m_states.getSize() + 1;
            pStates[i + m1.m_states.getSize() + 1] = new State(i
                    + m1.m_states.getSize() + 1);
            rc.m_states.addElement(pStates[i + m1.m_states.getSize() + 1]);
        }

        // Add mapped m1.A and mapped m2.A to rc.A
        for (int i = 0; i < m1.m_acceptingStates.getSize(); i++) {
            State pAccepting = (State) m1.m_acceptingStates.elementAt(i);
            int index = m1StateMap[Integer.valueOf(pAccepting.getID())
                    .intValue()];

            rc.m_acceptingStates.addElement(new State(pStates[index]));
        }
        for (int i = 0; i < m2.m_acceptingStates.getSize(); i++) {
            State pAccepting = (State) m2.m_acceptingStates.elementAt(i);
            int index = m2StateMap[Integer.valueOf(pAccepting.getID())
                    .intValue()];
            rc.m_acceptingStates.addElement(new State(pStates[index]));
        }

        // Add mapped m1.transitions and mapped m2.transitions to rc.
        for (int i = 0; i < m1.m_stateTransitions.getSize(); i++) {
            Transition pTrans = (Transition) m1.m_stateTransitions.elementAt(i);
            int start = m1StateMap[Integer.valueOf(
                    pTrans.getStartState().getID()).intValue()];
            int end = m1StateMap[Integer
                    .valueOf(pTrans.getFinalState().getID()).intValue()];
            rc.addTransition(new Transition(pStates[start], pStates[end],
                    pTrans.getSymbol()));
        }
        for (int i = 0; i < m2.m_stateTransitions.getSize(); i++) {
            Transition pTrans = (Transition) m2.m_stateTransitions.elementAt(i);
            int start = m2StateMap[Integer.valueOf(
                    pTrans.getStartState().getID()).intValue()];
            int end = m2StateMap[Integer
                    .valueOf(pTrans.getFinalState().getID()).intValue()];
            rc.addTransition(new Transition(pStates[start], pStates[end],
                    pTrans.getSymbol()));
        }

        // Set rc.q0 to a new state "0".
        rc.m_pStartingState = pStates[0];

        // Create null transitions from state 0 to states "1" and
        // "m1.Q.count+1".
        RegExpPred NULLSymbol = new RegExpPred();
        int q0_1 = m1StateMap[Integer.valueOf(m1.m_pStartingState.getID())
                .intValue()];
        int q0_2 = m2StateMap[Integer.valueOf(m2.m_pStartingState.getID())
                .intValue()];
        rc.addTransition(new Transition(pStates[0], pStates[q0_1], NULLSymbol));
        rc.addTransition(new Transition(pStates[0], pStates[q0_2], NULLSymbol));

        pStates = null;

        m1StateMap = null;

        m2StateMap = null;

        return rc;
    }

    private Set m_states = new Set();// Q

    // private Alphabet m_alphabet; // {sigma}
    private State m_pStartingState; // q0

    private Set m_stateTransitions = new Set(); // {delta}

    private Set m_acceptingStates = new Set(); // A

    private boolean m_bHasNullTransitions = false;

    // create a state machine that accepts nothing
    public StateMachine() {
        // Create a single state for q0 with no transitions
        m_pStartingState = new State(0);
        m_states.addElement(m_pStartingState);
    }

    // creates a state machine that accepts a single symbol
    public StateMachine(RegExpPred symbol) {
        // RegExpSymbol* symbolCopy = new RegExpSymbol( symbol );

        // Create two states: "0" "1".
        State pQ0 = new State(0);
        State pQ1 = new State(1);
        m_states.addElement(pQ0);
        m_states.addElement(pQ1);

        // Add symbol to alphabet
        // m_alphabet.addElement( pSymbolCopy );

        // Set q0 to "0"
        m_pStartingState = pQ0;

        // Create a transition from "0" to "1" on symbol
        Transition pD_0_1_sym = new Transition(pQ0, pQ1, symbol);
        addTransition(pD_0_1_sym);

        // Add "1" to A
        m_acceptingStates.addElement(new State(pQ1));
    }

    public void addTransition(Transition pTrans) {
        if (pTrans != null) {
            if (pTrans.getSymbol().isNullSymbol()) {
                m_bHasNullTransitions = true;
            }

            if (!m_stateTransitions.containsElement(pTrans)) {
                m_stateTransitions.addElement(pTrans);
            }
        }
    }

    public LinkedList<String> getActions(State state) {

        LinkedList<String> actions = new LinkedList<String>();

        for (int j = 0; j < m_stateTransitions.getSize(); j++) {
            Element pElem = m_stateTransitions.elementAt(j);
            if (pElem instanceof Transition) {
                Transition pTrans = (Transition) pElem;
                if (pTrans.startsFrom(state))
                    if (pTrans.getSymbol().getLanguageDescription()
                            .startsWith("ACT[")) {
                        actions.add(pTrans.getSymbol().getLanguageDescription());
                    }
            }
        }
        if (actions.isEmpty())
            actions.add("ACT[*]");
        return actions;

    }

    public LinkedList<State> getIncomingStateTriggersAndActions(State state) {

        LinkedList<State> states = new LinkedList<State>();

        for (int j = 0; j < m_stateTransitions.getSize(); j++) {
            Element pElem = m_stateTransitions.elementAt(j);
            if (pElem instanceof Transition) {
                Transition pTrans = (Transition) pElem;
                if (pTrans.getFinalState().equals(state))
                    if (pTrans.getSymbol().getLanguageDescription()
                            .startsWith("[")
                            || pTrans.getSymbol().getLanguageDescription()
                            .startsWith("ACT[")) {
                        states.add(pTrans.getStartState());
                    }
            }
        }
        return states;

    }

    public LinkedList<Transition> getIncomingTransition(State pState) {
        LinkedList<Transition> set = new LinkedList<Transition>();
        for (int j = 0; j < m_stateTransitions.getSize(); j++) {
            Element pElem = m_stateTransitions.elementAt(j);
            if (pElem instanceof Transition) {
                Transition pTrans = (Transition) pElem;
                if (pTrans.getFinalState().equals(pState)) {
                    set.add(pTrans);
                }
            }
        }

        return set;
    }

    public LinkedList<Transition> getOutgoingTransition(State pState) {
        LinkedList<Transition> set = new LinkedList<Transition>();
        for (int j = 0; j < m_stateTransitions.getSize(); j++) {
            Element pElem = m_stateTransitions.elementAt(j);
            if (pElem instanceof Transition) {
                Transition pTrans = (Transition) pElem;
                if (pTrans.getStartState().equals(pState)
                        && !pTrans.getSymbol().getLanguageDescription()
                        .startsWith("ACT[")
                        && !pTrans.getSymbol().getLanguageDescription()
                        .startsWith("[")
                        && !pTrans.getSymbol().isNullSymbol()) {
                    set.add(pTrans);
                }
            }
        }

        return set;
    }

    public State getInitial() {
        return m_pStartingState;
    }

    public State getNext(State state, String pred) {
        for (int j = 0; j < m_stateTransitions.getSize(); j++) {
            Element pElem = m_stateTransitions.elementAt(j);
            if (pElem instanceof Transition) {
                Transition pTrans = (Transition) pElem;
                if (pTrans.startsFrom(state)
                        && pTrans.matchesSymbol(new RegExpPred(pred))) {
                    return pTrans.getFinalState();
                }

            }
        }

        return null;

    }

    public HashSet<State> getNullReachableStates(State pState) {
        HashSet<State> set = new HashSet<State>();
        for (int j = 0; j < m_stateTransitions.getSize(); j++) {
            Element pElem = m_stateTransitions.elementAt(j);
            if (pElem instanceof Transition) {
                Transition pTrans = (Transition) pElem;
                if (pTrans.getSymbol().isNullSymbol()) {
                    if (pTrans.startsFrom(pState)) {
                        State pFinal = pTrans.getFinalState();
                        if (!set.contains(pFinal)) {
                            set.add(pFinal);
                        }
                    }
                }
            }
        }

        return set;
    }

	/*
	 * public void removeNullTransitions() { // For each state, we need the set
	 * of states that you can reach using // null transitions // Go through the
	 * transitions. For each delta( p, null ) = q, Add // delta(p, a) = delta(q,
	 * a) Set[] pReachable = new Set[m_states.getSize()]; for (int i = 0; i <
	 * m_states.getSize(); i++) { pReachable[i] = new Set(); } for (int i = 0; i
	 * < m_states.getSize(); i++) { State pStart = (State)
	 * m_states.elementAt(i); findNullReachableStates(pStart, pReachable[i]);
	 * 
	 * for (int j = 0; j < m_stateTransitions.getSize(); j++) { Element pElem =
	 * m_stateTransitions.elementAt(j); if (pElem instanceof Transition) {
	 * Transition pTrans = (Transition) pElem; // if pTrans.start is in the null
	 * reachable states and // symbol isn't null, // add a transition from
	 * pStart to pTrans.Final on // pTrans.symbol if
	 * (!pTrans.getSymbol().isNullSymbol()) { if ((pTrans.getStartState() !=
	 * pStart) && (pReachable[i].containsElement(pTrans .getStartState()))) {
	 * addTransition(new Transition(pStart, pTrans.getFinalState(),
	 * pTrans.getSymbol())); } } } }
	 * 
	 * // If any state reachable by this state is accepted, then this state //
	 * is accepted for (int j = 0; j < pReachable[i].getSize(); j++) { State
	 * pState = (State) pReachable[i].elementAt(j); if
	 * (m_acceptingStates.containsElement(pState)) { if
	 * (!m_acceptingStates.containsElement(pStart)) {
	 * m_acceptingStates.addElement(new State(pStart)); } break; } } }
	 * 
	 * // Remove all of the null transitions for (int i = 0; i <
	 * m_stateTransitions.getSize(); i++) { Element pElem =
	 * m_stateTransitions.elementAt(i); if (pElem instanceof Transition) {
	 * Transition pTrans = (Transition) pElem; // if pTrans.start is in the null
	 * reachable states and symbol // isn't null, // add a transition from
	 * pStart to pTrans.Final on pTrans.symbol if
	 * (pTrans.getSymbol().isNullSymbol()) { removeTransition(pTrans); if
	 * (pTrans.getParentCount() == 0) { pTrans = null; } i--; } } }
	 * 
	 * m_bHasNullTransitions = false;
	 * 
	 * pReachable = null; }
	 */

	/*
	 * public void removeUnreachableStates() { // Create a set of reachable
	 * states // start with the start state // Take all transitions that begin
	 * at the start state and add the final // state to the set // continue
	 * processing each state in the set Set reachable = new Set();
	 * reachable.addElement(new State(m_pStartingState)); for (int i = 0; i <
	 * reachable.getSize(); i++) { State pStart = (State)
	 * reachable.elementAt(i); for (int j = 0; j < m_stateTransitions.getSize();
	 * j++) { Transition pTrans = (Transition) m_stateTransitions .elementAt(j);
	 * if (pTrans.startsFrom(pStart)) { State pFinal = pTrans.getFinalState();
	 * if (!reachable.containsElement(pFinal)) { reachable.addElement(new
	 * State(pFinal)); } } } }
	 * 
	 * // Remove all states that are not in the reachable set for (int i = 0; i
	 * < m_states.getSize(); i++) { State pState = (State)
	 * m_states.elementAt(i); if (!reachable.containsElement(pState)) { //
	 * remove it from the accepted set if
	 * (m_acceptingStates.containsElement(pState)) {
	 * m_acceptingStates.removeElement(pState); } // remove all of the
	 * transitions for (int j = 0; j < m_stateTransitions.getSize(); j++) {
	 * Transition pTrans = (Transition) m_stateTransitions .elementAt(j); if
	 * (pTrans.startsFrom(pState)) { m_stateTransitions.removeElement(pTrans);
	 * pTrans = null; j--; } } // remove it from the state list
	 * m_states.removeElement(pState);
	 * 
	 * // if it doesn't have a parent, delete it if (pState.getParentCount() ==
	 * 0) { pState = null; }
	 * 
	 * // back up one in the state list since we just removed an item i--; } }
	 * renumberStates(0); }
	 */

	/*
	 * public boolean removeEquivalentStates() { // if a and b are strings and
	 * ax and bx // both accept or both reject for all // possible x, then a and
	 * b are equivalent // If two states share outgoing transitions // then the
	 * two states are equivalent boolean rc = false; for (int i = 0; i <
	 * m_states.getSize(); i++) { State s1 = (State) m_states.elementAt(i); for
	 * (int j = i + 1; j < m_states.getSize(); j++) { State s2 = (State)
	 * m_states.elementAt(j); if (isEquivalent(s1, s2)) { // replace all s2 with
	 * s1 if (s2.equals(m_pStartingState)) { // if s2 is start, make it s1
	 * m_pStartingState = s1; } // replace all transitions with s2 for (int k =
	 * 0; k < m_stateTransitions.getSize(); k++) { Transition t = (Transition)
	 * m_stateTransitions .elementAt(k); if (t.getFinalState().equals(s2)) { //
	 * transition is immutable - add a new copy // if duplicate then it will not
	 * be added. m_stateTransitions.addElement(new Transition(t
	 * .getStartState(), s1, t.getSymbol())); } if (t.getStartState().equals(s2)
	 * || t.getFinalState().equals(s2)) { // remove the transition if it is
	 * involved with the // old state m_stateTransitions.removeElement(t); k--;
	 * } } // remove from state list and accepting state list
	 * m_states.removeElement(s2); m_acceptingStates.removeElement(s2); j--; //
	 * return true since we removed at least one state rc = true; } } } if (rc)
	 * renumberStates(0); return rc; }
	 */

	/*
	 * 
	 * public boolean joinSimilarStates() { // If two states have the same input
	 * transitions, then they // represent equivalent strings. The output
	 * transitions // can be merged for these two states into one new state
	 * boolean rc = false; for (int i = 0; i < m_states.getSize(); i++) { State
	 * s1 = (State) m_states.elementAt(i); for (int j = i + 1; j <
	 * m_states.getSize(); j++) { State s2 = (State) m_states.elementAt(j); if
	 * (isSimilar(s1, s2)) { // remove s2. // Replace transitions (s2,symbol,sn)
	 * with (s1,symbol,sn) // Remove transitions (sn,symbol,s2) if
	 * (s2.equals(m_pStartingState)) { // if s2 is start, make it s1
	 * m_pStartingState = s1; } // replace all transitions with s2 for (int k =
	 * 0; k < m_stateTransitions.getSize(); k++) { Transition t = (Transition)
	 * m_stateTransitions .elementAt(k); if (t.getStartState().equals(s2)) { //
	 * transition is immutable - add a new copy // if duplicate then it will not
	 * be added. m_stateTransitions.addElement(new Transition(s1, t
	 * .getFinalState(), t.getSymbol())); } if (t.getStartState().equals(s2) ||
	 * t.getFinalState().equals(s2)) { // remove the transition if it is
	 * involved with the // old state m_stateTransitions.removeElement(t); k--;
	 * } } // remove from state list and accepting state list
	 * m_states.removeElement(s2); m_acceptingStates.removeElement(s2); j--; //
	 * return true since we removed at least one state rc = true; } } } if (rc)
	 * renumberStates(0); return rc; }
	 */

	/*
	 * public Set getNondeterministicGroups() { Set groups = new Set(); for (int
	 * i = 0; i < m_stateTransitions.getSize(); i++) { Transition t1 =
	 * (Transition) m_stateTransitions.elementAt(i); for (int j = 0; j <
	 * m_stateTransitions.getSize(); j++) { Transition t2 = (Transition)
	 * m_stateTransitions.elementAt(j); if (t1.isNonDeterministic(t2) &&
	 * !t1.getSymbol().isWildcardSymbol() && !t2.getSymbol().isWildcardSymbol())
	 * { groups.addElement(new Transition(t1.getStartState(), new
	 * State("NON-DET-GROUP"), t1.getSymbol())); } } } return groups; }
	 */

	/*
	 * public void removeNondeterminism(State state, RegExpPred symbol) { if
	 * (symbol.isWildcardSymbol()) { return; } // build a list of non-det
	 * transitions Set group = new Set(); for (int i = 0; i <
	 * m_stateTransitions.getSize(); i++) { Transition t = (Transition)
	 * m_stateTransitions.elementAt(i); if (t.getStartState().equals(state) &&
	 * t.getSymbol().equals(symbol)) { group.addElement(new Transition(t)); } }
	 * if (group.getSize() > 1) { // create a unique state id String id = "";
	 * for (int i = 0; i < group.getSize(); i++) { if (i > 0) { id += "-"; }
	 * Transition t = (Transition) group.elementAt(i); id +=
	 * t.getFinalState().getID(); } // Create a new state State newState = new
	 * State(id); m_states.addElement(newState); // update transitions // add
	 * det transition to new state m_stateTransitions.addElement(new
	 * Transition(state, newState, symbol)); for (int i = 0; i <
	 * m_stateTransitions.getSize(); i++) { Transition t = (Transition)
	 * m_stateTransitions.elementAt(i); if (t.getStartState().equals(state) &&
	 * t.getSymbol().equals(symbol) && !t.getFinalState().equals(newState)) { //
	 * remove non-det transitions m_stateTransitions.removeElement(t); i--; }
	 * else { // add transitions from new state for (int j = 0; j <
	 * group.getSize(); j++) { Transition g = (Transition) group.elementAt(j);
	 * // if transition is from component of new state, // add a new transition
	 * from new state if (t.getStartState().equals(g.getFinalState())) {
	 * m_stateTransitions .addElement(new Transition(newState, t
	 * .getFinalState(), t.getSymbol())); } } } } // update accepting states for
	 * (int i = 0; i < group.getSize(); i++) { Transition g = (Transition)
	 * group.elementAt(i); if
	 * (m_acceptingStates.containsElement(g.getFinalState())) {
	 * m_acceptingStates.addElement(new State(newState)); break; } } } }
	 */

	/*
	 * private boolean isSimilar(State s1, State s2) { boolean rc = false; if
	 * (m_acceptingStates.containsElement(s1) == m_acceptingStates
	 * .containsElement(s2)) { Set trans = new Set(); for (int i = 0; i <
	 * m_stateTransitions.getSize(); i++) { Transition t = (Transition)
	 * m_stateTransitions.elementAt(i); if (t.getFinalState().equals(s1)) {
	 * trans.addElement(new Transition(t.getStartState(), t .getFinalState(),
	 * t.getSymbol())); } } for (int i = 0; i < m_stateTransitions.getSize();
	 * i++) { Transition t = (Transition) m_stateTransitions.elementAt(i); if
	 * (t.getFinalState().equals(s2)) { Transition temp = new
	 * Transition(t.getStartState(), s1, t.getSymbol()); if
	 * (!trans.containsElement(temp)) { return false; }
	 * trans.removeElement(temp); } } if (trans.getSize() == 0) { rc = true; } }
	 * return rc; }
	 */

	/*
	 * 
	 * private boolean isEquivalent(State s1, State s2) { boolean rc = false; if
	 * (m_acceptingStates.containsElement(s1) == m_acceptingStates
	 * .containsElement(s2)) { Set trans = new Set(); for (int i = 0; i <
	 * m_stateTransitions.getSize(); i++) { Transition t = (Transition)
	 * m_stateTransitions.elementAt(i); if (t.getStartState().equals(s1)) {
	 * trans.addElement(new Transition(t.getStartState(), t .getFinalState(),
	 * t.getSymbol())); } } for (int i = 0; i < m_stateTransitions.getSize();
	 * i++) { Transition t = (Transition) m_stateTransitions.elementAt(i); if
	 * (t.getStartState().equals(s2)) { Transition temp = new Transition(s1,
	 * t.getFinalState(), t.getSymbol()); if (!trans.containsElement(temp)) {
	 * return false; } trans.removeElement(temp); } } if (trans.getSize() == 0)
	 * { rc = true; } } return rc; }
	 */

	/*
	 * 
	 * private void findNullReachableStates(State pState, Set reachable) { if
	 * (!reachable.containsElement(pState)) { reachable.addElement(new
	 * State(pState)); }
	 * 
	 * for (int i = 0; i < reachable.getSize(); i++) { State pStart = (State)
	 * reachable.elementAt(i); for (int j = 0; j < m_stateTransitions.getSize();
	 * j++) { Element pElem = m_stateTransitions.elementAt(j); if (pElem
	 * instanceof Transition) { Transition pTrans = (Transition) pElem; if
	 * (pTrans.getSymbol().isNullSymbol()) { if (pTrans.startsFrom(pStart)) {
	 * State pFinal = pTrans.getFinalState(); if
	 * (!reachable.containsElement(pFinal)) { reachable.addElement(new
	 * State(pFinal)); } } } } }
	 * 
	 * }
	 * 
	 * return; }
	 */

	/*
	 * 
	 * private void renumberStates(int startIndex) { for (int i = 0; i <
	 * m_states.getSize(); i++) { State pState = (State) m_states.elementAt(i);
	 * State pAState = (State) m_acceptingStates.getInstance(pState);
	 * pState.setID(startIndex + i); if (pAState != null) {
	 * pAState.setID(startIndex + i); } } }
	 */

    public State getState(String id) {
        for (int i = 0; i < m_states.getSize(); i++) {
            State curr = ((State) m_states.elementAt(i));
            if (curr.getID().equals(id))
                return curr;
        }
        return null;
    }

    public LinkedList<String> getTriggers(State state) {
        LinkedList<String> triggers = new LinkedList<String>();
        for (int j = 0; j < m_stateTransitions.getSize(); j++) {
            Element pElem = m_stateTransitions.elementAt(j);
            if (pElem instanceof Transition) {
                Transition pTrans = (Transition) pElem;
                if (pTrans.startsFrom(state))
                    if (pTrans.getSymbol().getLanguageDescription()
                            .startsWith("[")) {
                        triggers.add(pTrans.getSymbol()
                                .getLanguageDescription());
                    }
            }
        }
        if (triggers.isEmpty())
            triggers.add("[*]");
        return triggers;
    }

    public boolean hasNullTransitions() {
        return m_bHasNullTransitions;
    }

    public boolean hasOutgoingTransitions(State state) {
        for (int j = 0; j < m_stateTransitions.getSize(); j++) {
            Element pElem = m_stateTransitions.elementAt(j);
            if (pElem instanceof Transition) {
                Transition pTrans = (Transition) pElem;
                if (pTrans.startsFrom(state)) {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean isFinal(State st) {

        return m_acceptingStates.containsElement(st);
    }

    public ArrayList Expression() {
        ArrayList<String> Expression_list = new ArrayList<String>();
        String Exp = "";
        for (int i = 0; i < m_stateTransitions.getSize(); i++) {
            Transition pDelta = (Transition) m_stateTransitions.elementAt(i);
            if (!(pDelta.getSymbol().isNullSymbol())) {
                     Exp = Exp + pDelta.getSymbol().getLanguageDescription()+ "/";
            }
            if ((pDelta.getSymbol().getLanguageDescription().equals("<http://www.w3.org/2002/07/owl#sameAs>")) || i == m_stateTransitions.getSize() - 1) {
                if (!(Exp.trim().equals(""))) {
                    Exp = Exp.substring(0, Exp.length() - 1);
                    if (Exp.indexOf("/[") > 1){
                        Exp = Exp.replaceAll("/\\[", "[");
                    }
                    Expression_list.add(Exp.trim());
                    Exp = "";
                }
            }
        }
        return Expression_list;
    }

    public void printMachine(PrintStream out) {
        out.print("Q = ( ");
        for (int i = 0; i < m_states.getSize(); i++) {
            State pState = (State) m_states.elementAt(i);
            out.print(pState.getID() + " ");
        }
        out.println(")");
        out.print("Q0 = ( ");
        out.print(m_pStartingState.getID() + " ");
        out.println(")");
        out.println("delta:");
        for (int i = 0; i < m_stateTransitions.getSize(); i++) {
            Transition pDelta = (Transition) m_stateTransitions.elementAt(i);
            if (pDelta.getSymbol().isNullSymbol())
                out.println("\t" + pDelta.getStartState().getID() + " -> "
                        + pDelta.getFinalState().getID() + " : "
                        + pDelta.getSymbol().getLanguageDescription());
            else
                out.println("\t" + pDelta.getStartState().getID() + " -> "
                        + pDelta.getFinalState().getID() + " : "
                        + pDelta.getSymbol().getLanguageDescription());
        }

        out.print("A = ( ");
        for (int i = 0; i < m_acceptingStates.getSize(); i++) {
            State pState = (State) m_acceptingStates.elementAt(i);
            out.print(pState.getID() + " ");
        }
        out.println(")");
    }

    public void removeTransition(Transition pTrans) {
        if (pTrans != null) {
            m_stateTransitions.removeElement(pTrans);
        }
    }
}