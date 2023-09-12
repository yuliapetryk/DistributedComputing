import javax.swing.*;

public class ThreadRunnable extends Thread {

    private final int value;
    private final JSlider slider;
    private  int count;

    public ThreadRunnable(int value, JSlider slider) {
        this.slider = slider;
        this.value = value;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (slider){
                ++count;
                if(count > 10000000) {
                    if (slider.getValue() + value > 10 && slider.getValue() + value < 90) {
                        slider.setValue(slider.getValue() + value);
                        count = 0;

                    }
                }
            }
        }
    }
}
