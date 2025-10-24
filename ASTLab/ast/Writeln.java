/**
 * Writeln is a statement that prints out the value of an expression. When it's executed,
 * it calls the expression's eval function and outputs the returned value.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

package ast;

import environment.Environment;
import scanner.ScanErrorException;
public class Writeln extends Statement
{
    private Expression exp;
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Executes the Writeln by printing out the value of the expression
     * 
     * @postcondition Value of the expression outputted
     * @param env The current environment
     */

    public void exec(Environment env) throws ScanErrorException
    {
        System.out.println(exp.eval(env));
    }
}