package ast;

import emitter2.Emitter;
import environment.Environment;
import scanner.ScanErrorException;

/**
 * BinOp represents an expression of two expressions with a binary operator (+, -, *, /) in between.
 * It performs the mathematics upon evaluation.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

public class BinOp extends Expression
{
    private Expression exp1;
    private String operator;
    private Expression exp2;
    public BinOp(Expression exp1, String operator, Expression exp2)
    {
        this.exp1 = exp1;
        this.operator = operator;
        this.exp2 = exp2;
    }

    /**
     * Performs the binary operation and returns the result
     * 
     * @param env The current environment
     * @return The result of the binary operation
     */

    public int eval(Environment env) throws ScanErrorException
    {
        if (operator.equals("+"))
        {
            return exp1.eval(env) + exp2.eval(env);
        }
        else if (operator.equals("-"))
        {
            return exp1.eval(env) - exp2.eval(env);
        }
        else if (operator.equals("*"))
        {
            return exp1.eval(env) * exp2.eval(env);
        }
        else if (operator.equals("/"))
        {
            return exp1.eval(env) / exp2.eval(env);
        }
        throw new ScanErrorException("Operator \'" + operator + "\' not found.");
    }

    /**
     * Compiles a binary operator expression by emitting assembly code to perform the respective
     * operation (addu for +, subu for -, mult for *, div for /)
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        
        if (operator.equals("+"))
        {
            e.emit("#BinOp: Add");
            e.emit("addu $v0 $t0 $v0");
        }
        else if (operator.equals("-"))
        {
            e.emit("#BinOp: Sub");
            e.emit("subu $v0 $t0 $v0");
        }
        else if (operator.equals("/"))
        {
            e.emit("#BinOp: Div");
            e.emit("div $v0 $t0 $v0");
        }
        else if (operator.equals("*"))
        {
            e.emit("#BinOp: Mul");
            e.emit("mul $v0 $t0 $v0");
        }
    }
}
