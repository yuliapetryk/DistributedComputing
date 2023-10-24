import java.util.concurrent.Semaphore;

class BusStop {
    private Semaphore availableSeats;

    public BusStop(int limit) {
        availableSeats = new Semaphore(limit, true);
    }

    public boolean tryToStop(String name, int number) {
        if (availableSeats.tryAcquire()) {
            System.out.println(name + " arrived at stop " + number);
            return true;
        } else {
            System.out.println(name + " couldn't stop at stop " + number + ", moving to the next stop");
            return false;
        }
    }

    public void leaveBusStop() {
        availableSeats.release();
    }
}

class Bus implements Runnable {
    private String name;
    private BusStop[] stops;

    public Bus(String name, BusStop[] stops) {
        this.name = name;
        this.stops = stops;
    }

    @Override
    public void run() {
        for (int i = 0; i < stops.length; i++) {
            if (stops[i].tryToStop(name, i)) {
                try {
                    Thread.sleep(100);
                    System.out.println(name + " left the stop " + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                stops[i].leaveBusStop();
            }
        }
    }
}

public class task4Java {
    public static void main(String[] args) {
        int numberOfStops = 5;
        int limit = 3;

        BusStop[] stops = new BusStop[numberOfStops];
        for (int i = 0; i < numberOfStops; i++) {
            stops[i] = new BusStop(limit);
        }

        Thread bus1 = new Thread(new Bus("Bus 1", stops));
        Thread bus2 = new Thread(new Bus("Bus 2", stops));
        Thread bus3 = new Thread(new Bus("Bus 3", stops));
        Thread bus4 = new Thread(new Bus("Bus 4", stops));

        bus1.start();
        bus2.start();
        bus3.start();
        bus4.start();
    }
}
