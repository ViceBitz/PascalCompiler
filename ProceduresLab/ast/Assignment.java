package ast;

import environment.Environment;
import scanner.ScanErrorException;

/**
 * Assignments represents a statement that maps a variable to a value, using the ":=" operator.
 * It keeps track of the variable's identifier as well as the expression to be assigned to the variable, and
 * upon execution, it utilizes Environment's setVariable function and Expression's eval function to assign
 * the variable to the value.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */


public class Assignment extends Statement
{
    private String var;
    private Expression exp;
    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Executes the assignment statement by mapping the variable's identifier to the
     * expression's value
     * 
     * @postcondition The string identifier that represents the variable has been assigned
     * the expression's value
     * @param env The current environment
     */
    public void exec(Environment env) throws ScanErrorException
    {
        env.setVariable(var, exp.eval(env));
    }
}
