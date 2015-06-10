package dcc.agent.server.service.swget.multithread;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkManager {
	
	NavigatorIF navigator;

	public NetworkManager(Navigator navigator) {
		this.navigator=navigator;
	}

	public Model getModel(String uri) {

		Model model = ModelFactory.createDefaultModel();

		URLConnection connection = null;
		try {
			connection = makeConnection(new URL(uri));
			connection.setReadTimeout(2000);
			connection.setConnectTimeout(2000);

		} catch (MalformedURLException e1) {
			System.err.println("Malformed URI:" + uri);

		}
		try {

			connection.connect();
			readInToModel(model, connection);

		} catch (Exception e) {
			connection = null;
            System.err.println("\nI/O error in reading Jena model:" + uri+"\n");
			// System.err.println("I/O error in reading Jena model:" + uri);
			return model;

		}

		return model;

	}

	private URLConnection makeConnection(URL targetURL) {
		URLConnection uc = null;
		try {
			uc = targetURL.openConnection();

		} catch (IOException e) {

			System.err
					.println("Error (makeConnection) in opening connection with the URL:"
							+ targetURL.toString());

		}

		uc.setRequestProperty("Accept", "application/rdf+xml");

		return uc;
	}

	private Model readInToModel(Model m, URLConnection uc) throws IOException {
		InputStream stream = null;

		try {
			
			stream = uc.getInputStream();
			InputStreamReader bis = new InputStreamReader(stream);
			BufferedReader in = new BufferedReader(bis);

		if (!uc.getContentType().toString().contains("html")) 
		{

				m.read(in, "");
							}

		} catch (Exception e) 
		{
			//System.err.println("Error: no Jena model read for:"
			//		+ uc.getURL().toString());

			if (stream != null)
				stream.close();

			return m;
			// e.printStackTrace();

		} finally {
			if (stream != null) {
				try {

					stream.close();

					return m;

				} catch (IOException e) {

					System.err.println("Error while reading Jena model:"
							+ uc.getURL().toString());

					// e.printStackTrace();
					return m;
				}
			}
		}

		return m;
	}

}
