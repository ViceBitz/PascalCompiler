package parser;


/**
 * Parser utilizes the Scanner class to process sequences of tokens into numbers, factors, expressions,
 * and statements. Ultimately, this class executes statements of code in the grammar of Pascal.
 * 
 * @author Victor Gong
 * @version 10/4/2023
 *
 */

import java.io.IOException;
import java.util.*;

import scanner.ScanErrorException;
import scanner.Scanner;

public class Parser
{
    private Scanner scanner;
    private String currentToken;
    private Map<String,Integer> variableValues;

    public Parser(Scanner sc) throws IOException, ScanErrorException
    {
        scanner = sc;
        currentToken = sc.nextToken();
        variableValues = new HashMap<>();
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
     * Processes a integer, or throws an error if not an integer
     * 
     * @postcondition Number token has been eaten
     * @return The value of the parsed integer
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    private int parseNumber() throws IOException, ScanErrorException
    {
        int ret = Integer.parseInt(currentToken);
        eat(currentToken);
        return ret;
    }

    /**
     * Processes and executes code statements (WRITELN(expression), BEGIN statement END, or id := expression),
     * or throws an error if wrong type/not recognized
     * 
     * @postcondition Code statement (including semicolon) completely eaten and executed (or error thrown)
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    public void parseStatement() throws IOException, ScanErrorException
    {
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            System.out.println(parseExpression());
            eat(")");
        }
        else if (currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            while (!currentToken.equals(("END")))
            {
                parseStatement();
            }
            eat("END");
            
        }
        else
        {
            String id = currentToken;
            eat(currentToken);
            eat(":=");
            variableValues.put(id, parseExpression());
        }
        eat(";");
    }

    /**
     * Processes a factor (expression with paranthesis/negative sign, or variable name), or throws an error if wrong type/not recognized
     * @postcondition Sign symbol, paranthesis, and expression are eaten
     * @return The value of the parsed factor
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    private int parseFactor() throws IOException, ScanErrorException
    {
        if (currentToken.equals("("))
        {
            eat("(");
            int ret = parseExpression();
            eat(")");
            return ret;
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            return -parseFactor();
        }
        else if (Scanner.isDigit(currentToken.charAt(0)))
        {
            return parseNumber();
        }
        else
        {
            String id = currentToken;
            eat(currentToken);
            if (variableValues.containsKey(id))
            {
                return variableValues.get(id);
            }
            else
            {
                throw new ScanErrorException("Variable \'" + id + "\' never defined!");
            }
        }
    }

    /**
     * Processes a term (factors split by multiplication, division), or throws an error if wrong type/not recognized
     * @postcondition Operators (*, /), factors are all eaten/processed
     * @return The value of the parsed term
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    private int parseTerm() throws IOException, ScanErrorException
    {
        int ret = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            if (currentToken.equals("*"))
            {
                eat("*");
                ret *= parseFactor();
            }
            else if (currentToken.equals("/"))
            {
                eat("/");
                ret /= parseFactor();
            }
        }
        return ret;
    }

    /**
     * Processes an expression (factors split by addition, subtraction), or throws an error if wrong type/not recognized
     * @postcondition Operators (+, -), factors are all eaten/processed
     * @return The value of the parsed expression
     * @throws IOException If error occured in IO
     * @throws ScanErrorException If unrecognized character found
     */
    private int parseExpression() throws IOException, ScanErrorException
    {
        int ret = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            if (currentToken.equals("+"))
            {
                eat("+");
                ret += parseTerm();
            }
            else if (currentToken.equals("-"))
            {
                eat("-");
                ret -= parseTerm();
            }
        }
        return ret;
    }

}