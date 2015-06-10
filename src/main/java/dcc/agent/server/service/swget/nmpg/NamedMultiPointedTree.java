package dcc.agent.server.service.swget.nmpg;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import java.net.IDN;
import java.util.Hashtable;

public class NamedMultiPointedTree extends NamedMultiPointedGraphAbstract {

	Hashtable<String, String> r_edges = new Hashtable<String, String>();
	Hashtable<String, String> labels = new Hashtable<String, String>();
	String root;
	Hashtable<String, Integer> distances = new Hashtable<String, Integer>();

	public NamedMultiPointedTree(String root) {
		this.root = root;
	}

	public void addEdge(String outNode, String inNode, String label) {

		nodes.add(inNode);
		nodes.add(outNode);

		r_edges.put(inNode, outNode);

		labels.put(inNode, label);

	}

	public void removeNode(String node) {
		nodes.remove(node);
	}

	public void setDistance(String node, int distance) {
		distances.put(node, distance);
	}

	public int getDistance(String node) {
		return distances.get(node);
	}

	public void removeEdge(String outNode, String inNode) {

		if (r_edges.get(inNode).equals(outNode))
			r_edges.remove(inNode);

	}

	public String getInNode(String node) {
		return r_edges.get(node);
	}

	public String getInLabel(String node) {
		return labels.get(node);
	}

	public Model write2Model() {
		Model model = ModelFactory.createDefaultModel();

		for (String node : getNodes()) {
			if (isEndingNode(node))
				model.add(new StatementImpl(
						ResourceFactory.createResource(IDN.toUnicode(node)),
						ResourceFactory
								.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
						ResourceFactory
								.createResource("http://nmpg.org/ending")));
			if (isStartingNode(node))
				model.add(new StatementImpl(
						ResourceFactory.createResource(IDN.toUnicode(node)),
						ResourceFactory
								.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
						ResourceFactory
								.createResource("http://nmpg.org/starting")));
			// edges = getReachableNodes(node);
			if (r_edges.get(node) != null) {
				String label = labels.get(node);
				String rnode = r_edges.get(node);
				if (isEndingNode(rnode))
					model.add(new StatementImpl(
							ResourceFactory
									.createResource(IDN.toUnicode(rnode)),
							ResourceFactory
									.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
							ResourceFactory
									.createResource("http://nmpg.org/ending")));
				if (isStartingNode(rnode))
					model.add(new StatementImpl(
							ResourceFactory
									.createResource(IDN.toUnicode(rnode)),
							ResourceFactory
									.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
							ResourceFactory
									.createResource("http://nmpg.org/starting")));
				model.add(new StatementImpl(ResourceFactory.createResource(IDN
						.toUnicode(rnode)), ResourceFactory
						.createProperty(label), ResourceFactory
						.createResource(IDN.toUnicode(node))));
			}
		}

		return model;

	}
}
