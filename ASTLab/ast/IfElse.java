/**
 * IfElse represents an IF-THEN-ELSE statement, which includes a condition and a statement (if true)
 * and a statement (if false). Upon execution, it checks if condition is true and executes the 
 * first statement accordingly; otherwise, it executes the second statement.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */


package ast;

import environment.Environment;
import scanner.ScanErrorException;

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
}
