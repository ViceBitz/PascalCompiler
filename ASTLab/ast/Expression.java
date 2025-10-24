/**
 * Expression is an abstract, template class that includes an abstract method called eval.
 * The eval method should return the integer value of expression.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

package ast;

import environment.Environment;
import scanner.ScanErrorException;

public abstract class Expression 
{
    /**
     * Returns the value of the corresponding expression (abstract method)
     * 
     * @param env The current environment
     * @return The value of the expression
     */
    public abstract int eval(Environment env) throws ScanErrorException;
}

