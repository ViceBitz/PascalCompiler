/**
 * Number is a type of expression that represents a single number. When it's evaluated, it returns
 * the number associated with it.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

package ast;

import environment.Environment;

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
}
