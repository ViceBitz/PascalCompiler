package ast;

import java.util.*;

import environment.Environment;
import scanner.ScanErrorException;

/**
 * Program represents the entire code, consisting of ProcedureDeclarations followed by a main statement.
 * The list of ProcedureDeclarations with the main statement are stored in respective instance variables.
 * Upon execution, the program executes all of the procedure declarations and then the main statement.
 * 
 * @author Victor Gong
 * @version 10/24/2023
 *
 */

public class Program {
    List<ProcedureDeclaration> procedures;
    Statement mainStmt;
    public Program(List<ProcedureDeclaration> procedures, Statement mainStmt)
    {
        this.procedures = procedures;
        this.mainStmt = mainStmt;
    }
    public void exec(Environment env) throws ScanErrorException
    {
        for (ProcedureDeclaration p: procedures)
        {
            p.exec(env);
        }
        mainStmt.exec(env);
    }
}
