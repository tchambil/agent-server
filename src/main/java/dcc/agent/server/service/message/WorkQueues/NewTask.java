package dcc.agent.server.service.message.WorkQueues;

/**
 * Created by teo on 26/04/15.
 */
public class NewTask {
/*
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void mammmin(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("agent");
        factory.setPassword("159753");
        factory.setHost("192.168.50.19");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        String message = getMessage(argv);

        channel.basicPublish( "", TASK_QUEUE_NAME,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }*/
}
