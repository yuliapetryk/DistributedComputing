package JMS.RabbitMQ;

import Database.DatabaseService;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static Data.Product.printProduct;
import static Data.Section.printSection;

public class Recv {

    private final static String QUEUE_NAME = "main_queue";
    private static DatabaseService service;

    public static void main(String[] argv) throws Exception {
        service = new DatabaseService("jdbc:mysql://localhost:3306/shop", "root", "06102003");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                System.out.println(processQuery(message));
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        };

    public static String processQuery(String query) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String[] parameters = query.split("#");
        String command = parameters[0];
        switch (command) {
            case "1" -> {
                service.addSection(Integer.parseInt(parameters[1]), parameters[2]);
                return "Section successfully added";
            }
            case "2" -> {
                service.addProduct(Integer.parseInt(parameters[1]), parameters[2], Integer.parseInt(parameters[3]), Integer.parseInt(parameters[4]));
                return "Product successfully added";
            }
            case "3" -> {
                service.deleteProduct(Integer.parseInt(parameters[1]));
                return "Product successfully deleted";
            }
            case "4" -> {
                return printProduct(service.showProductInSection(Integer.parseInt(parameters[1])));
            }
            case "5" -> {
                return printSection(service.showSections());
            }
        }
        return "";
    }
}