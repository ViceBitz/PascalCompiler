package ast;

import java.util.List;

import emitter2.Emitter;
import environment.Environment;

/**
 * ProcedureDeclaration declares a new procedure with an associated set of params as well as a statement
 * to execute upon procedure call. Upon execution, it simply executes the statement associated with it.
 * 
 * @author Victor Gong
 * @version 10/24/2023
 *
 */

public class ProcedureDeclaration extends Statement
{
    private String procedureId;
    private List<Variable> params;
    private List<Variable> localVars;
    private Statement stmt;
    public ProcedureDeclaration(String procedureId, List<Variable> params, List<Variable> localVars, Statement stmt)
    {
        this.procedureId = procedureId;
        this.params = params;
        this.localVars = localVars;
        this.stmt = stmt;
    }

    /**
     * Get the ProcedureDeclaration object's corresponding id
     * @return The corresponding procedure id
     */
    public String getId()
    {
        return procedureId;
    }

    /**
     * Get the ProcedureDeclaration object's corresponding statement
     * @return The corresponding statement
     */
    public Statement getStatement()
    {
        return stmt;
    }

    /**
     * Get the ProcedureDeclaration object's corresponding set of params
     * @return The corresponding set of params
     */
    public List<Variable> getParams()
    {
        return params;
    }

    /**
     * Get the ProcedureDeclaration object's corresponding set of local variables
     * @return The corresponding set of local variables
     */
    public List<Variable> getLocalVariables()
    {
        return localVars;
    }

    /**
     * Executes the ProcedureDeclaration by executing the associated statement
     * @postcondition The corresponding statement is executed
     * @param env The current environment
     */
    public void exec(Environment env)
    {
        env.setProcedure(procedureId, this);
    }

    /**
     * Compiles the ProcedureDeclaration by first emitting the procedureId label and pushing the return value onto the stack.
     * Next, the $ra is pushed onto the stack, followed by all of the local variables, and then the procedure context is set.
     * At the end of the program, $ra is popped along with all of the local variables and return value, and finally code is
     * emitted to jump return back to the caller.
     * 
     * @param e The current emitter
     */
    public void compile(Emitter e)
    {
        e.emit("#Procedure: " + procedureId);

        //Emit label
        e.emit(procedureId + ":");

        //Push return value
        e.emit("#Push return value");
        e.emit("li $v0 0"); 
        e.emitPush("$v0");

        //Push return address
        e.emit("#Push $ra");
        e.emitPush("$ra"); 

        //Push local variables
        e.emit("#Push local variables");
        for (Variable var: localVars) 
        {
            e.emitPush("$v0");
        }

        e.setProcedureContext(this);

        stmt.compile(e);

        //Pop local variables
        for (Variable var: localVars)
        {
            e.emitPop("$t0");
        }

        //Pop return address
        e.emitPop("$ra");
        
        //Pop return value (into $v0)
        e.emitPop("$v0");

        e.emit("jr $ra");

        e.clearProcedureContext();
    }

}
