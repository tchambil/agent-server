package dcc.agent.server.service.swget.regExpression.automaton;

public class RegExpIntersect extends RegularExpressionBase {
	private RegularExpressionBase m_r;
	private RegularExpressionBase m_s;

	public RegExpIntersect(RegularExpressionBase r, RegularExpressionBase s) {
		m_r = r;
		m_s = s;
	}

	public StateMachine generateStateMachine() {
		StateMachine rc = null;

		StateMachine pMr = m_r.generateStateMachine();
		StateMachine pMs = m_s.generateStateMachine();

		rc = StateMachine.createIntersection(pMr, pMs);

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

		if (m_s != null) {
			if (rc.equals("("))
				rc += m_s.getLanguageDescription();
			else
				rc += '/' + m_s.getLanguageDescription();
		}

		rc += ")";

		return rc;
	}
}