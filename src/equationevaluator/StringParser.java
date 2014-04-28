/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equationevaluator;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author michael
 */
public class StringParser {
    
    
    public Equation ParseString(String expr){
        ArrayList<MathObject> bare = getBareRep(expr);
        //while(bare.get(0).type == 3){
        //    bare.remove(0);
        //    bare.remove(bare.size()-1);
        //}
        System.out.println("Start Print:");
        PrintBareBones(bare);
        //find pivot
        //parse right and left recursively
        
        return null;
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
        boolean error = false;
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
                    if(debug) System.out.print(":Treated as Brac");
                    ADD.setBracket(c);
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
}
