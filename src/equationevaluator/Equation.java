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
    
    public void Simplify(){
        
    }
    
    public void EvalAt(String vars,boolean simplify, double ... val){
        
        if(simplify)Simplify();
    }
    
    public double peekAt(String vars, double ... val){
        
        return 0;
    }
}
