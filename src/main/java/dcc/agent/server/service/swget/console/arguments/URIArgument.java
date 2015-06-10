package dcc.agent.server.service.swget.console.arguments;

import java.net.URI;

public class URIArgument extends CommandOption {

	private URI u;

	public URIArgument(URI u) {
		this.u = u;

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return u.toString();
	}

	public URI getURI() {
		return u;
	}

}
