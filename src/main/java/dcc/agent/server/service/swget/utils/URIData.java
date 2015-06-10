package dcc.agent.server.service.swget.utils;

import dcc.agent.server.service.swget.regExpression.automaton.State;

public class URIData {
	String url;
	int depth;
	State state;
	String provenance_authority;

	String type_of_link = "";

	public URIData() {
		provenance_authority = "";

	}

	public URIData(String url, int depth) {

		this.url = url;
		this.depth = depth;
	}

	public URIData(String url, int depth, State state,
			String provenance_authority, String type_of_link) {
		super();
		this.url = url;
		this.depth = depth;
		this.state = state;
		this.provenance_authority = provenance_authority;
		this.type_of_link = type_of_link;
	}

	public URIData(String url, State state) {
		this.url = url;
		this.state = state;
	}

	public int getDepth() {
		return depth;
	}

	public String getProvenanceAuthority() {
		return provenance_authority;
	}

	public State getState() {
		return state;
	}

	public String getTypeOfLink() {
		return type_of_link;
	}

	public String getUrl() {
		return url;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setProvenanceAuthority(String provenance_authority) {
		this.provenance_authority = provenance_authority;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setType_of_link(String type_of_link) {
		this.type_of_link = type_of_link;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
