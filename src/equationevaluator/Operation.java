/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equationevaluator;
public enum Operation {
    NONE (" ",Integer.MAX_VALUE),
    EXP ("^",0), LOG("&log",0),
    FAC("&fac",0), ABS("&abs",0),
    SIN("&sin",0), COS("&cos",0),TAN("&tan",0),
    SIH("&sih", 0), COH("&coh",0),TAH("&tah", 0),
    MULT ("*",1),DIV ("/",1),DIF("%",1),
    ADD ("+",2);
    
    private String value;
    private int Priority;
    Operation(String c, int p){
        value = c;
        Priority = p;
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
    static public Operation GetValue(String c){
        c = c.toLowerCase();
        switch(c){
            case "^":
                return EXP;
            case "&fac":
                return FAC;
            case "&mod":
            case "&abs":
                return ABS;
            case "&sin":
                return SIN;
            case "&cos":
                return COS;
            case "&tan":
                return TAN;
            case "&sih":
                return SIH;
            case "&coh":
                return COH;
            case "&tah":
                return TAH;
            case "&log":
                return LOG;
            case "%":
                return DIF;
            case "*":
                return MULT;
            case "/":
               return DIV;
            case "+":
                return ADD;
            case "-":
                return ADD;
            case " ":
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

