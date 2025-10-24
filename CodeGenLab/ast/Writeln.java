/**
 * Writeln is a statement that prints out the value of an expression. When it's executed,
 * it calls the expression's eval function and outputs the returned value.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

package ast;

import emitter2.Emitter;
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

    /**
     * Compiles the WRITELN statement by first compiling the expression to be printed, then
     * emitting assembly code to move the value of $v0 (where the expression is stored) to $a0
     * (the print value in MIPs), then setting $v0 to 1 (print integer setting) and finally
     * writing syscall to execute the print. The compile method for WRITELN also prints a newline
     * in the same fashion (except using load address for "\n")
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
        e.emit("#Writeln");
        exp.compile(e);
        
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");

        e.emit("la $a0 newline");
        e.emit("li $v0 4");
        e.emit("syscall");
    }
}