package dcc.agent.server.service.siebog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by teo on 27/04/15.
 */

public interface AgentManager extends Serializable {
    /**
     * Equivalent to startServerAgent(aid, args, true)
     */
    void startServerAgent(AID aid, AgentInitArgs args);

    void startServerAgent(AID aid, AgentInitArgs args, boolean replace);

    /**
     * Equivalent to startServerAgent(agClass, runtimeName, args, true)
     */
    AID startServerAgent(String runtimeName, AgentInitArgs args);

    AID startServerAgent(String runtimeName, AgentInitArgs args, boolean replace);

    AID startClientAgent(String runtimeName, AgentInitArgs args);

    void stopAgent(AID aid);

    List<AID> getRunningAgents();

    AID getAIDByRuntimeName(String runtimeName);



    void pingAgent(AID aid);
}
