package ast;

import emitter2.Emitter;
import environment.Environment;
import scanner.ScanErrorException;

/**
 * Condition represents an boolean condition with two expressions and a comparative
 * operator (=, <>, <, >, <=, >=) in between. It returns a boolean true or false upon
 * evaluation, depending on if the expressions are equal or not equal.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

public class Condition {
    private Expression exp1, exp2;
    private String operator;
    public Condition(Expression exp1, String operator, Expression exp2)
    {
        this.exp1 = exp1;
        this.operator = operator;
        this.exp2 = exp2;
    }
    /**
     * Analyzes the boolean condition and returns the value (true or false)
     * 
     * @param env The current environment
     * @return The value of the booealn condition
     */
    public boolean eval(Environment env) throws ScanErrorException
    {
        int exp1Eval = exp1.eval(env);
        int exp2Eval = exp2.eval(env);
        if (operator.equals("="))
        {
            return (exp1Eval == exp2Eval);
        }
        else if (operator.equals("<>"))
        {
            return (exp1Eval != exp2Eval);
        }
        else if (operator.equals("<"))
        {
            return (exp1Eval < exp2Eval);
        }
        else if (operator.equals(">"))
        {
            return (exp1Eval > exp2Eval);
        }
        else if (operator.equals("<="))
        {
            return (exp1Eval <= exp2Eval);
        }
        else if (operator.equals(">="))
        {
            return (exp1Eval >= exp2Eval);
        }
        else
        {
            throw new ScanErrorException("Comparison operator \'"+operator+"\' not found.");
        }
    }

    /**
     * Compiles the condition by emitting assembly code to evaluate the
     * condition, using beq, bgt, bge..etc to branch off to a given label if false,
     * skipping the statements that follow the if statement
     * 
     * @param e The current emitter
     * @param label The label to branch to if condition is true
     */
    public void compile(Emitter e, String label)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");

        if (operator.equals("="))
        {
            e.emit("#Condition: =");
            e.emit("blt $t0 $v0 "+label);
            e.emit("bgt $t0 $v0 "+label);
        }
        else if (operator.equals("<>"))
        {
            e.emit("#Condition: <>");
            e.emit("beq $t0 $v0 "+label);
        }
        else if (operator.equals("<"))
        {
            e.emit("#Condition: <");
            e.emit("bge $t0 $v0 "+label);
        }
        else if (operator.equals(">"))
        {
            e.emit("#Condition: >");
            e.emit("ble $t0 $v0 "+label);
        }
        else if (operator.equals("<="))
        {
            e.emit("#Condition: <=");
            e.emit("bgt $t0 $v0 "+label);
        }
        else if (operator.equals(">="))
        {
            e.emit("#Condition: >=");
            e.emit("blt $t0 $v0 "+label);
        }
    }

}
