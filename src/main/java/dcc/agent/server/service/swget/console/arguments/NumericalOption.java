package dcc.agent.server.service.swget.console.arguments;

public class NumericalOption extends CommandOption {
	private double _number;

	private String name;

	public NumericalOption(String name, double number) {

		this.name = name;

		_number = number;
	}

	public String toString() {

		return Double.toString(_number);
	}

	public double getValue() {
		return _number;
	}

	public String getName() {
		return name;
	}

}
