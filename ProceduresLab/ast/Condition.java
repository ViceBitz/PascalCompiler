package ast;

import environment.Environment;
import scanner.ScanErrorException;

/**
 * Condition represents an boolean condition with two expressions and a comparative
 * operator (=, <>, <, >, <=, >=) in between. It returns a boolean true or false upon
 * evaluation, depending on if the expressions are equal or not equal.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

public class Condition {
    private Expression exp1, exp2;
    private String operator;
    public Condition(Expression exp1, String operator, Expression exp2)
    {
        this.exp1 = exp1;
        this.operator = operator;
        this.exp2 = exp2;
    }
    /**
     * Analyzes the boolean condition and returns the value (true or false)
     * 
     * @param env The current environment
     * @return The value of the booealn condition
     */
    public boolean eval(Environment env) throws ScanErrorException
    {
        int exp1Eval = exp1.eval(env);
        int exp2Eval = exp2.eval(env);
        if (operator.equals("="))
        {
            return (exp1Eval == exp2Eval);
        }
        else if (operator.equals("<>"))
        {
            return (exp1Eval != exp2Eval);
        }
        else if (operator.equals("<"))
        {
            return (exp1Eval < exp2Eval);
        }
        else if (operator.equals(">"))
        {
            return (exp1Eval > exp2Eval);
        }
        else if (operator.equals("<="))
        {
            return (exp1Eval <= exp2Eval);
        }
        else if (operator.equals(">="))
        {
            return (exp1Eval >= exp2Eval);
        }
        else
        {
            throw new ScanErrorException("Comparison operator \'"+operator+"\' not found.");
        }
    }

}
