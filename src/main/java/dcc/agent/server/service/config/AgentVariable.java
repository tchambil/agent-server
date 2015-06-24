package dcc.agent.server.service.config;

public class AgentVariable {
    public static final String DEFAULT_PROPERTIES_FILE_PATH = "src/main/resources/agentserver.properties";
    public static final String ALTERNATE_PROPERTIES_FILE_PATH = "local-properties/agentserver.properties";
    public static String propertiesFilePath = DEFAULT_PROPERTIES_FILE_PATH;
    public static final String DEFAULT_PERSISTENT_STORE_DIR = "persistent_store";
    public static final String DEFAULT_PERSISTENT_STORE_FILE_NAME = "agentserver.pjson";
    public static final String DEFAULT_PERSISTENT_STORE_PATH =
            DEFAULT_PERSISTENT_STORE_DIR + "/" + DEFAULT_PERSISTENT_STORE_FILE_NAME;
    public static String persistent_store_dir = DEFAULT_PERSISTENT_STORE_DIR;
    public static final int DEFAULT_APP_SERVER_PORT = 8980;
    public static String DEFAULT_ADMIN_PASSWORD = "abracadabra";
    public static final String DEFAULT_USER_AGENT_NAME = "AgentServer";
    public static final long DEFAULT_MINIMUM_WEB_ACCESS_INTERVAL = 100;
    public static final long DEFAULT_DEFAULT_WEB_PAGE_REFRESH_INTERVAL = 60 * 1000;
    public static final long DEFAULT_MINIMUM_WEB_PAGE_REFRESH_INTERVAL = 60 * 1000;
    public static final long DEFAULT_MINIMUM_WEB_SITE_ACCESS_INTERVAL = 60 * 1000;
    public static final boolean DEFAULT_IMPLICITLY_DENY_WEB_ACCESS = false;
    public static final boolean DEFAULT_IMPLICITLY_DENY_WEB_WRITE_ACCESS = true;



}
