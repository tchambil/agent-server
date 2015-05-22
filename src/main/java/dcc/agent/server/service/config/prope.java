package dcc.agent.server.service.config;

/**
 * Created by teo on 22/05/15.
 */

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class prope {


    public static void main(String[] args)
    {
         Resource resource = new ClassPathResource("app-properties.xml");

        BeanFactory factory = new XmlBeanFactory(resource) ;
        AgentProperties springutil = (AgentProperties) factory.getBean("springUtilProperties");
        springutil.getProperties();
        for (String key :  springutil.getProperties().stringPropertyNames()) {
            String value =  springutil.getProperties().getProperty(key);
            System.out.println(key + " : " + value);

        }

    }
}
