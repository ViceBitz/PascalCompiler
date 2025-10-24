/**
 * Readln scans the console for integer input and stores the input into a designated variable. Upon execution, it
 * reads the console and stores the value.
 * 
 * @author Victor Gong
 * @version 10/19/2023
 *
 */

package ast;

import java.util.Scanner;

import emitter2.Emitter;
import environment.Environment;

public class Readln extends Statement{
    private String varId;
    private static Scanner scanner = new Scanner(System.in);
    public Readln(String varId)
    {
        this.varId = varId;
    }
    /**
     * Reads an integer from the input and stores it into the accompanying variable
     * 
     * @postcondition Scans an integer and stores value into variable
     * @param env The current environment
     */
    public void exec(Environment env)
    {
        int num = scanner.nextInt();
        env.setVariable(varId, num);
    }

    /**
     * Compiles a READLN statement by emitting assembly code with $v0 set to
     * 5 (read integer) and saving the result in $v0 (automatic, no code needed)
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
        e.emit("#ReadIn");
       e.emit("li $v0 5");
       e.emit("syscall");
    }
}
