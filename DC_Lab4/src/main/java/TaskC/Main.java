package TaskC;
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Bus bus = new Bus();

        Thread changePrice = new Thread(() -> {
            bus.changePrice();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        });

        Thread addOrDeleteRoutes = new Thread(() -> {
            while (true) {
               bus.addOrDeleteRoutes();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread addOrDeleteCities = new Thread(() -> {
            while (true) {
                bus.addOrDeleteCities();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread checkRoute = new Thread(() -> {
            while (true) {
                bus.checkRoute(1,2);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        changePrice.start();
        addOrDeleteRoutes.start();
        addOrDeleteCities.start();
        checkRoute.start();

        changePrice.join();
        addOrDeleteRoutes.join();
        addOrDeleteCities.join();
        checkRoute.join();
    }
}

