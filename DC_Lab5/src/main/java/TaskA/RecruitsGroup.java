package TaskA;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecruitsGroup implements Runnable {
    private static final AtomicBoolean isFinished = new AtomicBoolean(false);

    private final int[] recruits;

    private final MyBarrier myBarrier;

    private final int leftIndex;

    private final int rightIndex;

    private boolean isFinishedPart;

    public RecruitsGroup(int[] recruits, int leftIndex, int rightIndex, MyBarrier myBarrier) {

        this.isFinishedPart = false;
        this.recruits = recruits;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
        this.myBarrier = myBarrier;
    }

    public void run() {
        while (!isFinished.get()) {

            if (!isFinishedPart) {

                isFinishedPart = true;
                for (int i = leftIndex; i < rightIndex - 1; i++) {
                    if (recruits[i] != recruits[i+1]){
                        recruits[i] *= -1;
                        isFinishedPart = false;
                    }
                }
                if(isFinishedPart) {
                    System.out.println("Part " + ((leftIndex/(rightIndex-leftIndex))+1) + " is sorted.");
                    isFinishedPart=true;
                    isFinished.set(true);
                }
            }

            try {
                myBarrier.waitBarrier();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}