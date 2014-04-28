/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equationevaluator;

import com.sun.xml.internal.ws.api.pipe.NextAction;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author michael
 */
public class StringParser {
    
    
    public Equation ParseString(String expr){
        boolean debug = true;
        ArrayList<MathObject> bare = getBareRep(expr);
        //while(bare.get(0).type == 3){
        //    bare.remove(0);
        //    bare.remove(bare.size()-1);
        //}
        Equation eq = BareToEq(bare);
        if(debug){
            System.out.println("Start Bare Print:");
            PrintBareBones(bare);
            
            System.out.println("\nStart Equation Print");
            eq.PrintRepresentation();
        }
        return eq;
    }
    public Equation BareToEq(ArrayList<MathObject> b){
        String vars = "";
        for(MathObject m : b){
            if(m.type == MathObject.VAR_TYPE && vars.indexOf(m.var) != -1){
                vars += m.var;
            }
        }
        int start = 0;
        int end = b.size()-1;
        return new Equation(BareToEq_aux(b,start,end),vars);
    }
    private MathObject GetNextTerm(ArrayList<MathObject> b, int start, int end){
        for(int i = start; i <= end; i++){
            if(b.get(i).type == MathObject.VAL_TYPE || b.get(i).type == MathObject.VAR_TYPE){
                return b.get(i);
            }
        }
        return new MathObject();
    }
    private Monomial BareToEq_aux(ArrayList<MathObject> b, int start, int end){
        boolean debug = true;
        if(debug){
            System.out.printf("Printing from %d to %d\n",start,end);
            PrintBareBones(b,start,end);
            System.out.println();
        }
        boolean error = false;
        Monomial root = new Monomial();
        int pri = 0;
        int lvl = 0;
        int piv = GetPivot(b,start,end,pri,lvl);
        MathObject pivobj;
        if(piv != -1){
            pivobj = b.get(piv);
        }else{
            pivobj = new MathObject();
        }
        if(piv != -1){
            root.op = pivobj.Operator;
            Monomial m = BareToEq_aux(b, start, piv-1);
            if(m != null){
                root.left = m;
            }else{
                root.left = new Monomial(1);
            }
            m = BareToEq_aux(b, piv+1, end);
            if(m != null){
                root.right = m;
            }else{
                root.right = new Monomial(1);
            }
            
        }else{
            MathObject m = GetNextTerm(b, start, end);
            if(m.type == MathObject.VAL_TYPE){
                return new Monomial(m.val);
            }else if(m.type == MathObject.VAR_TYPE){
                return new Monomial(m.var);
            }
        }
        if(root == null){
            root = new Monomial(1);
        }
        
        return root;
    }
    public int GetPivot(ArrayList<MathObject> b, int start, int priority, int level){
        return GetPivot(b, start, b.size()-1,priority, level);
    }
    public int GetPivot(ArrayList<MathObject> b, int start, int end, int priority, int level){
        boolean debug = true;
        if(debug){
                System.out.println("Starting Pivot search");}
        if(b.size() <= 1){
            if(debug){
                System.out.println("Starting Pivot search");
                System.out.printf("Size is %d\n",b.size());
            }
            return 0;
        }
        int index = -1;
        int pri = -1;
        int lvl = -1;
        for(int i = start; i <= end; i++){
            MathObject obj = b.get(i);
            switch(obj.type){
                case MathObject.VAR_TYPE:
                    break;
                case MathObject.VAL_TYPE:
                    break;
                case MathObject.OP_TYPE:
                    System.out.println("I Found " + obj.Operator.toString());
                    int thispri = obj.Operator.GetPriority();
                    if((thispri >= priority && level <= 0)
                            && (index == -1 || level > lvl || (thispri < pri && level == lvl))){
                        System.out.printf("I Selected %s at %d %d\n",
                                obj.Operator.toString(),i,level);
                        index = i;
                        pri = thispri;
                        lvl = level;
                    }
                    break;
                case MathObject.BRAC_TYPE:
                    if(isOpenBracket(obj.bracket)){
                        level--;
                        //System.out.println("Level Down, is now " + level);
                    }else{
                        level++;
                        //System.out.println("Level Up, is now " + level);
                    }
                    break;
            }
        }
        System.out.println("Returning the Pivot:" + index);
        return index;
    }
    public int findNextOperand(String expr, int pri){
        
        return 0;
    }    
    
    public boolean isIgnored(char c){
        return c == ' ';
    }
    
