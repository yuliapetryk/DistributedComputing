import javax.swing.*;

public class ThreadRunnable extends Thread {

    private final int value;
    private final JSlider slider;
    private final int number;
    private  int count;

    public ThreadRunnable( JSlider slider, int number, int value) {
        this.slider = slider;
        this.number= number;
        this.value = value;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (slider){
                ++count;
                if(count > number) {
                    if (slider.getValue() + value > 10 && slider.getValue() + value < 90) {
                        slider.setValue(slider.getValue() + value);
                        count = 0;
                    }
                }
            }
        }
    }
}
