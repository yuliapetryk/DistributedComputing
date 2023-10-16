package TaskB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {

    static class ArrayModifier implements Runnable {
        private final List<List<Character>> arrays;

        private final CyclicBarrier barrier;

        private int index;

        public ArrayModifier(List<List<Character>> arrays, CyclicBarrier barrier, int index){
            this.arrays = arrays;
            this.barrier = barrier;
            this.index = index;
        }

        @Override
        public void run() {
            Random rand = new Random();
            while (!sumsAreEqual(arrays,index)) {
                int randIndex = rand.nextInt(arrays.size());

                switch (arrays.get(index).get(randIndex)) {
                    case 'A' -> {
                        arrays.get(index).set(randIndex, 'C');
                    }
                    case 'B' -> {
                        arrays.get(index).set(randIndex, 'D');
                    }
                    case 'C' -> {
                        arrays.get(index).set(randIndex, 'A');
                    }
                    case 'D' -> {
                        arrays.get(index).set(randIndex, 'B');
                    }
                }

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static List<Character> createString(int size) {
        List<Character> array = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            char randomChar = (char) (rand.nextInt(4) + 'A');
            array.add(randomChar);
        }

        return array;
    }

    static void printArrays(List<List<Character>> arrays) {
        for (List<Character> array : arrays) {
            System.out.println(array);
        }
        System.out.println();
    }

    static boolean sumsAreEqual(List<List<Character>> arrays, int index) {
        int[] sums = new int[arrays.size()];

        for (int i = 0; i < arrays.size(); i++) {
            for (int j = 0; j < arrays.get(i).size(); j++) {
                if (Objects.equals(arrays.get(i).get(j), 'A') || Objects.equals(arrays.get(i).get(j), 'B')) {
                    sums[i] += 1;
                }
            }
        }

        if (  (sums[0] == sums[1] && sums[1] == sums[2]) || (sums[1] == sums[2] && sums[2] == sums[3]) || (sums[0] == sums[2] && sums[2] == sums[3]) || (sums[0] == sums[1] && sums[1] == sums[3])){
            System.out.println("Sum of the elements A and B  in  string  " + index +" = " + sums[index]);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 4;
        int sizeOfString = 5;
        CyclicBarrier barrier = new CyclicBarrier(4);

        List<List<Character>> arrays = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            arrays.add(createString(sizeOfString));
        }
        printArrays(arrays);

        Thread thread1=    new Thread(new ArrayModifier(arrays, barrier, 0));
        Thread thread2=  new Thread(new ArrayModifier(arrays, barrier, 1));
        Thread thread3= new Thread(new ArrayModifier(arrays, barrier,2));
        Thread thread4=  new Thread(new ArrayModifier(arrays, barrier,3));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        printArrays(arrays);

    }
}
