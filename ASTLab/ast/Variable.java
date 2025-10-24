/**
 * Variable is an expression that represents a variable with an associated value. It has a private
 * instance variable called 'id' that keeps track of its own identifier, and it returns its value
 * when evaluated.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

package ast;

import environment.Environment;

public class Variable extends Expression
{
    private String id;

    public Variable(String id)
    {
        this.id = id;
    }
    /**
     * Returns the value associated with the variable given by id
     * 
     * @param env The current environment
     * @return The value of the variable given by id
     */
    public int eval(Environment env)
    {
        return env.getVariable(id);
    }

    /**
     * Getter function for the identifier of the variable (string name)
     * 
     * @return The id of this variable
     */
    public String getId()
    {
        return id;
    }   
}
