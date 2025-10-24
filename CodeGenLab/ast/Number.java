package ast;

import emitter2.Emitter;
import environment.Environment;

/**
 * Number is a type of expression that represents a single number. When it's evaluated, it returns
 * the number associated with it.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

 
public class Number extends Expression
{
    private int value;
    public Number(int value)
    {
        this.value = value;
    }
    /**
     * Returns the integer associated with this Number object
     * 
     * @param env The current environment
     * @return The corresponding integer
     */
    public int eval(Environment env)
    {
        return value;
    }

    /**
     * Compiles the number by emitting assembly code to load the value of this
     * number into $v0
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
        e.emit("#Number");
        e.emit("li $v0 "+value);
    }
}
