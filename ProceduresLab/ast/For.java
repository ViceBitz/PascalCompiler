package ast;

import environment.Environment;
import scanner.ScanErrorException;

/**
 * For represents an statement that emulates a standard for loop. Upon execution, it iterates a defined
 * loopVariable through the range [lowerBound, upperBound] with increments of 1, and executing the
 * statement each time.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

public class For extends Statement
{
    private Variable loopVar;
    private Number lowerBound, upperBound;
    private Statement stmt;
    public For(Variable loopVar, Number lowerBound, Number upperBound, Statement stmt)
    {
        this.loopVar = loopVar;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.stmt = stmt;
    }

    /**
     * Loops the loop variable through the range [lowerBound, upperBound] and
     * executes the statement during every increment (+1 every time)
     * 
     * @postcondition The loop variable is iterated from lowerBound to upperBound by increments of
     * 1 and the associated statement is executed each time
     * 
     * @param env The current environment
     */
    public void exec(Environment env) throws ScanErrorException
    {
        env.setVariable(loopVar.getId(), lowerBound.eval(env));
        while (loopVar.eval(env) <= upperBound.eval(env))
        {
            stmt.exec(env);
            env.setVariable(loopVar.getId(), loopVar.eval(env)+1);
        }
    }
}
