/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equationevaluator;

import java.util.ArrayList;

/**
 *
 * @author michael
 */
public class Equation {
    Monomial root;
    String vars;
    
    public Equation(Monomial ROOT, String VARS){
        root = ROOT;
        vars = VARS;
    }
    
    public double peekAt(String vars, double ... val){
        return root.PeekAt(vars, val);
    }
    
    public void PrintRepresentation(){
        root.PrintRepresentation();
    }
    public void PrintTreeRepresentation(){
        root.PrintTreeRepresentation(1);
    }
}
