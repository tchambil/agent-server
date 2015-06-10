package dcc.agent.server.service.swget.regExpression.automaton;

import java.util.Vector;

public class Element implements Cloneable {
	private Vector m_parentList = new Vector();

	public boolean addParent(Set pParent) {
		boolean rc = false;

		// Make sure the parent is not null
		if (pParent != null) {
			// If the new set is not already a parent, then accept the parent.
			if (m_parentList.indexOf(pParent) == -1) {
				rc = true;
				// Add the parent to our list.
				m_parentList.addElement(pParent);
				// Make sure that our instance is really a child of the parent
				if (!pParent.containsInstance(this)) {
					pParent.addElement(this);
				}
			}
		}

		return rc;
	}

	public int compareElements(Element rhs) {
		int rc = -1;
		rc = (getClass() == rhs.getClass()) ? 0 : -1;
		return rc;
	}

	public boolean equals(Object rhs) {
		boolean rc = false;
		if (rhs instanceof Element) {
			rc = compareElements((Element) rhs) == 0;
		}
		return rc;
	}

	public int getParentCount() {
		return m_parentList.size();
	}

	public boolean greaterthan(Element rhs) {
		boolean rc = compareElements(rhs) > 0;
		return rc;
	}

	public boolean greaterthanequals(Element rhs) {
		boolean rc = compareElements(rhs) >= 0;
		return rc;
	}

	public boolean isParent(Set pSet) {
		return (m_parentList.indexOf(pSet) != -1);
	}

	public boolean lessthan(Element rhs) {
		boolean rc = compareElements(rhs) < 0;
		return rc;
	}

	public boolean lessthanequals(Element rhs) {
		boolean rc = compareElements(rhs) <= 0;
		return rc;
	}

	public boolean removeParent(Set pParent) {
		boolean rc = false;
		// Make sure the parent is not null
		if (pParent != null) {
			// remove the parent from our list
			if (m_parentList.indexOf(pParent) != -1) {
				rc = true;
				m_parentList.removeElement(pParent);
			}

			// Make sure that we are not a child of the parent anymore.
			if (pParent.containsInstance(this)) {
				pParent.removeElement(this);
			}
		}
		return rc;
	}

}