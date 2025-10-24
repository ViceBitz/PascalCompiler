package ast;

import java.util.List;

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
    private Statement stmt;
    public ProcedureDeclaration(String procedureId, List<Variable> params, Statement stmt)
    {
        this.procedureId = procedureId;
        this.params = params;
        this.stmt = stmt;
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
     * Executes the ProcedureDeclaration by executing the associated statement
     * @postcondition The corresponding statement is executed
     * @param env The current environment
     */
    public void exec(Environment env)
    {
        env.setProcedure(procedureId, this);
    }
}
