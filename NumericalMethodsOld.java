/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmodel;

import java.math.BigDecimal;
import java.math.MathContext;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Milu
 */
public class NumericalMethodsOld extends JPanel {

    PrintXYValues print = new PrintXYValues();

    public NumericalMethodsOld() {
        trapeziumRule(9, 4, 7, 8);//limit a,limit b, mean, sd

    }

    public static void main(String args[]) {
        NumericalMethodsOld Nm = new NumericalMethodsOld();
        // System.out.println(trapeziumRule(0, 3, 0, 1));//limit a,limit b, mean, sd
    }

    public BigDecimal trapeziumRule(double a, double b, double mean, double sd) {

        double n = getN(a, b, mean, sd);                //get number of strips
        //double n=20;//remove
        double h = (b - a) / n;     //height aka width of trapezium 
        double sum = 0;                 //sum of the middle strips

        //first strip
        double yfirst = p_of_x(sd, mean, a);
        double[] xIntermediates = new double[(int) n];
        xIntermediates[0] = a;
        double[] yIntermediates = new double[(int) n];
        yIntermediates[0] = yfirst;

        //middle strips
        double temp = a;

        //last strip
        for (int i = 1; i < n; i++) {
            temp = temp + h;
            xIntermediates[i] = temp;
            yIntermediates[i] = p_of_x(sd, mean, xIntermediates[i]);
            sum += yIntermediates[i];
        }
        double yLast = p_of_x(sd, mean, xIntermediates[(int) n - 1] + h);
        double area = (h / 2) * (yfirst + (2 * (sum)) + yLast);

        //JLabel[] labels = printTable(xIntermediates, yIntermediates);
        printTable(xIntermediates, yIntermediates);

        /*
         for (int i = 0; i < labels.length ; i++) {
         print.add(labels[i]);
         }*/
        //print.setVisible(true);
        BigDecimal areaRounded = new BigDecimal(Math.abs(area));
        areaRounded = areaRounded.round(new MathContext(4));//3 sf is relevant
        System.out.println(areaRounded);
        return areaRounded;
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

    public int getN(double a, double b, double mean, double sd) {//get's accurate values of N based on perfect N values for whole Zs
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

        //JLabel lbl = new JLabel();
        //JLabel[] labels = new JLabel[x.length];
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i] + " ");
            System.out.print(y[i] + " ");
            System.out.println("");
        }

        //return labels;
    }

    /*
    public BigDecimal simpsonsRule(double a, double b, double mean, double sd) {
        
        double area = h / 2 * (yfirst + 2 * (sum) + yLast);

        BigDecimal areaRounded = new BigDecimal(Math.abs(area));
        areaRounded = areaRounded.round(new MathContext(4));//3 sf is relevant
        System.out.println(areaRounded);
        return areaRounded;
        
    }*/

}
