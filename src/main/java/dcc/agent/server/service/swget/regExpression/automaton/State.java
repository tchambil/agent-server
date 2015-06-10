package dcc.agent.server.service.swget.regExpression.automaton;

public class State extends Element implements
		Cloneable {
	private String m_id = "";

	public State(int id) {
		m_id = "" + id;
	}

	public State(State state) {
		m_id = new String(state.m_id);
	}

	public State(String id) {
		m_id = id;
	}

	public int compareElements(Element rhs) {
		int rc = super.compareElements(rhs);
		if (rhs instanceof State) {
			rc = m_id.compareTo(((State) rhs).m_id);
		}
		return rc;
	}

	public boolean equals(Object rhs) {
		boolean rc = false;
		if (rhs instanceof State) {
			rc = m_id.equals(((State) rhs).m_id);
		}
		return rc;
	}

	public String getID() {
		return m_id;
	}

	public void setID(int id) {
		m_id = "" + id;
	}

	public void setID(String id) {
		m_id = id;
	}
}