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
        ArrayList<MathObject> raw = new ArrayList<>();
        char lastbrac = ' ';
        for(int i = 0; i < expr.length(); i++){
            if((i == 0 || raw.get(raw.size()-1).type == 2) 
                    && (expr.charAt(i) == '+' || expr.charAt(i) == '-')){
                Double Add = new Double(0);
                System.out.println(expr.charAt(i));
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
                    raw.add(new MathObject(expr.charAt(i),true));
                }else if (!isOpenBracket(expr.charAt(i)) 
                        && !BracketsClose(lastbrac, expr.charAt(i))){
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
        if(error){
            System.out.printf("Error!\n");
        }
        return raw;
    }
    
    public int scanNumber(int i, String expr, Double val){
        String eval = "";
        for(;i<expr.length();i++){
            if(Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.'){
                eval+=expr.charAt(i);
            }else{
                System.out.println(eval);
                if(eval.isEmpty()){
                    return i;
                }
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
    
    public void PrintBareBones(ArrayList<MathObject> m){
        for(MathObject math : m){
            math.PrintRepresentation();
        }
    }
}
