/**
 * Statement is an abstract, template class representing a code statement (e.g. Block,
 * while loop, assignment ..etc). It contains an abstract method called exec, which should
 * run (execute) the statement.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

package ast;

import environment.Environment;
import scanner.ScanErrorException;

public abstract class Statement {
    /**
     * Executes the corresponding statement (abstract method)
     * 
     * @postcondition Statement is executed
     * @param env The current environment
     */
    public abstract void exec(Environment env) throws ScanErrorException;
}
