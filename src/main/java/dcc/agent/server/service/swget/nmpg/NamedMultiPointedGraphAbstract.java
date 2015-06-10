package dcc.agent.server.service.swget.nmpg;

import com.hp.hpl.jena.rdf.model.Model;

import java.util.HashSet;
import java.util.Set;

public abstract class NamedMultiPointedGraphAbstract {

	HashSet<String> nodes = new HashSet<String>();

	HashSet<String> b_nodes = new HashSet<String>();
	HashSet<String> e_nodes = new HashSet<String>();

	public abstract void addEdge(String n1, String n2, String label);

	public Set<String> getNodes() {
		return nodes;
	}

	public void addNode(String node) {
		nodes.add(node);
	}

	public void setAsStartingNode(String n) {
		if (!nodes.contains(n))
			nodes.add(n);
		b_nodes.add(n);

	}

	public void setAsEndingNode(String n) {
		if (!nodes.contains(n))
			nodes.add(n);
		e_nodes.add(n);
	}

	public Set<String> getStartingNodes() {
		return b_nodes;

	}

	public Set<String> getEndingNodes() {
		return e_nodes;

	}

	public boolean isStartingNode(String n) {
		return b_nodes.contains(n);
	}

	public boolean isEndingNode(String n) {
		return e_nodes.contains(n);
	}

	public static boolean areCompatible(NamedMultiPointedGraphAbstract g1,
			NamedMultiPointedGraphAbstract g2) {

		for (String s : g1.b_nodes)
			if (!g2.b_nodes.contains(s))
				return false;

		for (String s : g2.b_nodes)
			if (!g1.b_nodes.contains(s))
				return false;

		for (String s : g1.e_nodes)
			if (!g2.e_nodes.contains(s))
				return false;

		for (String s : g2.e_nodes)
			if (!g1.e_nodes.contains(s))
				return false;

		return true;

	}

	public abstract Model write2Model();

}
