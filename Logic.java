/**
 * * Created 27th October 2016
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package statsmodel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;

/**
 * Created 27th October 2016
 *
 * @author Milu
 */

/*LOGIC LAYER, forms and classes shall be handeled from here*/
public class Logic implements ActionListener {

    NBGraphDemo Nb = new NBGraphDemo("sds");
    //INITIALISE ALL INSTANCE VARIBLES -OBJECTS
    XYDataset dataset;
    XYPlot plot;
    double lowerBound, upperBound;
    Welcome w = new Welcome();//Main form

    CalculateArea cA = new CalculateArea();

    frmGreaterThan greaterThan = new frmGreaterThan();
    frmLessThan lessThan = new frmLessThan();
    frmBetween between = new frmBetween();
    frmExtremities ends = new frmExtremities();
    ZTable zTable = new ZTable();

    NumericalMethods nM = new NumericalMethods();
    WorkingOut wO = new WorkingOut();

    public Logic() {

        w.setVisible(true);

        cA.btnDisplay.addActionListener(this);

        w.btnBetween.addActionListener(this);
        w.btnEnds.addActionListener(this);
        w.btnGreaterThan.addActionListener(this);
        w.btnLessThan.addActionListener(this);

        greaterThan.btnDisplay.addActionListener(this);
        lessThan.btnDisplay.addActionListener(this);
        between.btnDisplay.addActionListener(this);
        ends.btnDisplay.addActionListener(this);

        greaterThan.btnSimpForCurve.addActionListener(this);
        greaterThan.btnTrapForCurve.addActionListener(this);
        greaterThan.btnSimpForTable.addActionListener(this);//Ztable button clicks
        greaterThan.btnTrapForTable.addActionListener(this);

        lessThan.btnSimpForCurve.addActionListener(this);
        lessThan.btnTrapForCurve.addActionListener(this);
        lessThan.btnSimpForTable.addActionListener(this);//Ztable button clicks
        lessThan.btnTrapForTable.addActionListener(this);

        between.btnSimpForCurve.addActionListener(this);
        between.btnTrapForCurve.addActionListener(this);
        between.btnSimpForTable.addActionListener(this);//Ztable button clicks
        between.btnTrapForTable.addActionListener(this);

        greaterThan.btnWorking.addActionListener(this);
        lessThan.btnWorking.addActionListener(this);

    }

