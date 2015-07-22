package dcc.agent.server.service.communication;

/**
 * Created by teo on 27/04/15.
 */

public enum Performative {
    //@formatter:off
    ACCEPT_PROPOSAL("ACCEPT_PROPOSAL","PUT"),
    AGREE("AGREE","PUT"),
    CANCEL("CANCEL","DELETE"),
    CALL_FOR_PROPOSAL("CALL_FOR_PROPOSAL","GET"),
    CONFIRM("CONFIRM","POST"),
    DISCONFIRM("DISCONFIRM","POST"),
    FAILURE("FAILURE","POST"),
    INFORM("INFORM","POST"),
    INFORM_IF("INFORM_IF","POST"),
    INFORM_REF("INFORM_REF","GET"),
    NOT_UNDERSTOOD("NOT_UNDERSTOOD","GET"),
    PROPAGATE("PROPAGATE","GET"),
    PROPOSE("PROPOSE","POST"),
    PROXY("PROXY","POST"),
    QUERY_IF("QUERY_IF","POST"),
    QUERY_REF("QUERY_REF","POST"),
    REFUSE("REFUSE","PUT"),
    REJECT_PROPOSAL("REJECT_PROPOSAL","PUT"),
    REQUEST("REQUEST","POST"),
    REQUEST_WHEN("REQUEST_WHEN","POST"),
    REQUEST_WHENEVER("REQUEST_WHENEVER","POST"),
    SUBSCRIBE("SUBSCRIBE","POST");
        //@formatter:on
    private String performative;
    private String method;

    private Performative(String performative, String method) {
        this.performative=performative;
        this.method=method;
    }
    public String getperformative(){
        return  performative;
    }
    public String getMethod(){
        return method;
    }


}

