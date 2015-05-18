package dcc.agent.server.service.agentserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@XmlRootElement(name = "agent")
@XmlType(propOrder = {"name", "outputs"})
@JsonPropertyOrder({"name", "outputs"})
public class AgentDefinitions implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    public ArrayList<Outputs> outputs = new ArrayList<Outputs>();

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public ArrayList<Outputs> getOutputs() {
        return outputs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOutputs(ArrayList<Outputs> outputs) {
        this.outputs = outputs;
    }

    @JsonIgnore
    public String toString() {
        return "AgentDefinitions [name=" + name + " , outputs=" + outputs + "]";
    }

    public AgentDefinitions() {
    }

    public AgentDefinitions(String name, String description) {
        this.name = name;
    }

}
