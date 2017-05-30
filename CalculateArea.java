/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmodel;

import com.sun.org.apache.xalan.internal.XalanConstants;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Locale;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.NumberTickUnitSource;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.DataUtilities;
import org.jfree.data.Value;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import sun.security.krb5.Config;

/**
 *
 * @author st8511x
 */
public class CalculateArea extends javax.swing.JFrame {

    ChartPanel chartPanel;
    JFreeChart chart;

    public CalculateArea() {
        initComponents();
        chartPanel = new ChartPanel(chart);//prevents new graphs from appending
        txtMean.setText("10");
        txtSD.setText("1.2");
    }

    public void displayDefault() {
        Function2D normal = new NormalDistributionFunction2D(0.0, 1.0);
        XYDataset dataset = DatasetUtilities.sampleFunction2D(normal, -5.0, 5.0, 100, "P(x)");

        final JFreeChart chart = ChartFactory.createXYLineChart("XY Series Demo", "X", "Y", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        //setContentPane(chartPanel);
        pnlGraphDisplay.add(chartPanel);

    }

    public void displayGraph() {

        double mean = Double.parseDouble(txtMean.getText());             //user input 1
        double sd = Math.abs(Double.parseDouble(txtSD.getText()));      //user input 2

        /*Modulo equivalents of the above ensure sign doesn't 
         effect final value; magnitude only:*/
        double meanModulo = Math.abs(Double.parseDouble(txtMean.getText()));
        double sdModulo = Math.abs(Double.parseDouble(txtSD.getText()));

        Function2D normal = new NormalDistributionFunction2D(mean, sd); //create relevant graph
        //scale graph
        //RANGE of x-axis, LHS = RHS
        double distance = (4 * sdModulo) + meanModulo;                    //See ALGORITHM #1
        double temp = Math.abs((meanModulo - distance));           //needs to be positive
        double start = meanModulo - temp;                         //start of x-axis (LHS)
        double end = distance;                                   //end of x-axis (RHS)

        //XYDataset -interface via which data formated as x&y can be accessed
        XYDataset dataset = DatasetUtilities.sampleFunction2D(normal, start, end, 100, "P(x)");
        chart = ChartFactory.createXYLineChart("Normal Distribution", "X", "Y", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = (XYPlot) chart.getPlot();

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();    //x-axis
        xAxis.setTickUnit(new NumberTickUnit(sdModulo));              //jumps/increments i.t.o SDs

        ValueAxis range = plot.getRangeAxis();       //y-axis
        range.setVisible(false);                    //y-axis irrelvant to user

        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundPaint(Color.PINK);
        chart.setBorderPaint(new Color(0,0,0,0));
        
        chartPanel.setChart(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 540));//fix sensible graph size
        pnlGraphDisplay.add(chartPanel);

        //colour_AreaGreaterThan(meanModulo, sdModulo, dataset, plot);
        // colour_AreaLessThan(meanModulo, sdModulo, dataset, plot);
        //colour_AreaBetween(meanModulo, sdModulo, dataset, plot);
        colour_AreaEnds(meanModulo, sdModulo, dataset, plot);
    }

    public void colour_AreaGreaterThan(double meanModulo, double sdModulo, XYDataset dataset, XYPlot plot) {

        XYSeries line = new XYSeries("line");//sequence of data items in (x,y) format
        line.add((meanModulo + sdModulo), 0);//vertical line @ limit a
        line.add((meanModulo + (4 * sdModulo)), 0);//vertical line @ limit b
        /*XYSeriesCollection: collection of XYSeries objects
         */
        ((XYSeriesCollection) dataset).addSeries(line); //add the two lines to the exisiting normal dist. graph

        XYDifferenceRenderer renderer = new XYDifferenceRenderer();
        plot.setRenderer(renderer);
    }

    public void colour_AreaLessThan(double meanModulo, double sdModulo, XYDataset dataset, XYPlot plot) {

        XYSeries line = new XYSeries("line");//sequence of data items in (x,y) format
        line.add((meanModulo - (4 * sdModulo)), 0);//vertical line @ limit a
        line.add((meanModulo + sdModulo), 0);//vertical line @ limit b
        /*XYSeriesCollection: collection of XYSeries objects
         */
        ((XYSeriesCollection) dataset).addSeries(line); //add the two lines to the exisiting normal dist. graph

        XYDifferenceRenderer renderer = new XYDifferenceRenderer();
        plot.setRenderer(renderer);
    }

    public void colour_AreaBetween(double meanModulo, double sdModulo, XYDataset dataset, XYPlot plot) {

        XYSeries line = new XYSeries("line");//sequence of data items in (x,y) format
        line.add((meanModulo - sdModulo), 0);//vertical line @ limit a
        line.add((meanModulo + sdModulo), 0);//vertical line @ limit b
        /*XYSeriesCollection: collection of XYSeries objects
         */
        ((XYSeriesCollection) dataset).addSeries(line); //add the two lines to the exisiting normal dist. graph

        XYDifferenceRenderer renderer = new XYDifferenceRenderer();
        plot.setRenderer(renderer);
    }

    public void colour_AreaEnds(double meanModulo, double sdModulo, XYDataset dataset, XYPlot plot) {
        Number no = DatasetUtilities.findMaximumRangeValue(dataset);
        
        XYDifferenceRenderer renderer = new XYDifferenceRenderer(Color.CYAN, Color.CYAN, false);
        renderer.setNegativePaint(new Color(0,0,0,0));
        plot.setRenderer(0,renderer);
        
        plot.getRenderer().setSeriesPaint(0, Color.CYAN);
        plot.getRenderer().setSeriesPaint(1, (new Color(0,0,0,0)));
        plot.setOutlineVisible(false);
        XYSeries line = new XYSeries("line");//sequence of data items in (x,y) format
        
         line.add((meanModulo - (4 * sdModulo)),0);//vertical line @ limit a
        line.add((meanModulo - (2 * sdModulo)), 0);//vertical line @ limit b
        
        line.add((meanModulo - (2 * sdModulo)), no);//vertical line @ limit a
        line.add((meanModulo + (sdModulo)), no);//vertical line @ limit b

        line.add((meanModulo + sdModulo), 0);
        line.add((meanModulo + (4*sdModulo)), 0);//vertical line @ limit a
        
/*XYSeriesCollection: collection of XYSeries objects
         */
        
        ((XYSeriesCollection) dataset).addSeries(line); //add the two lines to the exisiting normal dist. graph

       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField4 = new javax.swing.JTextField();
        txtX2 = new javax.swing.JTextField();
        txtSD = new javax.swing.JTextField();
        txtMean = new javax.swing.JTextField();
        txtX1 = new javax.swing.JTextField();
        btnDisplay = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlGraphDisplay = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculate Area");
        setExtendedState(6);
        setMinimumSize(new java.awt.Dimension(400, 390));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("MV Boli", 0, 24)); // NOI18N
        jTextField4.setText("x>= user input value");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 310, 80));

        txtX2.setText("x2");
        getContentPane().add(txtX2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 50, 90, 30));

        txtSD.setText("1.2");
        getContentPane().add(txtSD, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, 110, 30));

        txtMean.setText("10");
        txtMean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMeanActionPerformed(evt);
            }
        });
        getContentPane().add(txtMean, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 90, 30));

        txtX1.setText("x1");
        getContentPane().add(txtX1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 50, 100, 30));

        btnDisplay.setText("Display");
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayActionPerformed(evt);
            }
        });
        getContentPane().add(btnDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 30, 120, 40));

        jLabel1.setText("SD");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 60, 20));

        jLabel2.setText("MEAN");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 60, 20));

        pnlGraphDisplay.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(pnlGraphDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 990, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void btnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayActionPerformed
    }//GEN-LAST:event_btnDisplayActionPerformed

    private void txtMeanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMeanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMeanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        //w.pnlGraph.add(label);
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalculateArea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalculateArea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalculateArea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalculateArea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new CalculateArea().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnDisplay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField4;
    public javax.swing.JPanel pnlGraphDisplay;
    public javax.swing.JTextField txtMean;
    public javax.swing.JTextField txtSD;
    public javax.swing.JTextField txtX1;
    public javax.swing.JTextField txtX2;
    // End of variables declaration//GEN-END:variables
}
