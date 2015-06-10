package dcc.agent.server.service.swget.actions;

public abstract class AbstractAction implements Action {

	String test;
	String command;
	String name;

	public AbstractAction() {
	}

	@Override
	public void setTest(String test) {
		this.test = test;
	}

	@Override
	public void setCommand(String command) {

		this.command = command;
	}

	@Override
	public String getTest() {
		// TODO Auto-generated method stub
		return test;
	}

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return command;
	}

	@Override
	public void performCommand() {
		// TODO Auto-generated method stub

	}

	public void setName(String name) {

		this.name = name;
	}

	public String getName() {
		return name;
	}

}
