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
}
