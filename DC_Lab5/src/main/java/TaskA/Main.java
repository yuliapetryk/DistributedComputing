package TaskA;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int NUMBER_OF_THREADS = 4;

    private static final MyBarrier MY_BARRIER = new MyBarrier(NUMBER_OF_THREADS);

    private static final int NUMBER_OF_RECRUITS = 200;

    private static final int [] recruits = new int[NUMBER_OF_RECRUITS];


    public static void main(String[] args) {

        Random random = new Random();

        for(int i = 0; i < NUMBER_OF_RECRUITS; i++) {
            if(random.nextBoolean()) {
                recruits[i] = 1;
            } else {
                recruits[i] = -1;
            }
        }
        System.out.println("Start: " + Arrays.toString(recruits));

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int startIndex = i * NUMBER_OF_RECRUITS/NUMBER_OF_THREADS;
            int endIndex = startIndex + NUMBER_OF_RECRUITS/NUMBER_OF_THREADS;
            executor.execute(new RecruitsGroup(recruits,  startIndex, endIndex,MY_BARRIER ) );
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < NUMBER_OF_RECRUITS-1; i++) {
            if( recruits[i]!= recruits[i+1]) {
                for (int j = i+1; j < i+1+(NUMBER_OF_RECRUITS / NUMBER_OF_THREADS); j++) {
                    recruits[j] *= -1;
                }
            }

        }

        System.out.println("Finish: " + Arrays.toString(recruits));
    }
}