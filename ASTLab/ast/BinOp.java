/**
 * BinOp represents an expression of two expressions with a binary operator (+, -, *, /) in between.
 * It performs the mathematics upon evaluation.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

package ast;

import environment.Environment;
import scanner.ScanErrorException;

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
}
