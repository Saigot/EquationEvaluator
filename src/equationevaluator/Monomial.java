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
    public Monomial(Monomial l, Monomial r, Operation o){
        op = o;
        left = l;
        right = r;
        isLeaf = false;
    }
    
    public Monomial(){
        
    }
    public Monomial(double d){
        isLeaf = true;
        isVar = false;
        val = d;
    }
    
    public Monomial(char c) {
        isLeaf = true;
        isVar = true;
        var = c;
    }
//    public boolean IsSimplifiable(){
//        if(isLeaf){
//            return !isVar;
//        }else{
//            return right.IsSimplifiable() && left.IsSimplifiable();
//        }
//    }
    
    public void Simplify(){
        if(isLeaf) return;
        left.Simplify();
        right.Simplify();
        if(left.isLeaf && !left.isVar && right.isLeaf && !right.isVar){
            val = op.Eval(left.val, right.val);
            isLeaf = true;
            isVar = false;
        }
    }
    public void EvalAt(String vars, double ... vals){
        
    }
    public boolean isOnlyVar(){
        if(isLeaf){
            return isVar;
        }
        boolean b = left.isOnlyVar();
        if(b){
           b = right.isOnlyVar(); 
        }
        return b;
    }
    public String GatherVar(){
        String res = "";
        if(isLeaf && isVar){
            res += var;
        }else if(isLeaf){
            return res;
        }else{
            res += left.GatherVar();
            res += right.GatherVar();
        }
        return res;
    }
    static public Monomial Add(Monomial m1, Monomial m2){
        return null;
    }
    static public Monomial Mult(Monomial m1, Monomial m2){
        Monomial ans = new Monomial();
        //Val * Val
        if(m1.isLeaf && !m1.isVar && m2.isLeaf &&!m2.isVar){
            ans = new Monomial(Operation.MULT.Eval(m1.val, m2.val));
        }
        //Val * Var || Var * Val || Var * Var
        else if(m1.isLeaf && m2.isLeaf){
            ans = new Monomial(m1,m2,Operation.MULT);
        }
        //Val * ADD || Var * ADD
        else if(m1.isLeaf && m2.op == Operation.ADD){
            ans = new Monomial(Mult(m1,m2.left),Mult(m1,m2.right),Operation.ADD);
        }
        return ans;
    }
    
    public double PeekAt(String vars, double ... vals){
        boolean debug = true;
        if(isLeaf){
            if(isVar){
                int index = vars.indexOf(var);
                if(index == -1){
                    val = 0;
                }else{
                    val = vals[index];
                }
            }
        }else{
            val = op.Eval(left.PeekAt(vars,vals), right.PeekAt(vars,vals));
        }
        if(debug){
            String res = PrintStrRepresentation();
            System.out.printf("%-50s Evaluated to: %f\n", res, val);
        }
        return val;
    }
    
    
    public void PrintTreeRepresentation(int depth){
        String print = Integer.toString(depth) + ". ";
        if(isLeaf){
            if(isVar){
                print += var;
            }else{
                print += Double.toString(val);
            }
        }else{
            print += op.toString();
        }
        System.out.println(print);
        if(!isLeaf){
            left.PrintTreeRepresentation(depth+1);
            right.PrintTreeRepresentation(depth+1);
        }
    }
    
    public void PrintRepresentation(){
        if(isLeaf){
            if(isVar){
                System.out.print(var);
            }else{
                System.out.print(val);
            }
        }else{
            System.out.print("(");
            if(left != null)
            left.PrintRepresentation();
            if(op != null)
            System.out.print(op.toString());
            if(right != null)
            right.PrintRepresentation();
            
            System.out.print(")");
        }
        
    }
    public String PrintStrRepresentation(){
        String res = "";
        if(isLeaf){
            if(isVar){
                res +=(var);
            }else{
                res +=(val);
            }
        }else{
            res +=("(");
            if(left != null)
            res +=left.PrintStrRepresentation();
            if(op != null)
            res +=(op.toString());
            if(right != null)
            res+=right.PrintStrRepresentation();
            
            res +=(")");
        }
        return res;
    }
}
