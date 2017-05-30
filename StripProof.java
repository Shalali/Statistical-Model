/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmodel;

/**
 *
 * @author Milu
 */
public class StripProof {

    static double n;

    public static void main(String args[]) {
        n = findN();
        System.out.println(trapeziumRule(0, 4, 0, 1, n));//limit a,limit b, mean, sd
        System.out.println(n);
    }

    public static double trapeziumRule(double a, double b, double mean, double sd, double n) {
        //double n = n;                //number of strips
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
        double area = h / 2 * (yfirst + 2 * (sum) + yLast);

        area = Math.round((area) * 100000d) / 100000d;
        return area;
    }

    public static double p_of_x(double sd, double mean, double x) {
        //P(x), Probability fuction 
        double y;
        double denominator, numerator, exponent;
        numerator = Math.E;
        denominator = sd * Math.sqrt(2 * Math.PI);
        exponent = Math.pow(x - mean, 2) / 2 * (Math.pow(sd, 2));
        y = Math.pow(numerator, -exponent) / denominator;    //P(x)

        return y;       //return y subscript n

    }
    /*
     TEST WHEN 5dp PRECISON is found to true integral values
     true: 0.49996833 @ n = 200, 7dp matches
     */

    public static double findN() {//
        double trueValue = 0.49997;//0.49996833
        //double trueValue = 0.38209;
        double a;
        n = 0;

        do {
            n++;
            a= trapeziumRule(0, 4, 0, 1, n);
           // a = trapeziumRule(1.5, 20, 0, 5, n);

        } while (a != trueValue);
        return n;
    }

}
