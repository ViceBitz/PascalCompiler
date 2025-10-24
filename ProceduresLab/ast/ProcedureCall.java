package ast;

import java.util.List;

import environment.Environment;
import scanner.ScanErrorException;

/**
 * ProcedureCall is a type of expression that represents a call to a specific ProcedureDeclaration
 * and the value that the procedure returns. Upon evaluation, it creates a local environment, sets
 * the parameters to the arguments, creates a new variable with id as its own name to act as a return
 * value, runs the procedure statement, and returns the return variable.
 * 
 * @author Victor Gong
 * @version 10/24/2023
 *
 */

public class ProcedureCall extends Expression
{
    private String procedureId;
    private List<Expression> args;
    public ProcedureCall(String procedureId, List<Expression> args)
    {
        this.procedureId = procedureId;
        this.args = args;
    }

    /**
     * Creates a local environment, sets the parameters to the arguments, creates a new variable
     * with id as its own name to act as a return value, runs the procedure statement, and
     * returns the return variable
     * 
     * @param env The current environment
     * @return The return value resulting from calling the procedure
     */
    public int eval(Environment env) throws ScanErrorException
    {
        Environment localEnv = new Environment(env.getGlobalEnvironment());
        ProcedureDeclaration declaration = env.getProcedure(procedureId);

        List<Variable> params = declaration.getParams();
        for (int i=0;i<params.size();i++)
        {
            localEnv.declareVariable(params.get(i).getId(), args.get(i).eval(env));
        }
        localEnv.declareVariable(procedureId, 0);

        declaration.getStatement().exec(localEnv);
        return localEnv.getVariable(procedureId);
    }
}
