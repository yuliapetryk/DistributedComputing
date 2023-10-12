package TaskA;

public class MyBarrier {
    private int numThreads;
    private int count;

    public MyBarrier(int numThreads) {
        this.numThreads = numThreads;
        this.count = 0;
    }

    public synchronized void waitBarrier() throws InterruptedException {
        count++;
        if (count == numThreads) {
            notifyAll();
        } else {
            while (count < numThreads) {
                wait();
            }
        }
    }
}