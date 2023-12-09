package JMS.RabbitMQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
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
            showSections();
            addSection("16", "Household chemicals");
            addProduct("77", "Soap", "34", "16");
            showProductInSection("16");


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

    private static void sendRequest(String... requests) throws IOException, ExecutionException, InterruptedException {
        StringBuilder request = new StringBuilder();
        for (String arg : requests) {
            request.append(arg).append("#");
        }
        System.out.println(call(request.toString()));
    }

    public static void addSection(String id, String name) throws IOException, ExecutionException, InterruptedException {
        sendRequest("1", id, name);
    }

    public static void addProduct( String id, String name, String price, String section) throws IOException, ExecutionException, InterruptedException {
        sendRequest("2", id, name, section, price);
    }

    public static void deleteProduct(String id) throws IOException, ExecutionException, InterruptedException {
       sendRequest("3", id);
    }

    public static void showProductInSection(String id) throws IOException, ExecutionException, InterruptedException {
        sendRequest("4", id);
    }

    public static void showSections() throws IOException, ExecutionException, InterruptedException {
        sendRequest("5");
    }
    public static String call(String message) throws IOException, InterruptedException, ExecutionException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", QUEUE_NAME, props, message.getBytes("UTF-8"));

        final CompletableFuture<String> response = new CompletableFuture<>();

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.complete(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        String result = response.get();
        channel.basicCancel(ctag);
        return result;
    }

}