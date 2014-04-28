/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equationevaluator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael
 */
public class EquationEvaluator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String expr;
        //try {
        //    expr = br.readLine();
        //} catch (IOException ex) {
        //    expr = "Error";
        //}
        //expr = "(((+1-+3)*(-3/+4)-abs(-10)))";
        //expr = "({[([()])]})";
        //expr = "(1+1)";
        expr = "1+2+3+4+5+5*5*4/2+ 4^2";
        //expr = "2^2";
        System.out.println(expr);
        StringParser str = new StringParser();
        Equation eq = str.ParseString(expr);
        System.out.println();
        //System.out.println(eq.peekAt("x", 1));
        
    }
}