    public static void main(String args[]) {
        Logic logic = new Logic();//pls understand the IMPORTANCE OF THIS 
        // SwingUtilities.getRootPane(w.btnOpenCA).setDefaultButton(w.btnOpenCA);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == cA.btnDisplay) {
            showGraph();
        } else if (e.getSource() == w.btnGreaterThan) {
            greaterThan.setVisible(true);
        } else if (e.getSource() == w.btnLessThan) {
            lessThan.setVisible(true);
        } else if (e.getSource() == w.btnBetween) {
            between.setVisible(true);
        } else if (e.getSource() == w.btnEnds) {
            ends.setVisible(true);
        } else if (e.getSource() == greaterThan.btnDisplay) {
            greaterThanX();
        } else if (e.getSource() == lessThan.btnDisplay) {
            lessThanX();
        } else if (e.getSource() == between.btnDisplay) {
            betweenTwoXs();
        } else if (e.getSource() == ends.btnDisplay) {
            extremities();
        } //MORE THAN GRAPH AREA
        else if (e.getSource() == greaterThan.btnSimpForCurve) {
            greaterThan.showGraphArea(this, greaterThan.btnSimpForCurve, true);
        } else if (e.getSource() == greaterThan.btnTrapForCurve) {
            greaterThan.showGraphArea(this, greaterThan.btnTrapForCurve, false);
        } //LESS THAN GRAPH AREA
        else if (e.getSource() == lessThan.btnSimpForCurve) {
            lessThan.showGraphArea(this, lessThan.btnSimpForCurve, true);
        } else if (e.getSource() == lessThan.btnTrapForCurve) {
            lessThan.showGraphArea(this, lessThan.btnTrapForCurve, false);
        } //BETWEEN GRAPH AREA
         else if (e.getSource() == between.btnSimpForCurve) {
            between.showGraphArea(this, between.btnSimpForCurve, true);
        } else if (e.getSource() == between.btnTrapForCurve) {
            between.showGraphArea(this, between.btnTrapForCurve, false);
        }
        
        
        
        
        else if (e.getSource() == greaterThan.btnSimpForTable) {
            greaterThan.setVisible(false);
            greaterThan.pnlZTable.removeAll();
            double start = parseUserInputData(greaterThan.txtZStart.getText());
            double end = parseUserInputData(greaterThan.txtZEnd.getText());
            greaterThan.pnlZTable.add(zTable.getZTable(true, start, end));
            greaterThan.setVisible(true);

            // getZTableUsingSimpsons(greaterThan);
        } else if (e.getSource() == greaterThan.btnTrapForTable) {
            greaterThan.setVisible(false);
            greaterThan.pnlZTable.removeAll();
            double start = parseUserInputData(greaterThan.txtZStart.getText());
            double end = parseUserInputData(greaterThan.txtZEnd.getText());
            greaterThan.pnlZTable.add(zTable.getZTable(false, start, end));
            greaterThan.setVisible(true);

        } else if (e.getSource() == lessThan.btnSimpForTable) {
            //calculateArea(greaterThan);//displays area according to numerical method selected
            lessThan.setVisible(false);
            lessThan.pnlZTable.removeAll();
            double start = parseUserInputData(lessThan.txtZStart.getText());
            double end = parseUserInputData(lessThan.txtZEnd.getText());
            lessThan.pnlZTable.add(zTable.getZTable(true, start, end));
            lessThan.setVisible(true);

        } else if (e.getSource() == lessThan.btnTrapForTable) {
            //calculateArea(greaterThan);//displays area according to numerical method selected
            lessThan.setVisible(false);
            lessThan.pnlZTable.removeAll();
            double start = parseUserInputData(lessThan.txtZStart.getText());
            double end = parseUserInputData(lessThan.txtZEnd.getText());
            lessThan.pnlZTable.add(zTable.getZTable(false, start, end));
            lessThan.setVisible(true);

        } 
        else if (e.getSource() == between.btnSimpForTable) {
            //calculateArea(greaterThan);//displays area according to numerical method selected
            between.setVisible(false);
            between.pnlZTable.removeAll();
            double start = parseUserInputData(between.txtZStart.getText());
            double end = parseUserInputData(between.txtZEnd.getText());
            between.pnlZTable.add(zTable.getZTable(true, start, end));
            between.setVisible(true);

        } else if (e.getSource() == between.btnTrapForTable) {
            //calculateArea(greaterThan);//displays area according to numerical method selected
            between.setVisible(false);
            between.pnlZTable.removeAll();
            double start = parseUserInputData(between.txtZStart.getText());
            double end = parseUserInputData(between.txtZEnd.getText());
            between.pnlZTable.add(zTable.getZTable(false, start, end));
            between.setVisible(true);

        }
                      else if (e.getSource() == greaterThan.btnWorking
                || e.getSource() == lessThan.btnWorking) {//if the user wants to see the working out 
            wO.setVisible(true);
        }

    }

    public void greaterThanX() {

        greaterThan.setVisible(false);

        double mean = parseUserInputData(greaterThan.txtMean.getText());
        double sd = parseUserInputData(greaterThan.txtSD.getText());
        double xOne = parseUserInputData(greaterThan.txtX1.getText());
        if (is_SD_positive(sd)) {
            JOptionPane.showMessageDialog(null,
                    "Standard Deviation can only be positive.",
                    "Invalid put",
                    JOptionPane.ERROR_MESSAGE);

            greaterThan.setVisible(true);

        } else if (checkUserInput(xOne, mean, sd)) {
            displayGraph(
                    mean,
                    sd,
                    greaterThan.chart,
                    greaterThan.chartPanel,
                    greaterThan.pnlGraphDisplay);

            greaterThan.colour_AreaGreaterThan(mean, sd, xOne, dataset, plot);
            greaterThan.setVisible(true);
        }

    }

    public void lessThanX() {
        lessThan.setVisible(false);

        double mean = parseUserInputData(lessThan.txtMean.getText());
        double sd = parseUserInputData(lessThan.txtSD.getText());
        double xOne = parseUserInputData(lessThan.txtX1.getText());
        if (is_SD_positive(sd)) {
            JOptionPane.showMessageDialog(null,
                    "Standard Deviation can only be positive.",
                    "Invalid put",
                    JOptionPane.ERROR_MESSAGE);

            lessThan.setVisible(true);

        } else if (checkUserInput(xOne, mean, sd)) {

            displayGraph(
                    mean,
                    sd,
                    lessThan.chart,
                    lessThan.chartPanel,
                    lessThan.pnlGraphDisplay);

            lessThan.colour_AreaLessThan(mean, sd, xOne, dataset, plot);
            lessThan.setVisible(true);
        }
    }

    public void betweenTwoXs() {
        between.setVisible(false);

        double mean = parseUserInputData(between.txtMean.getText());
        double sd = parseUserInputData(between.txtSD.getText());
        double xOne = parseUserInputData(between.txtX1.getText());
        double xTwo = parseUserInputData(between.txtX2.getText());
        if (is_SD_positive(sd)) {
            JOptionPane.showMessageDialog(null,
                    "Standard Deviation can only be positive.",
                    "Invalid put",
                    JOptionPane.ERROR_MESSAGE);

            between.setVisible(true);
            between.setVisible(true);

        } else if (checkUserInput(xOne, mean, sd) && checkUserInput(xTwo, mean, sd)) {
            displayGraph(
                    mean,
                    sd,
                    between.chart,
                    between.chartPanel,
                    between.pnlGraphDisplay);

            between.colour_AreaBetween(mean, sd, dataset, plot, xOne, xTwo);

            between.setVisible(true);
        }
    }

    public void extremities() {
        ends.setVisible(false);

        double mean = parseUserInputData(ends.txtMean.getText());
        double sd = parseUserInputData(ends.txtSD.getText());
        double xOne = parseUserInputData(ends.txtX1.getText());
        double xTwo = parseUserInputData(ends.txtX2.getText());
        if (is_SD_positive(sd)) {
            JOptionPane.showMessageDialog(null,
                    "Standard Deviation can only be positive.",
                    "Invalid put",
                    JOptionPane.ERROR_MESSAGE);

            ends.setVisible(true);
            ends.setVisible(true);

        } else if (checkUserInput(xOne, mean, sd)
                && checkUserInput(xTwo, mean, sd)) {
            displayGraph(
                    mean,
                    sd,
                    ends.chart,
                    ends.chartPanel,
                    ends.pnlGraphDisplay);

            ends.colour_AreaEnds(mean, sd, dataset, plot, xOne, xTwo);

            ends.setVisible(true);

        }
    }

    public void showGraph() {
        cA.setVisible(false);//so that graph is updated
        cA.displayGraph();
        cA.setVisible(true);//
    }

    public void displayGraph(
            double mean,
            double sd,
            JFreeChart chart,
            ChartPanel chartPanel,
            JPanel pnlGraphDisplay) {

        Function2D normal = new NormalDistributionFunction2D(mean, sd); //create relevant graph
        //scale graph
        //RANGE of x-axis, LHS = RHS
        double distance = (4 * sd) + mean;                    //See ALGORITHM #1
        double temp = Math.abs((mean - distance));           //needs to be positive
        double start = mean - temp;                         //start of x-axis (LHS)
        double end = distance;                                   //end of x-axis (RHS)

        //XYDataset -interface via which data formated as x&y can be accessed
        dataset = DatasetUtilities.sampleFunction2D(normal, start, end, 100, "P(x)");
        chart = ChartFactory.createXYLineChart(null, null, null, dataset,
                PlotOrientation.VERTICAL, true, true, false);

        plot = (XYPlot) chart.getPlot();
        XYPlot plot = chart.getXYPlot();

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();    //x-axis
        xAxis.setTickUnit(new NumberTickUnit(sd));              //jumps/increments i.t.o SDs

        ValueAxis range = plot.getRangeAxis();       //y-axis
        range.setVisible(false);                    //y-axis irrelvant to user

        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        //plot.setBackgroundPaint(new Color(240,240,240,100));
        //plot.setBackgroundPaint(Color.GRAY);
        plot.setBackgroundPaint(Color.WHITE);

        //chart.setBorderPaint(new Color(240,240,240,0));
        plot.setOutlineVisible(false);
        chartPanel.setChart(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(630, 420));//fix sensible graph size
        chartPanel.getChart().removeLegend();

        pnlGraphDisplay.add(chartPanel);

        // greaterThan.colour_AreaGreaterThan(mean, sd, dataset, plot);
        // colour_AreaLessThan(mean, sd, dataset, plot);
        //colour_AreaBetween(mean, sd, dataset, plot);
    }

    public double parseUserInputData(String value) {
        double valueAsDouble = Double.parseDouble(value);
        return valueAsDouble;
    }

    public boolean is_x_within_bounds(double x, double mean, double sd) {

        double distance = (4 * sd) + mean;                    //See ALGORITHM #1
        double temp = Math.abs((mean - distance));           //needs to be positive
        lowerBound = mean - temp;                         //start of x-axis (LHS)
        upperBound = distance;

        //checks whether x is more than 4SDs Left or right to the central mean
        if (x < lowerBound | x > upperBound) {
            return false;
        } else {
            return true;
        }
    }

    public double roundLower(double low) {
        low = Math.round((lowerBound) * 10000d) / 10000d;
        return low;
    }

    public double roundUpper(double up) {
        up = Math.round((upperBound) * 10000d) / 10000d;
        return up;
    }

    public boolean checkUserInput(
            double x,
            double mean,
            double sd) {
        if (!is_x_within_bounds(x, mean, sd)) {
            //error message
            double low = roundLower(lowerBound);
            double up = roundUpper(upperBound);

            JOptionPane.showMessageDialog(null,
                    "x value(s) out of reasonable bounds. Please enter an x value between "
                    + low + " and "
                    + up);
            return false;
        } else {
            return true;
        }
    }

    public boolean is_SD_positive(double sd) {//SD must be postive
        if (sd < 0 || sd == 0) {
            return true;
        } else {

            return false;
        }
    }

    public void showArea(
            JTextField txtMean,
            JTextField txtSD,
            JTextField txtX1,
            JLabel lblArea,
            boolean s) {
        double mean = parseUserInputData(txtMean.getText());
        double sd = parseUserInputData(txtSD.getText());
        double a = parseUserInputData(txtX1.getText());
        double b = ((4 * sd) + mean);  //upper limit is default Z=4 as user only enters lower limit
        BigDecimal area;
        if (s) {
            area = nM.simpsonsRule(a, b, mean, sd);
            lblArea.setText("The area of the graph using the SIMPSON'S RULE is " + String.valueOf(area) + " units^2");

        } else {
            area = nM.trapeziumRule(a, b, mean, sd);
            lblArea.setText("The area of the graph using the TRAPEZIUM RULE is " + String.valueOf(area) + " units^2");

        }
        //txtArea.setText(String.valueOf(area) + " units^2");
    }

}
