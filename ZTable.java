/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsmodel;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author Milu
 */
public class ZTable {

    NumericalMethods numMethods = new NumericalMethods();
    //frmGreaterThan greaterThan = new frmGreaterThan();
    temp temp = new temp();
    //static double start, end;  //get user inputs from text boxes
    double[] column = new double[10];
    //JTable table;

    public ZTable() {

    }

    public static void main(String args[]) {
        //data as object;
        //table
        //DISPLAY
        /*for (int k = 0; k < rows.length; k++) {    //print each row
         System.out.println(rows[k]);
         }*/
    }

    public void check_is_Input_Negative(double start, double end) {
        if (start < 0 && end < 0) {

        }

    }

    public JTable getZTable(boolean s, double start, double end) {
        //double start = 2;//.97;//0.97;
        // double end = 4;
        //3.9;
        //finding the no. of rows:
        //check_is_Input_Negative(start, end);//check whether the start or end z value is negative
        double startOnedp = (Math.floor(start * 10.0) / 10.0);//get the first 2 significant figures of the input start value 
        double endOnedp = (Math.floor(end * 10.0) / 10.0);// 2sf of end value

        double difference = endOnedp - startOnedp;   //find difference
        double tenths = difference * 10;    //gets a Double to 1 place value after decimal
        int noOfRows = (int) tenths;      //only whole number is relevnat

        //checks whether there is a number in the 2dp of start 0.0*<-. If so, the no. of columns needs to be incremented by 1
        int start_hundreth_present = getColumnHeading(start);
        double x = Math.floor(start);

        //if (start_hundreth_present != 0&&x==0) {
        /// noOfRows++;
        // }
        //if (start_hundreth_present != 0) {
        //    noOfRows++;
        //}

        noOfRows++;     //increment by 1 to get the no of rows between start and end

        //get the first LHS row header value
        double rowFirstHeader = (Math.floor(start * 10) / 10);//get the first 2 significant figures of the input start value 
        double rowLastHeader = (Math.floor(end * 10) / 10);// 2sf of end value
        double[] rows = new double[noOfRows];

//row,MINUS 1 AS O IS INCLUDED FOR BOTH VALUES R,C
        ///"+1" because og table headers
        JTable table = new JTable(noOfRows + 1, 10 + 1) {

            //Implement table cell tool tips.           
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = getValueAt(rowIndex, colIndex).toString();
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }

                return tip;
            }
        };

        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(60);
        }

        table.setRowHeight(30);

        //table.setShowGrid(false);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(Color.BLACK);

        table.setFont(new Font("Ariel", Font.PLAIN, 14));

//Z TABLE HEADERS 
        table.setValueAt("Z", 0, 0);//r,c/i,j//only applicatble for the FIRST ROW and possibly the LAST ROW

        //Z=0.1 to 4.0
        double z_rowHeaders = rowFirstHeader;//row headers are to 1dp
        DecimalFormat fRow = new DecimalFormat("0.0");
//ROW HEADERs
        for (int r = 1; r < (noOfRows) + 1; r++) {
            table.setValueAt(fRow.format(z_rowHeaders), r, 0);//r,c/i,j//only applicatble for the FIRST ROW and possibly the LAST ROW
            z_rowHeaders += 0.1;
        }
//COLUMN HEADERs
        //Z = 0.00 to 0.09
        double z_ColumnHeaders = 0.00;//column headers are to 2 dp
        DecimalFormat fCol = new DecimalFormat("0.00");

        for (int h = 1; h < 11; h++) {
            // z_rowHeaders = (Math.floor(z_rowHeaders * 100) / 100);// 2dp format
            table.setValueAt(fCol.format(z_ColumnHeaders), 0, h);//r,c/i,j//only applicatble for the FIRST ROW and possibly the LAST ROW
            z_ColumnHeaders += 0.01;
        }
        int intFirstColumnHeaderI = getColumnHeading(start);

        //LOCATE FIRST j (ROW place in table)
        double doubleRowHeader = Math.floor(start * 10) / 10;//gets Z value to 1 dp
        // double intFirstRowHeaderD = (doubleRowHeader * 10);//get the nth row postion of the double row magnitude
        int intFirstRowHeaderI = (int) doubleRowHeader;//typecasts to  populate in table

        //error check to fill in leading empty cells incase VALUE DOESTN START @ (0,0) i.e. somewhere middway 
        //empty cells before start
        if (intFirstColumnHeaderI != 0) {//0th is the first column
            for (int f = 1; f <= intFirstColumnHeaderI; f++) {//fill in the leading empty cells
                table.setValueAt(null, 1, f);//r,c/i,j//only applicatble for the FIRST ROW and possibly the LAST ROW
            }
        }
