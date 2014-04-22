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
public class StringParser {
    
    
    public Equation ParseString(String expr){
        
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
    public ArrayList<MathObject> getBareRep(String expr){
        boolean error = false;
        ArrayList<MathObject> raw = new ArrayList<>();
        char lastbrac = ' ';
        for(int i = 0; i < expr.length(); i++){
            if(raw.get(raw.size()).type == 2 && (expr.charAt(i) == '+' || expr.charAt(i) == '-')){
                Double Add = new Double(0);
                i = scanNumber(i,expr,Add);
                raw.add(new MathObject(Add.doubleValue()));
            }else if(isIgnored(expr.charAt(i))){
                continue;
            }else if(Character.isAlphabetic(expr.charAt(i))){
                Operation ADD = Operation.NONE;
                i = scanFunctions(i, expr, ADD);
                if(ADD != Operation.NONE){
                    raw.add(new MathObject(ADD));
                }else{
                    raw.add(new MathObject(expr.charAt(i)));
                }
            }else if(isBracket(expr.charAt(i))){
                if(lastbrac == ' '){
                    lastbrac = expr.charAt(i);
                }else if (!BracketsClose(lastbrac, expr.charAt(i))){
                    error = true;
                    break;
                }else{
                    lastbrac = ' ';
                    raw.add(new MathObject(expr.charAt(i),true));
                }
            }else{
                raw.add(new MathObject(Operation.GetType(
                        Character.toString(expr.charAt(i)))));
            }
        }
        return raw;
    }
    
    public int scanNumber(int i, String expr, Double val){
        String eval = "";
        for(;i<expr.length();i++){
            if(Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.'){
                eval+=expr.charAt(i);
            }else{
                val = Double.parseDouble(eval);
                return i;
            }
        }
        return i;
    }
    public int scanFunctions(int i, String Expr, Operation op){
        int org = i;
        String func = "";
        for(; i < Expr.length(); i++){
            if(Character.isAlphabetic(Expr.charAt(i))){
                func += Expr.charAt(i);
                Operation temp = Operation.GetType(func);
                if(temp != Operation.NONE){
                    op.setValue(temp);
                    return i;
                }
            }else{
               return org; 
            }
        }
        return org;
    }
}
