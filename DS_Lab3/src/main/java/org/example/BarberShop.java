package org.example;

import java.util.Queue;
import java.util.concurrent.*;

public class BarberShop {
    private static final Semaphore barberAvailable = new Semaphore(0);
    private static final Semaphore finished = new Semaphore(0);
    private static final  Object barberSynch = new Object();
    private static final Object queueSynch = new Object();
    private static final Queue<Customer> queue = new ConcurrentLinkedQueue<>();

    public static class Barber extends Thread {

        public Barber() {}

        @Override
        public void run() {

            synchronized (barberSynch) {
                while ( !finished.tryAcquire()) {
                    if (queue.isEmpty()) {
                        try {
                            System.out.println("Barber is sleeping");
                            barberAvailable.release();
                            barberSynch.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Customer customer = queue.remove();
                        customer.doHairCut();
                        System.out.println("Customer leaves the barbershop");
                    }
                }
            }
        }
    }

    public static class Customer extends Thread {
        private long id;

        public Customer(int id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void run() {
            synchronized (queueSynch) {
                if (barberAvailable.tryAcquire()) {
                    synchronized (barberSynch) {
                        System.out.println("Customer "+ this.getId() + " wakes up barber");
                        barberSynch.notify();
                        barberAvailable.release();
                   }
                } else {
                    try {
                        System.out.println( "Barber is sleeping");
                        queueSynch.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void doHairCut() {
            System.out.println("Barber cuts a customer's " +this.getId()+ " hair");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

        public static void main(String[] args) {
            int numberOfVisitors = 3;
            Barber barber = new Barber();
            barber.start();
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
                for (int i = 0; i < numberOfVisitors; i++) {
                    Customer customer = new Customer(i);
                    queue.add(customer);
                    System.out.println("Customer " + customer.getId() + " comes to barbershop");
                    customer.start();
                    try {
                        Thread.sleep(7000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }




