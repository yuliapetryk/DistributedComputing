package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public  class Honey {
    public static class Bee implements Runnable {

        private final HoneyPot honeyPot;

        private final Bear bear;

        public Bee(HoneyPot honeyPot, Bear bear) {
            this.honeyPot = honeyPot;
            this.bear = bear;
        }

        @Override
        public void run() {
            while (true) {

                honeyPot.fillHoneyPot();

                if (honeyPot.isFull()) {
                    bear.wakeUpBear();
                }
            }
        }
    }

    public static class HoneyPot {
            private final int capacity;
            private int currentAmount;

            public HoneyPot(int capacity) {
                this.capacity = capacity;
                this.currentAmount = 0;
            }

            public synchronized void fillHoneyPot() {
                while (currentAmount + 1 > capacity) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                try {
                    Thread.sleep(500);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentAmount += 1;
                System.out.println("The bee brought a portion of honey. The total number of: " + currentAmount);

                if (currentAmount == capacity) {
                    notify();
                }
            }

            public synchronized boolean isFull() {
                return capacity == currentAmount;
            }

            public synchronized void eatHoney() {
                try {
                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("The bear ate honey.");
                currentAmount = 0;
                notifyAll();
            }
        }

       public static class Bear implements Runnable {
           private final HoneyPot honeyPot;

           private  boolean isAwake;

           public synchronized void wakeUpBear() {
               isAwake = true;
               notify();
           }

           public Bear(HoneyPot honeyPot) {
               this.honeyPot = honeyPot;
               this.isAwake = false;
           }

           @Override
           public void run() {
               while (true) {
                   synchronized (this) {
                       while (!isAwake){
                           try {
                               wait();
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                       honeyPot.eatHoney();
                       isAwake = false;
                       notifyAll();
                    }
                }
            }
        }

    public static void main(String[] args) {
        int numBees = 3;
        int potCapacity =6;
        HoneyPot   honeyPot  = new HoneyPot(potCapacity);
        ExecutorService executor = Executors.newFixedThreadPool(numBees);
        Bear bear = new Bear(honeyPot);
        new Thread(bear).start();

        for (int i = 0; i < numBees; i++) {
            executor.execute(new Bee(honeyPot, bear));

        }

        executor.shutdown();
    }
}