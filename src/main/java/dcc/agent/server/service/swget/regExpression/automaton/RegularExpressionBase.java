package dcc.agent.server.service.swget.regExpression.automaton;

public abstract class RegularExpressionBase extends Element {

	public abstract StateMachine generateStateMachine();

	public String getLanguageDescription() {
		return "";
	}

}