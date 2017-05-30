/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Milu
 */
public class NumericalMethods extends JPanel implements ActionListener {

     WorkingOut wO = new WorkingOut();
    //frmGreaterThan greaterThan = new frmGreaterThan();

    public NumericalMethods() {
      //  greaterThan.btnWorking.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
       // if (e.getSource() == greaterThan.btnWorking) {
            
        //}
    }

    public static void main(String args[]) {
        NumericalMethods Nm = new NumericalMethods();
        // System.out.println(trapeziumRule(0, 3, 0, 1));//limit a,limit b, mean, sd
    }

    public void numericalMethods() {

    }

    public static double p_of_x(double sd, double mean, double x) {
        //P(x), Probability fuction 
        double y;
        double denominator, numerator, exponent;
        numerator = Math.E;
        denominator = sd * (Math.sqrt(2 * Math.PI));
        exponent = (Math.pow((x - mean), 2)) / (2 * ((Math.pow(sd, 2))));
        y = (Math.pow(numerator, -exponent)) / denominator;    //P(x)
        return y;       //return y subscript n

    }

    public int getN(double a, double b, double mean, double sd, boolean s) {//get's accurate values of N based on perfect N values for whole Zs
        //get Z1 and Z2
        //round z1 and z2

        double zofA = (a - mean) / sd;
        double zofB = (b - mean) / sd;

        int zA = (int) Math.round(zofA);
        int zB = (int) Math.round(zofB);

        if (zA == 0) {
            zA = 1;
        }
        if (zB == 0) {
            zB = 1;
        }
        int aStrips = getStripsUsingZ(Math.abs(zA));
        int bStrips = getStripsUsingZ(Math.abs(zB));

        double N = (aStrips + bStrips) / 2;//
        Math.round(N);

        if (s = true && (N / 2) != 0) {//if NM is simpson's rule & N is odd
            N = N + 1;  //make no of strips even for simpsons rule
            return (int) N;
        }
        return (int) N;

    }

    public int getStripsUsingZ(int c) {
        int n = 0;
        switch ((int) c) {
            case 1:
                n = 46;
                break;
            case 2:
                n = 86;
                break;
            case 3:
                n = 45;
                break;
            case 4:
                n = 15;
                break;
        }
        return n;
    }

    public void printTable(double[] x, double[] y) {
        wO.txtarea.append("x | y ");
        wO.txtarea.append("\n");

        for (int i = 0; i < x.length; i++) {    //print each pair
            wO.txtarea.append(round(x[i]) + " | ");
            wO.txtarea.append(round(y[i]) + " | ");
            wO.txtarea.append("\n");

            
             System.out.print(x[i] + " ");
             System.out.print(y[i] + " ");
             System.out.println("");
             
        }
        
      
        //wO.setVisible(true);
        wO.txtarea.setLineWrap(true);

    }

    public double first(double mean, double sd, double a) {
        double yfirst = p_of_x(sd, mean, a);
        return yfirst;
    }

    public double[] middle(
            double a,
            double b,
            double mean,
            double sd,
            double h,
            double[] xIntermediates,
            double[] yIntermediates,
            double n) {

        //First indices are needed to start populating from right location
        xIntermediates[0] = a;
        yIntermediates[0] = first(mean, sd, a);

        //populate middle values from N=1 to N=N-1
        double temp = a;
        for (int i = 1; i < n; i++) {
            temp = temp + h;
            xIntermediates[i] = temp;
            yIntermediates[i] = p_of_x(sd, mean, xIntermediates[i]);
        }
        return yIntermediates;
    }

    public double last(double h, double mean, double sd, double[] xMiddles, double n) {
        double yLast = p_of_x(sd, mean, (xMiddles[(int) n - 1] + h));
        return yLast;
    }

    public BigDecimal trapeziumRule(double a, double b, double mean, double sd) {
        boolean s = false;// confirms that isnt simpsons therefore N odd/evens
        //double n = getN(a, b, mean, sd, s);
        double n =20;//500;
        double h = (b - a) / n;

        double[] xIntermediates = new double[(int) n];
        double[] yIntermediates = new double[(int) n];

        double sum = 0;

        //get first value
        double yFirst = first(mean, sd, a);

        //get middle values
        yIntermediates = middle(a, b, mean, sd, h, xIntermediates, yIntermediates, n);
        for (int i = 1; i < n; i++) {
            sum += yIntermediates[i];
        }

        //get last value
        double yLast = last(h, mean, sd, xIntermediates, n);

        //area
        double area = (h / 2) * (yFirst + (2 * (sum)) + yLast);

        BigDecimal areaRounded = round(area);        //round area neatly i.e 4 sf
        
        
        //printTable(xIntermediates, yIntermediates);
        System.out.println(round(area));
        return areaRounded;
    }
    
    public Double[] xCopy(Double[] a){
        return a;
    }

    public BigDecimal simpsonsRule(double a, double b, double mean, double sd) {
        boolean s = true;//EVEN NUMBER
        //double n = getN(a, b, mean, sd, s);    //ensure n = even
        double n = 20;//500;
        double h = (b - a) / n;

        double[] xIntermediates = new double[(int) n];
        double[] yIntermediates = new double[(int) n];

        double odd = 0;
        double even = 0;

        //get first value
        double yFirst = first(mean, sd, a);
        //get middle
        yIntermediates = middle(a, b, mean, sd, h, xIntermediates, yIntermediates, n);
        //ODD
        for (int i = 1; i < n; i += 2) {
            odd += yIntermediates[i];
        }
        //EVEN
        for (int j = 2; j < n; j += 2) {
            even += yIntermediates[j];
        }

        //get last value
        double yLast = last(h, mean, sd, xIntermediates, n);

        //area
        double area = (h / 3) * (yFirst + (4 * odd) + (2 * even) + yLast);

        BigDecimal areaRounded = round(area);        //round area neatly i.e 4 sf

        printTable(xIntermediates, yIntermediates);
        System.out.println(round(area));
        return areaRounded;

    }

    public BigDecimal round(double area) {
        BigDecimal bigDec = new BigDecimal(Math.abs(area));
        bigDec = bigDec.round(new MathContext(4));//3 sf is relevant
        return bigDec;
    }

  }
