package dcc.agent.server.service.swget.nmpg;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import java.util.HashSet;
import java.util.Hashtable;

public class NamedMultiPointedGraph extends NamedMultiPointedGraphAbstract {

	// key1 node, key2 label, hashset nodes
	Hashtable<String, Hashtable<String, HashSet<String>>> back_edges = new Hashtable<String, Hashtable<String, HashSet<String>>>();
	Hashtable<String, Hashtable<String, HashSet<String>>> edges = new Hashtable<String, Hashtable<String, HashSet<String>>>();

	private int num_edges;

	public int getNum_edges() {
		return num_edges;
	}

	public NamedMultiPointedGraph() {
		num_edges = 0;
	}

	public NamedMultiPointedGraph(Model model) {

		StmtIterator iter = model.listStatements();
		num_edges = 0;

		while (iter.hasNext()) {

			Statement stmt = iter.nextStatement(); // get next statement
			Resource subject = stmt.getSubject(); // get the subject
			Property predicate = stmt.getPredicate(); // get the predicate
			RDFNode object = stmt.getObject(); // get the object

			if (predicate.toString().equals(
					"http://www.w3.org/1999/02/22-rdf-syntax-ns#type"))
				if (ResourceFactory.createResource(object.toString())
						.toString().equals("http://nmpg.org/starting")) {
					addNode(subject.toString());
					setAsStartingNode(subject.toString());
				} else if (ResourceFactory.createResource(object.toString())
						.toString().equals("http://nmpg.org/ending")) {
					addNode(subject.toString());
					setAsEndingNode(subject.toString());
				} else {
					addEdge(subject.toString(),
							ResourceFactory.createResource(object.toString())
									.toString(), predicate.toString());
				}
			else
				addEdge(subject.toString(),
						ResourceFactory.createResource(object.toString())
								.toString(), predicate.toString());

		}

	}

	public void addEdge(String outNode, String inNode, String label) {

		nodes.add(inNode);
		nodes.add(outNode);

		Hashtable<String, HashSet<String>> ht1_r = back_edges.get(inNode);

		if (ht1_r == null) {
			ht1_r = new Hashtable<String, HashSet<String>>();
			back_edges.put(inNode, ht1_r);
		}

		HashSet<String> nodes = ht1_r.get(label);

		if (nodes == null) {
			nodes = new HashSet<String>();
			ht1_r.put(label, nodes);
		}

		if (!nodes.contains(outNode)) {
			nodes.add(outNode);
			num_edges++;
		}

		ht1_r = edges.get(outNode);

		if (ht1_r == null) {
			ht1_r = new Hashtable<String, HashSet<String>>();
			edges.put(outNode, ht1_r);
		}

		nodes = ht1_r.get(label);

		if (nodes == null) {
			nodes = new HashSet<String>();
			ht1_r.put(label, nodes);
		}

		nodes.add(inNode);

	}

	public Hashtable<String, HashSet<String>> getReachableNodes(String node) {
		return edges.get(node);
	}

	public HashSet<String> getIncomingNodes(String node, String label) {
		if (back_edges.get(node) == null)
			return new HashSet<String>();
		return back_edges.get(node).get(label);
	}

	public Hashtable<String, HashSet<String>> getIncomingNodes(String node) {
		if (back_edges.get(node) == null)
			return new Hashtable<String, HashSet<String>>();
		return back_edges.get(node);
	}

	public Model write2Model() {
		Model model = ModelFactory.createDefaultModel();
		Hashtable<String, HashSet<String>> edges;

		for (String node : getNodes()) {
			if (isEndingNode(node))
				model.add(new StatementImpl(
						ResourceFactory.createResource(node),
						ResourceFactory
								.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
						ResourceFactory
								.createResource("http://nmpg.org/ending")));
			if (isStartingNode(node))
				model.add(new StatementImpl(
						ResourceFactory.createResource(node),
						ResourceFactory
								.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
						ResourceFactory
								.createResource("http://nmpg.org/starting")));
			edges = getReachableNodes(node);
			if (edges != null) {
				for (String label : edges.keySet()) {
					for (String rnode : edges.get(label)) {
						if (isEndingNode(rnode))
							model.add(new StatementImpl(
									ResourceFactory.createResource(rnode),
									ResourceFactory
											.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
									ResourceFactory
											.createResource("http://nmpg.org/ending")));
						if (isStartingNode(rnode))
							model.add(new StatementImpl(
									ResourceFactory.createResource(rnode),
									ResourceFactory
											.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
									ResourceFactory
											.createResource("http://nmpg.org/starting")));

						// System.out.println(node+ " - "+label.substring(1,
						// label.length()-1)+" - "+rnode);

						model.add(new StatementImpl(ResourceFactory
								.createResource(node), ResourceFactory
								.createProperty(label), ResourceFactory
								.createResource(rnode)));
					}
				}
			}
		}

		return model;

	}

}
