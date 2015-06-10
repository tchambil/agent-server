package dcc.agent.server.service.swget.regExpression.automaton;

public class RegExpUnion extends RegularExpressionBase {
	private RegularExpressionBase m_r;
	private RegularExpressionBase m_s;

	public RegExpUnion(RegularExpressionBase r, RegularExpressionBase s) {
		m_r = r;
		m_s = s;
	}

	public StateMachine generateStateMachine() {
		StateMachine rc = null;

		StateMachine pMr = m_r.generateStateMachine();
		StateMachine pMs = m_s.generateStateMachine();

		rc = StateMachine.createUnion(pMr, pMs);

		pMr = null;
		pMs = null;

		return rc;
	}

	public String getLanguageDescription() {
		String rc = "";

		rc += "(";

		if (m_r != null) {
			rc += m_r.getLanguageDescription();
		}

		rc += "|";

		if (m_s != null) {
			rc += m_s.getLanguageDescription();
		}

		rc += ")";

		return rc;
	}
}
