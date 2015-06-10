package dcc.agent.server.service.swget.console.arguments;

public class StringOption extends CommandOption {

	protected String _value;

	protected String _name;

	public StringOption(String name, String word) {
		_value = word;

		_name = name;
	}

	public String getValue() {
		return _value;
	}

	public String toString() {
		return _value.toString();
	}

	public String getName() {
		return _name;
	}

}
