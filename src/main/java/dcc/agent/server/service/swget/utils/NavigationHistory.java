package dcc.agent.server.service.swget.utils;

import dcc.agent.server.service.swget.regExpression.automaton.State;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

public class NavigationHistory {

	Hashtable<String, HashSet<State>> S;
	Hashtable<String, HashSet<State>> A;

	public NavigationHistory() {
		S = new Hashtable<String, HashSet<State>>();
		A = new Hashtable<String, HashSet<State>>();
	}

	public NavigationHistory(String seed, State initial) {
		S = new Hashtable<String, HashSet<State>>();
		A = new Hashtable<String, HashSet<State>>();

		S.put(seed, new HashSet<State>());

		S.get(seed).add(initial);
	}

	public void add(String uri, State s, boolean isFinal) {

		HashSet<State> hs = S.get(uri);

		if (hs == null) {
			hs = new HashSet<State>();
			S.put(uri, hs);
		}

		if (s != null) {
			hs.add(s);

			if (isFinal) {
				hs = A.get(uri);

				if (hs == null) {
					hs = new HashSet<State>();
					A.put(uri, hs);
				}

				hs.add(s);
			}
		}
	}

	public HashSet<String> containsState(State state) {
		HashSet<String> hs = new HashSet<String>();

		for (String url : S.keySet()) {
			if (S.get(url).contains(state))
				hs.add(url);
		}

		return hs;

	}

	public LinkedList<URIPair> getFinals() {
		LinkedList<URIPair> fs = new LinkedList<URIPair>();
		HashSet<State> hs;

		for (String url : A.keySet()) {
			hs = A.get(url);
			for (State state : hs) {
				fs.add(new URIPair(url, state));
			}
		}

		return fs;
	}

	public boolean urlReachebleAtState(String url, State state) {
		if (S.get(url) == null)
			return false;
		if (state == null)
			return (S.get(url) != null);
		return S.get(url).contains(state);
	}

}
