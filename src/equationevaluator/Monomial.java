/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equationevaluator;

/**
 *
 * @author michael
 */
public class Monomial {
    Operation op;
    
    Monomial left = null;
    Monomial right = null;
    double val;
    char var;
    
    boolean isLeaf;
    boolean isVar = false;
    
    public boolean IsSimplifiable(){
        if(isLeaf){
            return !isVar;
        }else{
            return right.IsSimplifiable() && left.IsSimplifiable();
        }
    }
    
    public void EvalAt(String vars, double ... vals){
        
    }
    
    public double PeekAt(String vars, double ... vals){
        if(isLeaf){
            if(isVar){
                int index = vars.indexOf(var);
                if(index == -1){
                    return 0;
                }else{
                    return vals[index];
                }
            }
        }else{
            return op.Eval(left.PeekAt(vars,vals), right.PeekAt(vars,vals));
        }
        return 0;
    }
}
