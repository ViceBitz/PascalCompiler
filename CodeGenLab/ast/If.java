package ast;

import emitter2.Emitter;
import environment.Environment;
import scanner.ScanErrorException;

/**
 * If represents an IF-THEN statement, which includes a condition and a statement.
 * Upon execution, it checks if condition is true and executes the associated statement according
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

public class If extends Statement {
    private Condition cond;
    private Statement stmt;
    public If(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * Executes the If statement by checking if the condition
     * is true and then executing the statement accordingly
     * 
     * @postcondition If condition true, statement executed
     * @param env The current environment
     */
    public void exec(Environment env) throws ScanErrorException
    {
        if (cond.eval(env))
        {
            stmt.exec(env);
        }
    }

    /**
     * Compiles an IF statement by first compiling the condition, which does all the
     * emission of the branching commands. Then, the method compiles the statement
     * under the if statement and finally emits the endIf label
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
        String label = Emitter.generateLabelId("endIf");

        e.emit("#If");
        cond.compile(e, label);
        stmt.compile(e);
        e.emit(label+":");
    }
}
