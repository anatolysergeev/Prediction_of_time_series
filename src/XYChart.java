import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;

public class XYChart extends ApplicationFrame {
    Data data;

    public XYChart(final String title, Data data) {
        super(title);
        this.data = data;
        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
        setContentPane(chartPanel);
    }

    private XYDataset createDataset() {
        final XYSeries series = new XYSeries(data.nameClose);
        for (Data.Element x : data.elements)
            series.add(x.id, x.value);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Chart Time Series",      // chart title
                "Period",                 // x axis label
                "Value",                  // y axis label
                dataset,                  //
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        XYPlot xyplot = (XYPlot) chart.getPlot();
        xyplot.setDomainPannable(true);
        xyplot.setRangePannable(false);
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        xyplot.setBackgroundPaint(new Color(187, 225, 255));
        xyplot.setDomainGridlinePaint(new Color(0, 0, 0));
        xyplot.setRangeGridlinePaint(new Color(0, 0, 0));
        xyplot.setDomainCrosshairPaint(new Color(255, 0, 11));
        xyplot.setRangeCrosshairPaint(new Color(255, 0, 11));
        xyplot.getRenderer().setSeriesPaint(0, new Color(55, 1, 255));

        final NumberAxis rangeAxis = new NumberAxis("Value");
        rangeAxis.setAutoRangeIncludesZero(false);
        xyplot.setRangeAxis(rangeAxis);

        return chart;
    }


//    public static void main(final String[] args) {
//
//        final XYChart demo = new XYChart("chart TimeRow", new Data(in));
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//
//    }

//пример диалога окна
//    protected static void initUI() {
//        // First we create the frame and make it visible
//        final XYSeries series = new XYSeries("Price");
//        //final PieChart series = new PieChart("Comparison");
//        series.setSize(500, 270);
//        series.setVisible(true);
//        // Then we display the dialog on that frame
//        final JDialog dialog = new JDialog(series);
//        dialog.setUndecorated(true);
//        JPanel panel = new JPanel();
//        final JLabel label = new JLabel("Please wait...");
//        panel.add(label);
//        dialog.add(panel);
//        dialog.pack();
//        // Public method to center the dialog after calling pack()
//        dialog.setLocationRelativeTo(series);
//
//        // allowing the frame and the dialog to be displayed and, later, refreshed
//        SwingWorker<JFreeChart, String> worker = new SwingWorker<JFreeChart, String>() {
//
//            @Override
//            protected JFreeChart doInBackground() throws Exception {
//                publish("Loading dataset");
//                // simulating the loading of the Dataset
//                try {
//                    System.out.println("Loading dataset");
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                // This will create the dataset
//                PieDataset dataset = series.createDataset();
//                publish("Loading JFreeChart");
//                // simulating the loading of the JFreeChart
//                try {
//                    System.out.println("Loading JFreeChart");
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                // based on the dataset we create the chart
//                JFreeChart chart = series.createChart(dataset, "Which operating system are you using?");
//                // we put the chart into a panel
//                return chart;
//            }
//
//            @Override
//            protected void process(List<String> chunks) {
//                label.setText(chunks.get(0));
//                dialog.pack();
//                dialog.setLocationRelativeTo(series);
//                dialog.repaint();
//            }
//
//            @Override
//            protected void done() {
//                try {
//                    // Retrieve the created chart and put it in a ChartPanel
//                    ChartPanel chartPanel = new ChartPanel(this.get());
//                    // add it to our frame
//                    series.setContentPane(chartPanel);
//                    // Dispose the dialog.
//                    dialog.dispose();
//                    // We revalidate to trigger the layout
//                    series.revalidate();
//                    // Repaint, just to be sure
//                    series.repaint();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        };
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                worker.execute();
//            }
//        });
//        dialog.setVisible(true);
//    }

}
