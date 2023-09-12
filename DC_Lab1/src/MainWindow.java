import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static Thread thread1;
    private static Thread thread2;
    private static Thread firstThread;
    private static  Thread secondThread;
    static int SEMAPHORE = 0;
    private static JSlider slider;
    private static JButton start = new JButton("Start!");
    private static JButton stop = new JButton("Stop!");
    private static JButton buttonStartFirst;
    private static JButton buttonStopFirst;
    private static JButton buttonStartSecond;
    private static JButton buttonStopSecond;
    static  JLabel occupied;
    private static void startThread() {
        thread1.start();
        thread2.start();
        start.setEnabled(false);
        stop.setEnabled(true);

    }
    private static void stopThread() {
        thread1.interrupt();
        thread2.interrupt();
        stop.setEnabled(false);
        start.setEnabled(true);
    }

    private static void startFirstThread(){
        if (SEMAPHORE == 1){
            occupied.setVisible(true);
            return;
        }
        firstThread = new Thread(new ThreadRunnable(-1, slider));
        firstThread.setDaemon(true);
        firstThread.start();
        firstThread.setPriority(1);
        buttonStopFirst.setEnabled(true);
        buttonStartFirst.setEnabled(false);
        SEMAPHORE = 1;
    }

    private static void startSecondThread(){
        if (SEMAPHORE == 1){
            occupied.setVisible(true);
            return;
        }
        secondThread = new Thread(new ThreadRunnable(1, slider));
        secondThread.setDaemon(true);
        secondThread.start();
        secondThread.setPriority(10);
        SEMAPHORE = 1;
        buttonStopSecond.setEnabled(true);
        buttonStartSecond.setEnabled(false);
    }

    private static void stopFirstThread(){

        if (SEMAPHORE== 1) {
        firstThread.interrupt();
        buttonStopFirst.setEnabled(false);
        buttonStartFirst.setEnabled(true);
        occupied.setVisible(false);
        SEMAPHORE =0;
        }

    }

    private static void stopSecondThread(){
        if (SEMAPHORE == 1) {
        secondThread.interrupt();
        buttonStopSecond.setEnabled(false);
        buttonStartSecond.setEnabled(true);
        occupied.setVisible(false);
        SEMAPHORE =0;
        }
    }


    public MainWindow() {
        super("Lab1");
        super.setSize(650, 650);
        super.setLayout(new GridLayout(8, 4, 100, 10));
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container mainWindow = super.getContentPane();

        slider = new JSlider(0, 100, 50);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);
        slider.setPreferredSize(new Dimension(550, slider.getPreferredSize().height));

        thread1 = new Thread(new ThreadRunnable(-1, slider));
        thread2 = new Thread(new ThreadRunnable(1, slider));
        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread1.setPriority(1);
        thread2.setPriority(1);

        JPanel spinnerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JSpinner spinnerLeft = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JSpinner spinnerRight = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spinnerLeft.setPreferredSize(new Dimension(50,100));
        spinnerRight.setPreferredSize(new Dimension(50,100));
        spinnerPanel.add(spinnerLeft);
        spinnerPanel.add(spinnerRight);
        spinnerLeft.addChangeListener(e -> {
            int changedValue = (int) spinnerLeft.getValue();
            thread1.setPriority(changedValue);
            System.out.println(thread1.getPriority());

        });

        spinnerRight.addChangeListener(e -> {
            int changedValue = (int) spinnerRight.getValue();
            thread2.setPriority(changedValue);
            System.out.println(thread2.getPriority());
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        start.setPreferredSize(new Dimension(250, start.getPreferredSize().height));
        start.addActionListener(e -> startThread());
        buttonPanel.add(start);

        stop.setPreferredSize(new Dimension(250, start.getPreferredSize().height));
        stop.addActionListener(e -> stopThread());
        buttonPanel.add(stop);

        mainWindow.add(slider);
        mainWindow.add(spinnerPanel);
        mainWindow.add(buttonPanel);

        JPanel buttonPanelBottom = new JPanel(new GridLayout(2,2));
        buttonStartFirst= new JButton("Start1");
        buttonStopFirst= new JButton("Stop1");
        buttonStartSecond= new JButton("Start2");
        buttonStopSecond= new JButton("Stop2");
        buttonStartFirst.addActionListener(e -> startFirstThread());
        buttonStartSecond.addActionListener(e -> startSecondThread());
        buttonStopFirst.addActionListener(e -> stopFirstThread());
        buttonStopSecond.addActionListener(e -> stopSecondThread());

        buttonPanelBottom.add(buttonStartFirst);
        buttonPanelBottom.add(buttonStopFirst);
        buttonPanelBottom.add(buttonStartSecond);
        buttonPanelBottom.add(buttonStopSecond);
        buttonStopFirst.setEnabled(false);
        buttonStopSecond.setEnabled(false);
        mainWindow.add(buttonPanelBottom);

        occupied = new JLabel("Occupied by another thread");
        mainWindow.add(occupied);
        occupied.setVisible(false);
    }


}