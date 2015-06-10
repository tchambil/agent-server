package dcc.agent.server.service.swget.actions;

/**
 * This is a generic interface that each action has to implements
 * 
 * @author gpirro
 * 
 */
public interface Action {

	public void setTest(String test);

	public void setCommand(String command);

	public String getTest();

	public String getCommand();

	public void performCommand();

	public void setName(String name);

	public String getName();

}