    public boolean isBracket(char c){
        return c == '(' || c == ')' || c == '[' || c == ']'
                || c == '{' || c == '}';
    }
    
    public boolean BracketsClose(char o, char c){
        switch(o){
            case '(':
                return c == ')';
            case '[':
                return c == ']';
            case '{':
                return c == '}';
            default:
                return false;
        }
    }
    public boolean isOpenBracket(char b){
        return b == '(' || b == '[' || b == '{';
    }
    
    public ArrayList<MathObject> getBareRep(String expr){
        boolean error = false;//false;
        boolean debug = true;
        ArrayList<MathObject> raw = new ArrayList<>();
        Stack<Character> lastbrac =  new Stack<>();
        lastbrac.add(new Character('\0'));
        for(int i = 0; i < expr.length(); i++){
            char c = expr.charAt(i);
            if (debug) System.out.print("\n\"" + c + "\"");
            //Evaluates Sign as opposed to addition/subtraction, numbers
            if(((i == 0 || raw.get(raw.size()-1).type == 2 || raw.get(raw.size()-1).type == 3) 
                    && (c == '+' || c == '-')) || (Character.isDigit(c))){
                if (debug) {
                    System.out.print(": ");
                    if ((i != 0 && (raw.get(raw.size() - 1).type == 2
                            || raw.get(raw.size() - 1).type == 3))) {
                        raw.get(raw.size() - 1).PrintRepresentation();
                        System.out.print(" is type " + raw.get(raw.size() - 1).type);
                    }
                    System.out.print(" , Treated as Number");
                }
                MathObject obj = new MathObject();
                i = scanNumber(i,expr,obj);
                if(obj.type == -1){
                    obj.setOperation(Operation.GetType(Character.toString(c)));
                }
                raw.add(obj);
            //Continues if ignorable character
            }else if(isIgnored(c)){
                if(debug) System.out.print(":Ignored");
                continue;
            //Variables & functions
            }else if(Character.isAlphabetic(c)){
                MathObject ADD = new MathObject();
                i = scanFunctions(i, expr, ADD);
                if(ADD.Operator == Operation.NONE){
                    if(debug) System.out.print(":Treated as Var");
                    ADD.setVar(c);
                }else{
                    if(debug) System.out.print(":Treated as Function");
                }
                raw.add(ADD);
            //Brackets
            }else if(isBracket(c)){
                if(debug) System.out.print(":Treated as Brac");
                if(!isOpenBracket(c)){
                   char last = lastbrac.pop().charValue();
                   if(last == '\0'){
                       error = true;
                       break;
                   }else if(!BracketsClose(last, c)){
                       error = true;
                   }else{
                       raw.add(new MathObject(c,true));
                   }
                }else{
                    raw.add(new MathObject(c,true));
                    lastbrac.add(new Character(c));
                }
             
            //Operations (not functions)
            }else{
                if(debug) System.out.print(":Treated as Operator");
                raw.add(new MathObject(Operation.GetType(
                        Character.toString(c))));
            }
        }
        if(error){
            System.out.printf("Error!\n");
        }
        return raw;
    }
    
    public int scanNumber(int i, String expr, MathObject val){
        char first = expr.charAt(i);
        String eval = ""; 
        if(first == '-' || first == '+'){
            i++;
        }
        for(;i<expr.length();i++){
            if(Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.'){
                eval+=expr.charAt(i);
            }else{
                if(eval.isEmpty()){
                    return i-1;
                }
                double d= Double.parseDouble(eval); 
                if(first == '-') d *= -1;
                val.setVal(d);
                return i-1;
            }
        }
        return i-1;
    }
    public int scanFunctions(int i, String Expr, MathObject op){
        int org = i;
        String func = "";
        for(; i < Expr.length(); i++){
            if(Character.isAlphabetic(Expr.charAt(i))){
                func += Expr.charAt(i);
                Operation temp = Operation.GetType(func);
                if(temp != Operation.NONE){
                    op.setOperation(temp);
                    return i;
                }
            }else{
               return org; 
            }
        }
        return org;
    }
    
    public void PrintBareBones(ArrayList<MathObject> m){
        for(MathObject math : m){
            math.PrintRepresentation();
        }
    }
    public void PrintBareBones(ArrayList<MathObject> m, int start, int end){
        if (start > end) return;
        for(int i = start; i <= end; i++){
            m.get(i).PrintRepresentation();
        }
    }
}
