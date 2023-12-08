package JMS.RabbitMQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String QUEUE_NAME = "main_queue";
    private static Channel channel;
    private static Connection connection;
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //TODO create scenario
            //showSections();
            showProductInSection("1");

        } catch (IOException | TimeoutException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    channel.close();
                    connection.close();
                } catch (IOException | TimeoutException ignored) {
                }
            }
    }

    private static void sendRequest(String... requests) throws IOException {
        StringBuilder request = new StringBuilder();
        for (String arg : requests) {
            request.append(arg).append("#");
        }
        channel.basicPublish("", QUEUE_NAME, null, request.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static void addSection(String id, String name) throws IOException {
        sendRequest("1", id, name);
    }

    public static void addProduct( String id, String name, String price, String section) throws IOException {
        sendRequest("2", id, name, section, price);
    }

    public static void deleteProduct(String id) throws IOException {
       sendRequest("3", id);
    }

    public static void showProductInSection(String id) throws IOException {
        sendRequest("4", id);
    }

    public static void showSections() throws IOException, ExecutionException, InterruptedException {
        sendRequest("5");
    }
}