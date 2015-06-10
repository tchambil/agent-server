package dcc.agent.server.service.swget.regExpression.automaton;

import dcc.agent.server.service.swget.regExpression.parser.ParseException;
import dcc.agent.server.service.swget.regExpression.parser.PathExpression;
import dcc.agent.server.service.swget.regExpression.parser.TokenMgrError;

public class RegularExpression extends RegularExpressionBase {
	private RegularExpressionBase m_pChildExpression = null;
	private String m_regularExpressionStr = "";

	public RegularExpression() {
	}

	protected boolean buildTree(String regexp) throws ParseException,
			TokenMgrError {
		boolean rc = true;
		int[] index = new int[1];
		index[0] = 0;

		PathExpression pe = new PathExpression(regexp);

		m_pChildExpression = pe.startParsing();

		if (m_pChildExpression == null) {
			rc = false;
		}

		return rc;
	}

	public StateMachine generateStateMachine() {
		StateMachine rc = null;

		if (m_pChildExpression != null) {
			rc = m_pChildExpression.generateStateMachine();
		} else {
			rc = new StateMachine();
		}

		return rc;
	}

	public String getLanguageDescription() {
		String rc;
		if (m_pChildExpression != null) {
			rc = m_pChildExpression.getLanguageDescription();
		} else {
			rc = "<Empty Language>";
		}
		return rc;
	}

	public boolean isEmpty() {
		return (m_pChildExpression == null);
	}

	public boolean setRegularExpression(String regExp) throws ParseException,
			TokenMgrError {
		boolean rc = true;

		setRegularExpressionEmpty();

		m_regularExpressionStr = regExp;

		rc = buildTree(m_regularExpressionStr);

		// if the regular expression is invalid, clean the object
		if (rc == false) {
			setRegularExpressionEmpty();
		}

		return rc;
	}

	public void setRegularExpressionEmpty() {
		if (m_pChildExpression != null) {
			m_pChildExpression = null;
		}
		m_regularExpressionStr = "";
	}
}