package dcc.agent.server.service.swget.utils;

import dcc.agent.server.service.swget.regExpression.automaton.State;

public class URIPair {

	String url;
	State state;

	public URIPair(String url, State state) {
		super();
		this.url = url;
		this.state = state;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof URIPair))
			return false;
		if (((URIPair) o).getUrl().equals(this.url)
				&& ((URIPair) o).getState().getID().equals(this.state.getID()))
			return true;
		else
			return false;
	}

	public State getState() {
		return state;
	}

	public String getUrl() {
		return url;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toString() {
		return url + state.getID();
	}

}