//ENDING POINT OF TABLE
        //ENDING ROW
        double doubleLastRowHeader = Math.floor(end * 10) / 10;//gets Z value to 1 dp
        double integerLastRowHeadersD = (doubleLastRowHeader * 10) + 1;//get the nth row postion of the double row magnitude
        int integerLastRowHeadersI = (int) integerLastRowHeadersD;//typecasts to  populate in table

        //ENDING COLUMN
        double truncate_ZEnd = Math.floor(end * 10) / 10;//gets Z value to 1 dp
        double hundreths_ZEnd = end - truncate_ZEnd;//gets the the 2nd dp (hunthreths) number for column headings
        double intLastColumnHeadersDR = Math.round(hundreths_ZEnd * 100.0) / 100.0;    //gets the interger part of the column, igoring the leading 0.0s 
        int intLastColumnHeadersI = (int) (intLastColumnHeadersDR * 100);//as first heading is inclusive of 0(.00)

//POPULATE
        int cPointer, counter = 0;

        int tempR;// = (intFirstRowHeaderI+1);

        if (intFirstRowHeaderI == 0) {//only exception when value of row cant be used as a count
            tempR = 1;
        } else {
            tempR = intFirstRowHeaderI;
        }

        for (int rPointer = 1; rPointer < noOfRows + 1; rPointer++) {//row pointer

            //ensures that the row after the first stare from the first cell in the next row   
            //FIRST VALUE MAY NOT BE IN THE FIRST COLUMN OF THE FIRST ROW
            counter++;//redunant after first iteration
            if (counter == 1) {
                cPointer = intFirstColumnHeaderI + 1;//WHAT I DID HERE IS CRUCIAL EXPLAIN PERFECTLY; DOUBLT TO MAG CONVERSION SO THAT DISPLAY PARALLELS WITH MAG COUNT

            } else {
                cPointer = 1;
            }//if else 
            for (cPointer = cPointer; cPointer < 11; cPointer++) {//9999

                /*if (rB == greaterThan.radBtnZSimp) {
                 table.setValueAt(zScoreUsingSimpsons(start), rPointer, cPointer);//value, row, col
                 }
                 else if (rB == greaterThan.radBtnZTrap) {
                 table.setValueAt(zScoreUsingTrapezium(start), rPointer, cPointer);//value, row, col
                 }*/
                table.setValueAt(getAreaLeftOfZScore(start, s), rPointer, cPointer);//value, row, col
                start += 0.01;
            }//for loop column 
            int t = 0;
        }//for loop row

        //empty @ end
        int columnIndex = intLastColumnHeadersI + 1;//column index is n+1
        int temp = columnIndex + 1;//column after should be next

        if (columnIndex != 10) {//9th column is the last
            for (int f = temp; f < 11; f++) {//fill in the ending/trailing empty cells
                table.setValueAt(null, noOfRows, f);//r,c/i,j//only applicatble for the FIRST ROW and possibly the LAST ROW
            }
        }
        return table;
    }

    public int getColumnHeading(double start) {
        //STARTING POINTS OF TABLE
        //LOCATE FIRST i (COLUMN place in table)
        double truncate_ZStart = Math.floor(start * 10) / 10;//gets Z value to 1 dp
        double hundreths_ZStart = start - truncate_ZStart;//gets the the 2nd dp (hunthreths) number for column headings
        double hundreths_ZStartR = Math.round(hundreths_ZStart * 100.0) / 100.0;//gets the the 2nd dp (hunthreths) number for column headings
        double intFirstColumnHeaderD = hundreths_ZStartR * 100;    //gets the interger part of the column, igoring the leading 0.0s 
        int intFirstColumnHeaderI = (int) intFirstColumnHeaderD;//+1;//as first heading is inclusive of 0(.00)
        return intFirstColumnHeaderI;
    }

    public BigDecimal getAreaLeftOfZScore(double b, boolean s) {
        double a = -4;//-4;//cos -10 //upper limit 
        BigDecimal zArea;

        if (s) {
            //System.out.println("Simpson");
            zArea = numMethods.simpsonsRule(a, b, 0, 1);
            //return zArea;

        } else {

            //System.out.println("Trapezium");
            zArea = numMethods.trapeziumRule(a, b, 0, 1);

            //return zArea; 
        }

        // BigDecimal zArea = numMethods.simpsonsRule(a, b, 0, 1);
        //zArea = numMethods.trapeziumRule(a, b, 0, 1);
        //zArea = numMethods.simpsonsRule(a, b, 0, 1);
        return zArea;
    }

    public Object zScoreUsingSimpsons(double b) {
        double a = -4;//-4;//cos -10 //upper limit 
        BigDecimal zArea = numMethods.simpsonsRule(a, b, 0, 1);
        return zArea;
    }

    public Object zScoreUsingTrapezium(double b) {
        double a = -4;//-4;//cos -10 //upper limit 
        BigDecimal zArea = numMethods.trapeziumRule(a, b, 0, 1);
        return zArea;
    }

}
