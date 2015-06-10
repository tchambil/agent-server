package dcc.agent.server.service.swget.regExpression.automaton;

public class RegExpPred extends RegularExpressionBase {
	private String m_symbol = "";
	private boolean m_bNullSymbol = true;
	private boolean m_bWildcardSymbol = false;

	public RegExpPred() {
	}

	public RegExpPred(boolean isWildcard) {
		m_bNullSymbol = !isWildcard;
		m_bWildcardSymbol = isWildcard;
	}

	public RegExpPred(RegExpPred pred) {
		m_bNullSymbol = pred.m_bNullSymbol;
		m_bWildcardSymbol = pred.m_bWildcardSymbol;
		m_symbol = pred.m_symbol;
	}

	public RegExpPred(String pred) {
		m_bNullSymbol = false;
		m_bWildcardSymbol = false;
		m_symbol = pred;
	}

	public int compareElements(Element rhs) {
		int rc = super.compareElements(rhs);

		if (rhs instanceof RegExpPred) {
			RegExpPred pSymbol = (RegExpPred) (rhs);
			if (rc == 0) {
				if (!m_bNullSymbol && !pSymbol.m_bNullSymbol) {
					rc = m_symbol.compareTo(pSymbol.m_symbol);
				}
			}
			rc = ((rc == 0) && (m_bWildcardSymbol == pSymbol.m_bWildcardSymbol)) ? 0
					: -1;
		}

		return rc;
	}

	public boolean equals(RegExpPred rhs) {
		boolean rc = true;
		rc = rc && (m_symbol.equals(rhs.m_symbol));
		rc = rc && (m_bNullSymbol == rhs.m_bNullSymbol);
		rc = rc || m_bWildcardSymbol;
		return rc;
	}

	public StateMachine generateStateMachine() {
		StateMachine rc = null;

		rc = new StateMachine(this);

		return rc;
	}

	public String getLanguageDescription() {
		String rc = "";

		if (m_bNullSymbol) {
			rc += "^";
		} else if (m_bWildcardSymbol) {
			rc += "{_}";
		} else {
			rc += m_symbol;
		}

		return rc;
	}

	public boolean isNullSymbol() {
		return m_bNullSymbol;
	}

	public boolean isWildcardSymbol() {
		return m_bWildcardSymbol;
	}
}