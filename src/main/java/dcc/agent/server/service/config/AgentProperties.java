package dcc.agent.server.service.config;


import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by teo on 21/05/15.
 */
@Component
public class AgentProperties {

    public Properties getProperties() {
        return properties;
    }

    Properties properties;
        public void setProperties(Properties properties)
        {
            this.properties = properties;
        }



}
