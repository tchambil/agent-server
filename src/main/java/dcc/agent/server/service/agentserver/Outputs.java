package dcc.agent.server.service.agentserver;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement(name = "outputs")
@XmlType(propOrder = {"name", "type", "default_value"})
@JsonPropertyOrder({"name", "type", "default_value"})
public class Outputs implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    private String default_value;

    public
    @XmlElement
    String getName() {
        return name;
    }

    public
    @XmlElement
    String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public
    @XmlElement
    String getDefault_value() {
        return default_value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    public Outputs() {
    }

    public Outputs(String name, String type, String default_value) {
        this.name = name;
        this.type = type;
        this.default_value = default_value;
    }

}
