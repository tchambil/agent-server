package dcc.agent.server.service.swget.regExpression.automaton;

public class RegExpRepeat extends RegularExpressionBase {
	private RegularExpressionBase m_r;

	public RegExpRepeat(RegularExpressionBase r) {
		m_r = r;
	}

	public StateMachine generateStateMachine() {
		StateMachine rc = null;

		StateMachine pMr = m_r.generateStateMachine();

		rc = StateMachine.createRepeat(pMr, true);

		pMr = null;

		return rc;
	}

	public String getLanguageDescription() {
		String rc = "";

		rc += "(";
		if (m_r != null) {
			rc += m_r.getLanguageDescription();
		}
		rc += "*)";

		return rc;
	}
}