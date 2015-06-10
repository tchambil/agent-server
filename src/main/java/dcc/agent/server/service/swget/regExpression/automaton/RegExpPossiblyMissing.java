package dcc.agent.server.service.swget.regExpression.automaton;

public class RegExpPossiblyMissing extends RegularExpressionBase {
	private RegularExpressionBase m_r;

	public RegExpPossiblyMissing(RegularExpressionBase r) {
		m_r = r;
	}

	public StateMachine generateStateMachine() {
		StateMachine rc = null;

		StateMachine pMr = m_r.generateStateMachine();

		rc = StateMachine.createPossiblyMissing(pMr);

		pMr = null;

		return rc;
	}

	public String getLanguageDescription() {
		String rc = "";

		rc += "(";
		if (m_r != null) {
			rc += m_r.getLanguageDescription();
		}
		rc += "?)";

		return rc;
	}
}