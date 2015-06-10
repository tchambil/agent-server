package dcc.agent.server.service.swget.regExpression.automaton;

public class Transition extends Element {
	private State m_startState;
	private State m_endState;
	private RegExpPred m_symbol;

	public Transition(State start, State end, RegExpPred symbol) {
		m_startState = start;
		m_endState = end;
		m_symbol = symbol;
	}

	public Transition(Transition t) {
		m_startState = t.m_startState;
		m_endState = t.m_endState;
		m_symbol = t.m_symbol;
	}

	public int compareElements(Element rhs) {
		int rc = super.compareElements(rhs);

		if (rhs instanceof Transition) {
			Transition pTransition = (Transition) (rhs);
			if (rc == 0) {
				rc = m_startState.compareElements(pTransition.m_startState);
			}
			if (rc == 0) {
				rc = m_symbol.equals(pTransition.m_symbol) ? 0 : 1;
			}
			if (rc == 0) {
				rc = m_endState.compareElements(pTransition.m_endState);
			}
		}

		return rc;
	}

	public boolean equals(Object rhs) {
		boolean rc = false;
		if (rhs instanceof Transition) {
			rc = (compareElements((Transition) rhs) == 0);
		}
		return rc;
	}

	public State getFinalState() {
		return m_endState;
	}

	public State getStartState() {
		return m_startState;
	}

	public RegExpPred getSymbol() {
		return m_symbol;
	}

	public boolean isNonDeterministic(Transition t) {
		return m_startState.equals(t.m_startState)
				&& m_symbol.equals(t.m_symbol)
				&& !m_endState.equals(t.m_endState);
	}

	public boolean matchesSymbol(RegExpPred symbol) {
		return (m_symbol.equals(symbol));
	}

	public boolean startsFrom(State state) {
		return (m_startState.equals(state));
	}
}