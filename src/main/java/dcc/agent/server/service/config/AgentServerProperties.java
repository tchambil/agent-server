package dcc.agent.server.service.config;

import dcc.agent.server.service.agentserver.AgentDefinition;
import dcc.agent.server.service.agentserver.AgentInstance;
import dcc.agent.server.service.agentserver.AgentServerException;
import dcc.agent.server.service.agentserver.User;
import dcc.agent.server.service.mailaccessmanager.MailAccessManager;
import dcc.agent.server.service.script.runtime.ScriptState;
import dcc.agent.server.service.util.ListMap;
import dcc.agent.server.service.webaccessmanager.WebAccessManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class AgentServerProperties {



    static final Logger log = Logger.getLogger(AgentServerProperties.class);
    public static final String Rabbitmq_Username = "agent";
    public static final String Rabbitmq_HostLocal = getHostAddress().toString();
    public static final String Rabbitmq_HostPublic = "";
    public static final String Rabbitmq_HostExtern="uchile.cloudapp.net";
    public static final String Rabbitmq_Password = "159753";
    public static final String Rabbitmq_QueueName = "queue";

    public static final String agentServerIP = getHostAddress();
    public static final String agentServerHostName=getHostName();

    public static final String DEFAULT_PROPERTIES_FILE_PATH = "agentserver.properties";
    public static final String ALTERNATE_PROPERTIES_FILE_PATH = "agentserver.properties";
    public String propertiesFilePath = DEFAULT_PROPERTIES_FILE_PATH;
    public static final String DEFAULT_PERSISTENT_STORE_DIR = "./persistent_store";
    public static final String DEFAULT_PERSISTENT_STORE_FILE_NAME = "agentserver.pjson";
    public static final String DEFAULT_PERSISTENT_STORE_PATH =
            DEFAULT_PERSISTENT_STORE_DIR + "/" + DEFAULT_PERSISTENT_STORE_FILE_NAME;
    public String persistent_store_dir = DEFAULT_PERSISTENT_STORE_DIR;
    public static final int DEFAULT_APP_SERVER_PORT = 8980;
    public int appServerPort;
    public String agentServerName;
    public String agentServerDescription;
    public static String DEFAULT_ADMIN_PASSWORD = "abracadabra";
    public String adminPassword;
    public String mailServerHostName;
    public String mailServerUserName;
    public String mailServerUserPassword;
    public int mailServerPort;
    public String mailServerFromEmail;
    public String mailServerFromName;
    public String testUserEmail;
    public String testUserName;
    public String supportContactEmail;
    public String website;
    public String adminApproveUserCreate;
    public String mailConfirmUserCreate;
    public String userAgentName;
    public String defaultWebPageRefreshInterval;
    public String minimumWebPageRefreshInterval;
    public String minimumWebSiteAccess_interval;
    public String minimumWebAccessInterval;
    public String execution_limit_level_1;
    public String executionLimitLevel2;
    public String executionLimitLevel3;
    public String executionLimitLevel4;
    public String executionLimitDefaultLevel;
    public String maxUsers;
    public String maxInstances;
    public String implicitlyDenyWebAccess;
    public String implicitlyDenyWebWriteAccess;
    public String defaultTriggerInterval;
    public String defaultReportingInterval;
    public String minimumTriggerInterval;
    public String minimumReportingInterval;
    public String mailAccessEnabled;
    public String minimumMailAccessInterval;
    public String minimumHostMailAccessInterval;
    public String minimumAddressMailAccessInterval;
    public String maximumLimitInstanceStatesStored;
    public String defaultLimitInstanceStatesStored;
    public String maximumLimitInstanceStatesReturned;
    public String defaultLimitInstanceStatesReturned;

    public ListMap<String, String> commandLineProperties;
    public Properties properties;



    public AgentServerProperties() throws AgentServerException {

     this(new ListMap<String, String>());
    }

    public AgentServerProperties(ListMap<String, String> commandLineProperties) throws AgentServerException {
       this.commandLineProperties = commandLineProperties;
       loadProperties();
    }

public void load() throws AgentServerException {
    agentServerName = getProperty("agent_server_name");
   log.info(agentServerName);
    if (agentServerName == null || agentServerName.trim().length() == 0)
        throw new AgentServerException("You must edit agentserver.properties (or set the agentserver_properties_path environment variable to point to another file) - agent_server_name is blank");

}
    public void loadProperties() throws AgentServerException {
        // Find the agent server properties file
        // If user has defined the 'agentserver_properties_path' environment variable, use it
        // If it exists as local-properties/agentserver.properties, use it
        // Else, use agentserver.properties in the current working directory
        Resource resource = new ClassPathResource("app-properties.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
        AgentProperties springutil = (AgentProperties) factory.getBean("springUtilProperties");
        properties = new Properties();
        properties =(springutil.getProperties());
        persistent_store_dir = getProperty("persistent_store_dir");
        if (persistent_store_dir == null || persistent_store_dir.trim().length() == 0)
            persistent_store_dir = DEFAULT_PERSISTENT_STORE_DIR;

        String portString = getProperty("app_server_port");
        appServerPort = portString == null || portString.trim().length() == 0 ? DEFAULT_APP_SERVER_PORT :
                Integer.parseInt(portString);

      //  agentServerName = getProperty("agent_server_name");
        if (agentServerName == null || agentServerName.trim().length() == 0)
        agentServerName=agentServerHostName;
        //    throw new AgentServerException("You must edit agentserver.properties (or set the agentserver_properties_path environment variable to point to another file) - agent_server_name is blank");
        agentServerDescription = getProperty("agent_server_description");
        log.info("agentServerDescription: " + agentServerDescription);
        adminPassword = getProperty("admin_password");
        if (adminPassword == null || adminPassword.trim().length() == 0)
            adminPassword = DEFAULT_ADMIN_PASSWORD;
        mailServerHostName = getProperty("mail_server_host_name");
        mailServerUserName = getProperty("mail_server_user_name");
        mailServerUserPassword = getProperty("mail_server_user_password");
        portString = getProperty("mail_server_port");
        mailServerPort = portString == null || portString.trim().length() == 0 ? 587 :
                Integer.parseInt(portString);
        mailServerFromEmail = getProperty("mail_server_from_email");
        mailServerFromName = getProperty("mail_server_from_name");
        testUserEmail = getProperty("test_user_email");
        testUserName = getProperty("test_user_name");
        supportContactEmail = getProperty("support_contact_email");
        website = getProperty("website");
        adminApproveUserCreate = getProperty("admin_approve_user_create");
        if (adminApproveUserCreate == null || adminApproveUserCreate.trim().length() == 0)
            adminApproveUserCreate = Boolean.toString(User.DEFAULT_ADMIN_ONLY_USER_CREATE);
        mailConfirmUserCreate = getProperty("mail_confirm_user_create");
        if (mailConfirmUserCreate == null || mailConfirmUserCreate.trim().length() == 0)
            mailConfirmUserCreate = Boolean.toString(User.DEFAULT_ADMIN_ONLY_USER_CREATE);
        userAgentName = getProperty("user_agent_name");
        if (userAgentName == null || userAgentName.trim().length() == 0)
            userAgentName = WebAccessManager.DEFAULT_USER_AGENT_NAME;
        defaultWebPageRefreshInterval = getProperty("default_web_page_refresh_interval");
        if (defaultWebPageRefreshInterval == null || defaultWebPageRefreshInterval.trim().length() == 0)
            defaultWebPageRefreshInterval = Long.toString(WebAccessManager.DEFAULT_DEFAULT_WEB_PAGE_REFRESH_INTERVAL);
        minimumWebPageRefreshInterval = getProperty("minimum_web_page_refresh_interval");
        if (minimumWebPageRefreshInterval == null || minimumWebPageRefreshInterval.trim().length() == 0)
            minimumWebPageRefreshInterval = Long.toString(WebAccessManager.DEFAULT_MINIMUM_WEB_PAGE_REFRESH_INTERVAL);
        minimumWebSiteAccess_interval = getProperty("minimum_web_site_access_interval");
        if (minimumWebSiteAccess_interval == null || minimumWebSiteAccess_interval.trim().length() == 0)
            minimumWebSiteAccess_interval = Long.toString(WebAccessManager.DEFAULT_MINIMUM_WEB_SITE_ACCESS_INTERVAL);
        minimumWebAccessInterval = getProperty("minimum_web_access_interval");
        if (minimumWebAccessInterval == null || minimumWebAccessInterval.trim().length() == 0)
            minimumWebAccessInterval = Long.toString(WebAccessManager.DEFAULT_MINIMUM_WEB_ACCESS_INTERVAL);
        execution_limit_level_1 = getProperty("execution_limit_level_1");
        if (execution_limit_level_1 == null || execution_limit_level_1.trim().length() == 0)
            execution_limit_level_1 = Long.toString(ScriptState.NODE_EXECUTION_LEVEL_1_LIMIT);
        executionLimitLevel2 = getProperty("execution_limit_level_2");
        if (executionLimitLevel2 == null || executionLimitLevel2.trim().length() == 0)
            executionLimitLevel2 = Long.toString(ScriptState.NODE_EXECUTION_LEVEL_2_LIMIT);
        executionLimitLevel3 = getProperty("execution_limit_level_3");
        if (executionLimitLevel3 == null || executionLimitLevel3.trim().length() == 0)
            executionLimitLevel3 = Long.toString(ScriptState.NODE_EXECUTION_LEVEL_3_LIMIT);
        executionLimitLevel4 = getProperty("execution_limit_level_4");
        if (executionLimitLevel4 == null || executionLimitLevel4.trim().length() == 0)
            executionLimitLevel4 = Long.toString(ScriptState.NODE_EXECUTION_LEVEL_4_LIMIT);
        executionLimitDefaultLevel = getProperty("execution_limit_default_level");
        if (executionLimitDefaultLevel == null || executionLimitDefaultLevel.trim().length() == 0)
            executionLimitDefaultLevel = Long.toString(ScriptState.DEFAULT_EXECUTION_LEVEL);
        maxUsers = getProperty("max_users");
        if (maxUsers == null || maxUsers.trim().length() == 0)
            maxUsers = Integer.toString(User.DEFAULT_MAX_USERS);
        maxInstances = getProperty("max_instances");
        if (maxInstances == null || maxInstances.trim().length() == 0)
            maxInstances = Integer.toString(AgentInstance.DEFAULT_MAX_INSTANCES);

        implicitlyDenyWebAccess = getProperty("implicitly_deny_web_access");
        if (implicitlyDenyWebAccess == null || implicitlyDenyWebAccess.trim().length() == 0)
            implicitlyDenyWebAccess = WebAccessManager.DEFAULT_IMPLICITLY_DENY_WEB_ACCESS ? "true" : "false";

        implicitlyDenyWebWriteAccess = getProperty("implicitly_deny_web_write_access");
        if (implicitlyDenyWebWriteAccess == null || implicitlyDenyWebWriteAccess.trim().length() == 0)
            implicitlyDenyWebWriteAccess = WebAccessManager.DEFAULT_IMPLICITLY_DENY_WEB_WRITE_ACCESS ? "true" : "false";

        defaultTriggerInterval = getProperty("default_trigger_interval",
                AgentDefinition.DEFAULT_TRIGGER_INTERVAL_EXPRESSION);
        defaultReportingInterval = getProperty("default_reporting_interval",
                AgentDefinition.DEFAULT_REPORTING_INTERVAL_EXPRESSION);
        minimumTriggerInterval = getProperty("minimum_trigger_interval",
                AgentDefinition.DEFAULT_MINIMUM_TRIGGER_INTERVAL_EXPRESSION);
        minimumReportingInterval = getProperty("minimum_reporting_interval",
                AgentDefinition.DEFAULT_MINIMUM_REPORTING_INTERVAL_EXPRESSION);

        mailAccessEnabled = getProperty("mail_access_enabled",
                Boolean.toString(MailAccessManager.DEFAULT_MAIL_ACCESS_ENABLED));
        minimumMailAccessInterval = getProperty("minimum_mail_access_interval",
                Long.toString(MailAccessManager.DEFAULT_MINIMUM_MAIL_ACCESS_INTERVAL));
        minimumHostMailAccessInterval = getProperty("minimum_host_mail_access_interval",
                Long.toString(MailAccessManager.DEFAULT_MINIMUM_HOST_MAIL_ACCESS_INTERVAL));
        minimumAddressMailAccessInterval = getProperty("minimum_address_mail_access_interval",
                Long.toString(MailAccessManager.DEFAULT_MINIMUM_ADDRESS_MAIL_ACCESS_INTERVAL));

        defaultLimitInstanceStatesStored = getProperty("default_limit_instance_states_stored",
                Integer.toString(AgentInstance.DEFAULT_LIMIT_INSTANCE_STATES_STORED));
        maximumLimitInstanceStatesStored = getProperty("maximum_limit_instance_states_stored",
                Integer.toString(AgentInstance.DEFAULT_MAXIMUM_LIMIT_INSTANCE_STATES_STORED));

        defaultLimitInstanceStatesReturned = getProperty("default_limit_instance_states_returned",
                Integer.toString(AgentInstance.DEFAULT_LIMIT_INSTANCE_STATES_RETURNED));
        maximumLimitInstanceStatesReturned = getProperty("maximum_limit_instance_states_returned",
                Integer.toString(AgentInstance.DEFAULT_MAXIMUM_LIMIT_INSTANCE_STATES_RETURNED));

    }


    public String getProperty(String key, String defaultValue){



        // Get the named property but default its value if missing or empty
        String property = getProperty(key);
        if (property == null || property.trim().length() == 0)
            return defaultValue;
        else
            return property;
    }

    public String getProperty(String key){
              // First check for a command line override property value
        String commandLineProperty = commandLineProperties == null ? null : commandLineProperties.get(key);
        if (commandLineProperty != null)
            return commandLineProperty;

        // Then check for an environment variable override property value
        String envProperty = System.getenv(key);
        if (envProperty != null)
            return envProperty;

        // Otherwise, return the property from the properties list
        return properties.getProperty(key);
    }
    public static String getHostName() {

        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTeo(String teo) {
    }
}
