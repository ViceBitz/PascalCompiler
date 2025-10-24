package ast;

import emitter2.Emitter;
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

    /**
     * Compiles a FOR statement by first assigning the loopVar to the
     * value of the lower bound. Then, a for label is generated and emitted. A condition
     * is created with loopVar <= upperBound and is compiled with an endFor label.
     * The for statement is then compiled with a jump at the end back to the top of for
     * label. Finally, the endFor label is emitted.
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
       new Assignment(loopVar.getId(), lowerBound).compile(e);

        String forLabel = Emitter.generateLabelId("for");
        String endForLabel = Emitter.generateLabelId("endFor");
        
        e.emit("#For");
        
        e.emit(forLabel + ":");

        Condition cond = new Condition(loopVar, "<=", upperBound);
        cond.compile(e, endForLabel);

        stmt.compile(e);
        e.emit("j " + forLabel);
        e.emit(endForLabel + ":");
    }
}
