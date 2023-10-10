package TaskA;

import java.io.IOException;
import java.util.Random;

public class Main {

    static String[] surnames = {
            "Smith",
            "Johnson",
            "Brown",
            "Taylor",
            "Anderson",
            "Wilson"
    };

    static String[] phoneNumbers = {
            "12335",
            "353453",
            "342346",
            "2124",
            "241242",
            "4213123",
            "43535",
            "785345",
            "223635",
            "64746746"
    };

    public static void main(String[] args) throws InterruptedException {

        Controller controller = new Controller();

        Random random = new Random();

        Thread readName = new Thread(() -> {
            while (true) {
                controller.getNames(phoneNumbers[random.nextInt(10)]);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread readPhones = new Thread(() -> {
            while (true) {
                controller.getPhoneNumbers(surnames[random.nextInt(6)]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread addOrWrite = new Thread(() -> {
            while (true) {

                switch (random.nextInt(2)) {
                    case 0:
                        controller.addRecord(surnames[random.nextInt(6)], phoneNumbers[random.nextInt(10)]);
                        break;

                    case 1:
                        try {
                            controller.removeRecord();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        readName.start();
        readPhones.start();
        addOrWrite.start();

        readName.join();
        readPhones.join();
        addOrWrite.join();
    }
}
