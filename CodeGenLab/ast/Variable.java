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

import emitter2.Emitter;
import environment.Environment;
import scanner.ScanErrorException;

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
    public int eval(Environment env) throws ScanErrorException
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

    /**
     * Compiles a variable by emitting assembly code to load the value
     * of the variable into $v0 using the lw command with the variable id
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
        if (!e.isLocalVariable(id))
        {
            e.emit("#Variable: "+id);
            e.emit("la $t0 var"+id);
            e.emit("lw $v0 ($t0)");
        }
        else
        {
            e.emit("#Local Variable: "+id);
            e.emit("lw $v0 " + e.getOffset(id) + "($sp)");
        }
        
    }
}
