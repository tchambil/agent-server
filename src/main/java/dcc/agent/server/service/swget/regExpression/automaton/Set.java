package dcc.agent.server.service.swget.regExpression.automaton;

import java.util.Enumeration;
import java.util.Vector;

public class Set extends Element {
	private Vector m_list = new Vector();

	public boolean addElement(Element element) {
		boolean rc = false;

		// Make sure this instance is not in the list already
		if (m_list.indexOf(element) == -1) {
			rc = true;
			m_list.addElement(element);
			// Make sure the element accepts the set as a parent
			if (!element.addParent(this)) {
				// if not, remove the new element and return false
				m_list.removeElement(element);
				rc = false;
			}
		}
		return rc;
	}

	public boolean containsElement(Element element) {
		return (m_list.indexOf(element) != -1);
	}

	public boolean containsInstance(Element element) {
		return (m_list.indexOf(element) != -1);
	}

	public Element elementAt(int index) {
		return (Element) m_list.elementAt(index);
	}

	public Element getInstance(Element element) {
		Element rc = null;

		int pos = m_list.indexOf(element);
		if (pos != -1) {
			rc = (Element) m_list.elementAt(pos);
		}

		return rc;
	}

	public int getSize() {
		return m_list.size();
	}

	public void intersection(Set set) {
		Enumeration r = m_list.elements();
		while (r.hasMoreElements()) {
			Element element = (Element) r.nextElement();
			if (!set.containsElement(element)) {
				removeElement(element);
			}
		}
	}

	/*
	 * private int getInstancePosition( Element element ) { return
	 * m_list.indexOf(element); }
	 */

	public boolean removeElement(Element element) {
		boolean rc = false;
		int index = m_list.indexOf(element);
		if (index != -1) {
			// make sure we have the correct instance for removeParent
			element = (Element) m_list.elementAt(index);
			m_list.removeElementAt(index);
			element.removeParent(this);
			rc = true;
		}
		return rc;
	}

	public void union(Set set) {
		Enumeration enumr = set.m_list.elements();
		while (enumr.hasMoreElements()) {
			Element element = (Element) enumr.nextElement();
			if (!containsElement(element)) {
				addElement(element);
			}
		}
	}
}