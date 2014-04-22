/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equationevaluator;

/**
 *
 * @author michael
 */
public class MathObject {
    char var;
    double val;
    Operation Operator;
    char bracket;
    
    int type;// 0 for var, 1 for val and 2 for operator, 3 for bracket
    public MathObject(char c){
        var = c;
        type = 0;
    }
    public MathObject(char c, boolean brac){
        if(brac){
            var = c;
            type = 0;
        }else{
            bracket = c;
            type = 3;
        }
    }
    
    public MathObject(double d){
        val = d;
        type = 1;
    }
    
    public MathObject(Operation op){
        Operator = op;
        type = 2;
    }
    public boolean isEqual(MathObject m){
        if(m.type != type){
            return false;
        }
        switch(type){
            case 0:
                return var == m.var;
            case 1:
                return val == m.val;
            case 2:
                return Operator == m.Operator;
            default:
                return bracket == m.bracket;
        }
    }
    
    public void PrintRepresentation(){
        switch(type){
            case 0:
                System.out.print(var);
                break;
            case 1:
                System.out.print(val);
                break;
            case 2:
                System.out.print(Operator.toString());
                break;
            case 3:
                System.out.print(bracket);
                break;
            default:
                System.out.print(" Err ");
        }
    }
}
