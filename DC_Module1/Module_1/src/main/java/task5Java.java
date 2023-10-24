
import java.util.Random;
import java.util.concurrent.*;

class Cash {
    private final int id;
    final BlockingQueue<Customer> customerQueue = new LinkedBlockingQueue<>();

    public Cash(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void enterQueue(Customer customer) {
        try {
            customerQueue.put(customer);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void leaveQueue(Customer customer) {
        try {
            customerQueue.remove(customer);
        } catch (Exception e) {
            System.out.println("Customer " + customer.getId() + " was not in the queue at Cash " + id);
        }
    }

    public void serveCustomer() {
        try {
            Customer customer = customerQueue.take();
            System.out.println("Cash " + id + " serving customer " + customer.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Customer implements Runnable {
    private final int id;
    private final Cash currentCash;


    private final Cash[] cashiers;

    public Customer(int id, Cash[] cashiers, Cash currentCash) {
        this.id = id;
        this.cashiers = cashiers;
        this.currentCash = currentCash;
    }

    @Override
    public void run() {
        while (true) {
            int newCash = getQueueWithFewerPeopleThanOrdinal();
            if (newCash != this.currentCash.getId() && newCash != -1) {
                this.currentCash.leaveQueue(this);
                System.out.println("Customer " + id + " entered queue at cash " + newCash);
                Cash targetCash = cashiers[newCash];
                targetCash.enterQueue(this);
            }

        }
    }

    private int getQueueWithFewerPeopleThanOrdinal() {
        int minCustomers = Integer.MAX_VALUE;
        int selectedQueue = -1;

        for (int i = 0; i < cashiers.length; i++) {
            int customersInQueue = cashiers[i].customerQueue.size();
            if (customersInQueue < i) {
                if (customersInQueue < minCustomers) {
                    minCustomers = customersInQueue;
                    selectedQueue = i;
                }
            }
        }

        return selectedQueue;
    }

    public int getId() {
        return id;
    }
}

public class task5Java {
    public static void main(String[] args) {
        int numberOfCashiers = 3;
        int numberOfCustomers = 10;
        Random random = new Random();
        Cash[] cashiers = new Cash[numberOfCashiers];
        for (int i = 0; i < numberOfCashiers; i++) {
            cashiers[i] = new Cash(i);
        }

        ExecutorService executor = Executors.newFixedThreadPool(numberOfCustomers);
        for (int i = 0; i < numberOfCustomers; i++) {
            executor.execute(new Customer(i, cashiers,cashiers[random.nextInt(numberOfCashiers)] ));
        }

        for (Cash cashier : cashiers) {
            executor.execute(cashier::serveCustomer);
        }

        executor.shutdown();
    }
}
