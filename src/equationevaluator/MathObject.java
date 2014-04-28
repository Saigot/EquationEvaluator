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
    static final int NO_TYPE = -1;
    static final int VAR_TYPE = 0;
    static final int VAL_TYPE = 1;
    static final int OP_TYPE = 2;
    static final int BRAC_TYPE = 3;
    
    char var;
    double val;
    Operation Operator;
    char bracket;
    
    int type;// -1, indeterminint, 0 for var, 1 for val and 2 for operator, 3 for bracket
    public MathObject(char c){
        var = c;
        type = 0;
    }
    
    public MathObject(char c, boolean brac){
        if(brac){
            bracket = c;
            type = 3;
        }else{
            var = c;
            type = 0;
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
      public MathObject(){
          type = -1;
      }
    
    public void setVar(char c){
       type = 0;
       var = c;
    }
    public void setVal(double d){ //hehe double d
        type = 1;
        val = d;
    }
    public void setOperation(Operation op){
        type = 2;
        Operator = op;
    }
    public void setBracket(char c){
        type = 3;
        bracket = c;
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
            case 3:
                return bracket == m.bracket;
            default: 
                return false; //indeterminent is always false
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
