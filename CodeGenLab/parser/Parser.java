package parser;

import java.io.IOException;
import java.util.*;

import scanner.ScanErrorException;
import scanner.Scanner;

import ast.*;
import ast.Number;

/**
 * Parser utilizes the Scanner class to process sequences of tokens into numbers, factors, expressions,
 * and statements, creating and returning objects of the respective method (e.g. parseFactor returns new Expression).
 * Instead of immediately executing the statement, this Parser creates an abstract syntax tree with a Program object
 * at the root, which is executed altogether.
 * 
 * @author Victor Gong
 * @version 10/24/2023
 *
 */

public class Parser
{
    private Scanner scanner;
    private String currentToken;

    public Parser(Scanner sc) throws IOException, ScanErrorException
    {
        scanner = sc;
        currentToken = sc.nextToken();
    }

    /**
     * Compares the current token with the expected token. If they match, the current token is updated to
     * the next token. If they don't, a ScanErrorException is thrown.
     * 
     * @param expected The expected token
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    private void eat(String expected) throws IOException, ScanErrorException
    {
        if (currentToken.equals(expected))
        {
            currentToken = scanner.nextToken();
        }
        else
        {
            throw new ScanErrorException("Received: " + currentToken + " | Expected: " + expected);
        }
    }

    /**
     * Parses a integer and returns a Number object, or throws an error if not an integer
     * 
     * @postcondition Number token has been scanned/eaten
     * @return A Number object representing the parsed integer
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    private Number parseNumber() throws IOException, ScanErrorException
    {
        int ret = Integer.parseInt(currentToken);
        eat(currentToken);
        return new Number(ret);
    }

    /**
     * Parses and returns a Program object, which consists of a list of ProcedureDeclaration objects
     * followed by a list of statements, or throws an error if wrong type/not recognized
     * 
     * @postcondition Entire program, including period, completely processed and eaten
     * @return A Program object representing the parsed program
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    public Program parseProgram() throws IOException, ScanErrorException
    {
        List<String> globalVars = new ArrayList<>();
        while (currentToken.equals("VAR"))
        {
            eat("VAR");
            globalVars.add(currentToken);
            eat(currentToken);
            while (currentToken.equals(","))
            {
                eat(",");
                globalVars.add(currentToken);
                eat(currentToken);
            }
            eat(";");
        }
        List<ProcedureDeclaration> procedures = new ArrayList<>();
        while (currentToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String procedureId = currentToken;
            eat(procedureId);
            eat("(");

            //Parse parameters
            List<Variable> params = new ArrayList<>();
            if (!currentToken.equals(")"))
            {
                String paramId = currentToken;
                eat(paramId);
                params.add(new Variable(paramId));
                while (currentToken.equals(","))
                {
                    eat(",");
                    paramId = currentToken;
                    eat(paramId);
                    params.add(new Variable(paramId));
                }
            }
            eat(")");
            eat(";");
            
            //Parse local variables
            List<Variable> localVariables = new ArrayList<>();
            while (currentToken.equals("VAR"))
            {
                eat(currentToken);
                String localVarId = currentToken;
                eat(localVarId);
                eat(";");
                localVariables.add(new Variable(localVarId));
            }
            Statement stmt = parseStatement();
            procedures.add(new ProcedureDeclaration(procedureId, params, localVariables, stmt));
        }
        Statement mainStmt = parseStatement();
        return new Program(globalVars, procedures, mainStmt);
    }
    /**
     * Parses and returns a Statement object (WRITELN, READLN, block, assignment, IF, IF-ELSE, WHILE, FOR)
     * (with the specific class depending on the type of statement), or throws an error if
     * wrong type/not recognized
     * 
     * @postcondition Code statement (including semicolon) completely scanned/eaten
     * @return A Statement object representing the parsed statement
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    public Statement parseStatement() throws IOException, ScanErrorException
    {
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if (currentToken.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            String id = currentToken;
            eat(currentToken);
            eat(")");
            eat(";");
            return new Readln(id);
        }
        else if (currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            List<Statement> stmts = new ArrayList<>();
            while (!currentToken.equals(("END")))
            {
                stmts.add(parseStatement());
            }
            eat("END");
            eat(";");
            return new Block(stmts);
            
        }
        else if (currentToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            Statement stmtIf = parseStatement();
            if (currentToken.equals("ELSE"))
            {
                eat("ELSE");
                Statement stmtElse = parseStatement();
                return new IfElse(cond, stmtIf, stmtElse);
            }
            return new If(cond, stmtIf);
        }
        else if (currentToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            Statement stmt = parseStatement();
            return new While(cond, stmt);
        }
        else if (currentToken.equals("FOR"))
        {
            eat("FOR");
            String id = currentToken;
            eat(currentToken);
            eat(":=");
            Number lowerBound = parseNumber();
            eat("TO");
            Number upperBound = parseNumber();
            eat("DO");
            return new For(new Variable(id), lowerBound, upperBound, parseStatement());
        }
        else
        {
            String id = currentToken;
            eat(currentToken);
            if (currentToken.equals("("))
            {
                eat("(");
                List<Expression> args = new ArrayList<>();
                if (!currentToken.equals(")"))
                {
                    args.add(parseExpression());
                    while (currentToken.equals(","))
                    {
                        eat(",");
                        args.add(parseExpression());
                    }
                }
                eat(")");
                eat(";");
                return new ProcedureStatement(id, args);
            }
            eat(":=");
            Expression exp = parseExpression();
            eat(";");
            return new Assignment(id, exp);
        }
        
    }

    /**
     * Parses a factor ((), -, number, procedure id, variable id) and returns it as a new Expression object (Number, BinOp,
     * ProcedureCall, Variable), or throws an error if wrong type/not recognized
     * 
     * @postcondition Entire factor has been scanned/eaten
     * @return The value of the parsed factor
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    private Expression parseFactor() throws IOException, ScanErrorException
    {
        if (currentToken.equals("("))
        {
            eat("(");
            Expression ret = parseExpression();
            eat(")");
            return ret;
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            return new BinOp(new Number(-1), "*", parseFactor());
        }
        else if (Scanner.isDigit(currentToken.charAt(0)))
        {
            return parseNumber();
        }
        else
        {
            String id = currentToken;
            eat(currentToken);
            if (currentToken.equals("("))
            {
                eat("(");
                List<Expression> args = new ArrayList<>();
                if (!currentToken.equals(")"))
                {
                    args.add(parseExpression());
                    while (currentToken.equals(","))
                    {
                        eat(",");
                        args.add(parseExpression());
                    }
                }
                eat(")");
                return new ProcedureCall(id, args);
            }
            return new Variable(id);
        }
    }

    /**
     * Parses a term a term (Factor (*,/) Factor) and returns an Expression object (BinOp),
     * or throws an error if wrong type/not recognized
     * 
     * @postcondition Entire term has been scanned/eaten
     * @return A new Expression object (BinOp) representing the parsed term
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    private Expression parseTerm() throws IOException, ScanErrorException
    {
        Expression ret = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            String op = currentToken;
            eat(currentToken);
            ret = new BinOp(ret, op, parseFactor());
        }
        return ret;
    }

    /**
     * Processes an expression (Factor (+,-) Factor) and returns an Expression object (BinOp),
     * or throws an error if wrong type/not recognized
     * @postcondition Operators (+, -), factors are all scanned/eaten
     * @return A new Expression object (BinOp) representing the parsed expression
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    private Expression parseExpression() throws IOException, ScanErrorException
    {
        Expression ret = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            String op = currentToken;
            eat(currentToken);
            ret = new BinOp(ret, op, parseTerm());
        }
        return ret;
    }

    /**
     * Parses a comparison operator for boolean conditions (=, <>, <, >, <=, >=), or
     * throws an error if wrong type/not recognized
     * 
     * @postcondition The comparison operator has been scanned/eaten
     * @return The comparison operator
     * @throws IOException
     * @throws ScanErrorException
     */
    private String parseComparisonOperator() throws IOException, ScanErrorException
    {
        if (currentToken.equals("=") || currentToken.equals("<>") || currentToken.equals("<")
        || currentToken.equals(">") || currentToken.equals("<=") || currentToken.equals(">="))
        {
            String op = currentToken;
            eat(currentToken);
            return op;
        }
        else
        {
            throw new ScanErrorException("Comparison operator not found, instead found: \'"+currentToken+"\'.");
        }
    }

    /**
     * Parses a boolean condition and returns a new Condition object, or throws
     * an error if wrong type/not recognized
     * 
     * @return A new Condition object representing the parsed boolean condition
     * @throws IOException
     * @throws ScanErrorException
     */
    private Condition parseCondition() throws IOException, ScanErrorException
    {
        Expression exp1 = parseExpression();
        String op = parseComparisonOperator();
        Expression exp2 = parseExpression();
        return new Condition(exp1, op, exp2);
    }

}