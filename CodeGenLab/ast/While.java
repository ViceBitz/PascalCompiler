/**
 * While is a statement that represents a while loop, running a code statement only if its associated
 * condition is true. Upon execution, it checks if its condition is true and executes the statement
 * accordingly, and continues doing this until the condition becomes false.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

package ast;

import emitter2.Emitter;
import environment.Environment;

import scanner.ScanErrorException;

public class While extends Statement{
    private Condition cond;
    private Statement stmt;
    public While(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }
    /**
     * Executes the While loop by continuously checking if the condition
     * is true and executing the statement if so. Stops when the condition
     * becomes false.
     * 
     * @postcondition Statement continuously executed until condition becomes false
     * @param env The current environment
     */
    public void exec(Environment env) throws ScanErrorException
    {
        while (cond.eval(env))
        {
            stmt.exec(env);
        }
    }

    /**
     * Compiles a WHILE statement by first emitting a while label, checking 
     * the while condition (with an endWhile label passed), compiling
     * the statement within, emitting a jump command to jump back to the
     * beginning of the loop, and finally emitting the endWhile label
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
        String whileLabel = Emitter.generateLabelId("while");

        e.emit("#While");
        e.emit(whileLabel + ":");

        String endWhileLabel = Emitter.generateLabelId("endWhile");
        cond.compile(e, endWhileLabel);
        stmt.compile(e);
        e.emit("j "+whileLabel);
        
        e.emit(endWhileLabel + ":");
    }
}
