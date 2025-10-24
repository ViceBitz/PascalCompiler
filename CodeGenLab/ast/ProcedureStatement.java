package ast;

import java.util.List;

import emitter2.Emitter;
import environment.Environment;
import scanner.ScanErrorException;

/**
 * ProcedureStatement represents a procedure call as a statement rather than an expression.
 * Therefore, users can call the procedure without needing to assign the value to a variable/
 * use it as an expression. The class stores a ProcedureCall object initialized upon this object's
 * initilization (with corresponding procedureId and list of arguments). Upon execution, the
 * ProcedureCall object is simply evaluated without the return value stored.
 * 
 * @author Victor Gong
 * @version 10/24/2023
 *
 */

public class ProcedureStatement extends Statement
{
    ProcedureCall procedureExp;
    public ProcedureStatement(String procedureId, List<Expression> args)
    {
        procedureExp = new ProcedureCall(procedureId, args);
    }

    /**
     * Evaluates the ProcedureCall object associated with this ProcedureStatement,
     * ignoring the return value.
     * 
     * @postcondition ProcedureCall evaluated with current environment
     * @param env The current environment
     */
    public void exec(Environment env) throws ScanErrorException
    {
        procedureExp.eval(env);
    }

    /**
     * Compiles a ProcedureStatement by simply compiling the associated ProcedureCall and ignoring the
     * return value stored in $v0
     */
    public void compile(Emitter e)
    {
        procedureExp.compile(e);
    }
}
