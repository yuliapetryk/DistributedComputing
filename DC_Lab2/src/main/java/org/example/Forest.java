package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Forest {
    private static final int NUM_DIVISIONS = 10;
    private static final int NUM_BEES = (int)NUM_DIVISIONS/3;
    private final static Random random = new Random();
    private static List<List<Boolean>> divisions;
    private static final MySynchronizer  founded = new MySynchronizer (0);
    private static final MySynchronizer  nextDivision = new MySynchronizer (-1);

    private class Bees extends Thread {
        @Override
        public void run() {
            while(founded.getValue()==0 && nextDivision.getValue()<NUM_DIVISIONS-1) {
                nextDivision.setValue(nextDivision.getValue() + 1);
                searchForWinniePooh(nextDivision.getValue());
            }
        }
    }

    private Thread[] threads;

    private void startThreads(){

        for(int i = 0; i < NUM_BEES; i++) {
            threads[i] = new Bees();
            threads[i].start();
        }

        for(int i = 0; i < NUM_BEES; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        divisions = new ArrayList<>();

        for (int i = 0; i < NUM_DIVISIONS; i++) {
            List<Boolean> division = new ArrayList<>();
            for (int j = 0; j < NUM_DIVISIONS; j++){
                division.add(false);}
            divisions.add(division);
        }

        divisions.get(random.nextInt(NUM_DIVISIONS-1)).set(random.nextInt(NUM_DIVISIONS-1),true);
        Forest forest = new Forest();
        System.out.println(NUM_BEES);
        System.out.println(NUM_DIVISIONS);
        forest.threads = new Thread[NUM_BEES];
        forest.startThreads();

    }
    private void searchForWinniePooh(int division) {
        if(founded.getValue()==1) { return; }

        String threadName = Thread.currentThread().getName();
        int threadNumber = Character.getNumericValue(threadName.charAt(threadName.length() - 1));

        System.out.println("Swarm "+threadNumber + " is working on division " + division);

        List<Boolean> section = divisions.get(division);
        if (section.contains(true)){
            System.out.println("Winnie Pooh was found in "+ division + " part of forest");
            founded.setValue(1);
        }
    }
    private static class MySynchronizer  {
        private int value;
        public MySynchronizer (int value) {
            this.value = value;
        }
        public synchronized int getValue() {
            return value;
        }
        public synchronized void setValue(int value) {
            this.value = value;
        }
    }
}

