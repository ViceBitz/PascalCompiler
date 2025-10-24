package ast;

import java.util.List;

import emitter2.Emitter;
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

        //Params
        List<Variable> params = declaration.getParams();
        for (int i=0;i<params.size();i++)
        {
            localEnv.declareVariable(params.get(i).getId(), args.get(i).eval(env));
        }

        //Return value
        localEnv.declareVariable(procedureId, 0);

        //Local variables
        List<Variable> localVariables = declaration.getLocalVariables();
        for (int i=0;i<localVariables.size();i++)
        {
            localEnv.declareVariable(localVariables.get(i).getId(), 0);
        }

        declaration.getStatement().exec(localEnv);
        return localEnv.getVariable(procedureId);
    }

    /**
     * Compiles the ProcedureCall by pushing all of the arguments onto the stack, then
     * emitting code to jump and link to the procedureId
     */
    public void compile(Emitter e)
    {
        e.emit("#Procedure Call: " + procedureId);
        e.emit("#Push parameters");
        for (Expression arg: args)
        {
            arg.compile(e);
            e.emitPush("$v0");
        }
        e.emit("jal "+procedureId);
        for (Expression arg: args)
        {
            e.emitPop("$t0");
        }
    }
}
