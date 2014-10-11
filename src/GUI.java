import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JPanel implements ActionListener {

    JButton chooseTimeSeries;
    JButton drawChart;
    JButton makePrediction;
    JTextArea log;
    JFileChooser fc;

    GUI() {
        super(new BorderLayout());

        //Create the log.
        log = new JTextArea(10,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a file chooser.
        fc = new JFileChooser();

        //Create a buttons.
        chooseTimeSeries = new JButton("Choose Time Series");
        chooseTimeSeries.addActionListener(this);
        drawChart = new JButton("Draw a chart");
        drawChart.addActionListener(this);
        makePrediction = new JButton("Make a prediction");
        makePrediction.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        buttonPanel.add(chooseTimeSeries);
        buttonPanel.add(drawChart);
        buttonPanel.add(makePrediction);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane,  BorderLayout.CENTER);
    }

    File chartGUI;
    Kohanan kohanan;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chooseTimeSeries) {
            fc.showOpenDialog(this);
            chartGUI = fc.getSelectedFile();
            if (chartGUI != null) {
                log.append("Choosing Time Series: " + chartGUI.getName() + ".\n");
                kohanan = new Kohanan(chartGUI.toString());
                log.append("Data is read\n");
            } else {
                log.append("Time Series not choose\n");
            }
        } else if (e.getSource() == drawChart) {
            if (chartGUI != null) {
                XYChart chart = new XYChart("Chart", kohanan.data);
                chart.pack();
                RefineryUtilities.centerFrameOnScreen(chart);
                chart.setVisible(true);
            } else {
                log.append("Time Series not choose\n");
            }
        } else if (e.getSource() == makePrediction) {
            if (chartGUI != null) {
                if (kohanan.answer == true) {
                    log.append("График будет возрастать\n");
                } else {
                    log.append("График будет убывать\n");
                }
            } else {
                log.append("Time Series not choose\n");
            }
        }

    }

    public static void createAndShowGUI() {
        JFrame jFrame = new JFrame("Prediction");
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(1600, 600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.add(new GUI());

        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static void main(String [] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI.createAndShowGUI();
            }
        });


    }

}
