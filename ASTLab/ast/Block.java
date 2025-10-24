/**
 * Block represents an statement consisting of a list of numerous other statements, sandwiched between
 * a "BEGIN" and "END" keyword. It executes all of the statements within the list upon its own
 * execution.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 *
 */

package ast;

import java.util.List;

import environment.Environment;
import scanner.ScanErrorException;

public class Block extends Statement
{
    private List<Statement> stmts;
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Executes the Block by executing all of the statements in its list
     * 
     * @postcondition All the statements in the list 'stmts' have been
     * executed
     * @param env The current environment
     */

    public void exec(Environment env) throws ScanErrorException
    {
        for (Statement st: stmts)
        {
            st.exec(env);
        }
    }
}
