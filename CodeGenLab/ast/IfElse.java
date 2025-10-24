package ast;

import emitter2.Emitter;
import environment.Environment;
import scanner.ScanErrorException;

/**
 * IfElse represents an IF-THEN-ELSE statement, which includes a condition and a statement (if true)
 * and a statement (if false). Upon execution, it checks if condition is true and executes the 
 * first statement accordingly; otherwise, it executes the second statement.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

public class IfElse extends Statement {
    private Condition cond;
    private Statement stmtIf;
    private Statement stmtElse;
    public IfElse(Condition cond, Statement stmtIf, Statement stmtElse)
    {
        this.cond = cond;
        this.stmtIf = stmtIf;
        this.stmtElse = stmtElse;
    }

    /**
     * Executes the If-Else statement by checking if the condition is true, executing
     * the first statement if true and second statement if false
     * 
     * @postcondition If condition true, first statement executed; otherwise, second
     * statement executed
     * @param env The current environment
     */

    public void exec(Environment env) throws ScanErrorException
    {
        if (cond.eval(env))
        {
            stmtIf.exec(env);
        }
        else
        {
            stmtElse.exec(env);
        }
    }

     /**
     * Compiles an IF-ELSE statement by first compiling the condition, which does all the
     * emission of the branching commands (to elseIf label). Then, the method compiles the
     * statement under the if statement along with a j to endIf. Next, it emits the elseIf label
     * with the else statement compilation and  finally emits the endIf label.
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
        String elseLabel = Emitter.generateLabelId("elseIf");
        String endLabel = Emitter.generateLabelId("endIf");

        e.emit("#IfElse");
        cond.compile(e, elseLabel);
        stmtIf.compile(e);
        e.emit("j " + endLabel);
        e.emit(elseLabel+":");
        stmtElse.compile(e);
        e.emit(endLabel+":");

    }
}
