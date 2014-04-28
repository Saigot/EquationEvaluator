/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equationevaluator;
public enum Operation {
    
    EXP ("^",0), LOG("&log",3),
    FAC("&fac",3), ABS("&abs",3),
    SIN("&sin",3), COS("&cos",3),TAN("&tan",3),
    SIH("&sih", 3), COH("&coh",3),TAH("&tah", 3),
    MULT ("*",1),DIV ("/",1),DIF("%",1),
    ADD ("+",2), SUB("-",2),NONE (" ",Integer.MAX_VALUE);
    
    private String value;
    private int Priority;
    Operation(String c, int p){
        value = c;
        Priority = p;
    }
    public void setValue(Operation op){
        value = op.GetSymbol();
    }
    public int GetPriority(){
        return Priority;
    }
    public boolean isEqual(String c){
        c = c.toLowerCase();
        return c.equals(value);
    }
    static public Operation GetValue(char c){
         switch(c){
            case '^':
                return EXP;
            case '*':
                return MULT;
            case '%':
                return DIF;
            case '/':
               return DIV;
            case '+':
                return ADD;
            case '-':
                return ADD;
            case ' ':
            default:
                //error
                return NONE;
    }}
    static public Operation GetType(String c){
        c = c.toLowerCase();
        switch(c){
            case "fac":
                return FAC;
            case "abs":
                return ABS;
            case "sin":
                return SIN;
            case "cos":
                return COS;
            case "tan":
                return TAN;
            case "sih":
                return SIH;
            case "coh":
                return COH;
            case "tah":
                return TAH;
            case "log":
                return LOG;
            case "^":
                return EXP;
            case "mod":
            case "%":
                return DIF;
            case "*":
                return MULT;
            case "/":
               return DIV;
            case "+":
                return ADD;
            case "-":
                return SUB;
            default:
                //error
                return NONE;
        }
    }
    
    public boolean isFunction(){
        return  this==SIN||this==COS||this==TAN||this==FAC||this==ABS||this==LOG
                ||this==COH||this==SIH||this==TAH;
    }
    public String GetSymbol(){
        return value;
    }
    public double Eval(double val1, double val2){
        switch(this){
            case EXP:
                return Math.pow(val1, val2);
            case FAC:
                double result = 1;
                for(int i = 1; i <= val2; i++){
                    result = result*i;
                }
                return result*val1;
            case SIN:
                return val1 * Math.sin(val2);
            case COS:
                return val1 * Math.cos(val2);
            case TAN:
                return val1 * Math.tan(val2);
            case SIH:
                return val1 * Math.sinh(val2);
            case COH:
                return val1 * Math.cosh(val2);
            case TAH:
                return val1 * Math.tanh(val2);
            case DIF:
                return val1%val2;
            case MULT:
                return val1 * val2;
            case DIV:
               return val1 / val2;
            case ADD:
                return val1 + val2;
            case ABS:
                return val1 * Math.abs(val2);
            case LOG:
                return val1*Math.log10(val2);
            case NONE:
            default:
                //error
                return val1;   
        }
    }
}

