import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static Thread firstThreadA;
    private static Thread secondThreadA;
    private static Thread firstThreadB;
    private static  Thread secondThreadB;
    static int SEMAPHORE = 0;
    private static JSlider slider;
    private static JButton start;
    private static JButton stop;
    private static JButton buttonStartFirst;
    private static JButton buttonStopFirst;
    private static JButton buttonStartSecond;
    private static JButton buttonStopSecond;
    static  JLabel occupied;
    private static void startThread() {
        firstThreadA.start();
        secondThreadA.start();
        start.setEnabled(false);
        stop.setEnabled(true);

    }
    private static void stopThread() {
        firstThreadA.interrupt();
        secondThreadA.interrupt();
        stop.setEnabled(false);
        start.setEnabled(true);
    }

    private static void startFirstThread(){
        if (SEMAPHORE == 1){
            occupied.setVisible(true);
            return;
        }
        firstThreadB = new Thread(new ThreadRunnable( slider, 10000000, -1));
        firstThreadB.start();
        firstThreadB.setPriority(1);
        buttonStopFirst.setEnabled(true);
        buttonStartFirst.setEnabled(false);
        SEMAPHORE = 1;
    }

    private static void startSecondThread(){
        if (SEMAPHORE == 1){
            occupied.setVisible(true);
            return;
        }
        secondThreadB = new Thread(new ThreadRunnable( slider, 10000000,1));
        secondThreadB.start();
        secondThreadB.setPriority(10);
        SEMAPHORE = 1;
        buttonStopSecond.setEnabled(true);
        buttonStartSecond.setEnabled(false);
    }

    private static void stopFirstThread(){

        if (SEMAPHORE== 1) {
            firstThreadB.interrupt();
            buttonStopFirst.setEnabled(false);
            buttonStartFirst.setEnabled(true);
            occupied.setVisible(false);
            SEMAPHORE =0;
        }

    }

    private static void stopSecondThread(){
        if (SEMAPHORE == 1) {
            secondThreadB.interrupt();
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

        firstThreadA = new Thread(new ThreadRunnable( slider, 1000000, -1));
        secondThreadA = new Thread(new ThreadRunnable( slider, 1000000, 1));

        firstThreadA.setPriority(1);
        secondThreadA.setPriority(1);

        JPanel spinnerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JSpinner spinnerLeft = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JSpinner spinnerRight = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spinnerLeft.setPreferredSize(new Dimension(50,100));
        spinnerRight.setPreferredSize(new Dimension(50,100));
        spinnerPanel.add(spinnerLeft);
        spinnerPanel.add(spinnerRight);
        spinnerLeft.addChangeListener(e -> {
            int value = (int) spinnerLeft.getValue();
            firstThreadA.setPriority(value);
        });

        spinnerRight.addChangeListener(e -> {
            int value= (int) spinnerRight.getValue();
            secondThreadA.setPriority(value);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        start = new JButton("Start!");
        start.setPreferredSize(new Dimension(250, start.getPreferredSize().height));
        start.addActionListener(e -> startThread());
        buttonPanel.add(start);

        stop = new JButton("Stop!");
        stop.setPreferredSize(new Dimension(250, start.getPreferredSize().height));
        stop.addActionListener(e -> stopThread());
        stop.setEnabled(false);
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