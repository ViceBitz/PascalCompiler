package scanner;

import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2023-2024) lab exercise 1
 * @author Victor Gong
 * @version 9/4/2023
 *  
 * Usage:
 * Create a new Scanner object with a parameter of an InputStream or String block. 
 * Use nextToken to get the nextToken in the stream/string
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to us
     */
    public Scanner(InputStream inStream) throws IOException
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString) throws IOException
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    /**
     * Method: getNextChar
     * @postcondition Sets currentChar to the next character, or -1 if EOF
     * 
     * @throws IOException when IO issue encountered
     */
    private void getNextChar() throws IOException
    {
        int charInt = in.read();
        if (charInt == -1 || (char) charInt == '.'){
            eof = true;
            return;
        }
        char c = (char) charInt;
        currentChar = c;
    }
    /**
     * Method: eat
     * Takes in a character and compares it with currentChar; if it matches, advances currentChar ahead one character
     * @postcondition Moves to the next character
     * @param expected The expected currentCharacter
     * 
     * @throws ScanErrorException when illegal character encountered
     * @throws IOException when IO issue encountered
     */
    
    private void eat(char expected) throws ScanErrorException, IOException
    {
        if (expected == currentChar){
            getNextChar();
        }
        else{
            throw new ScanErrorException("Illegal character - expected " + expected + " but received " + currentChar);
        }
    }
    /**
     * Method: hasNext
     * Checks if Scanner has reached end of file
     * @return False if end of file, true otherwise
     */
    public boolean hasNext()
    {
       return !eof;
    }

    /**
     * Iterates scanner until new line or EOF
     * @postcondition All tokens skipped up to new line or EOF
     * 
     * @throws ScanErrorException when illegal character encountered
     * @throws IOException when IO issue encountered
     */
    public void skipToNewline() throws ScanErrorException, IOException
    {
        while (hasNext() && currentChar != '\n' && currentChar != '\r')
        {
            eat(currentChar);
        }
    }
    /**
     * Method: nextToken
     * Skips whitespace and then scans the next token in the input stream; if EOF then returns "END"
     * Catches ScanErrorException, prints an error message, and returns null
     * 
     * @postcondition Iterates the scanner past the next token
     * @return The next token in the input stream, or "END" if EOF
     * 
     * @throws IOException when IO issue encountered
     */
    public String nextToken() throws IOException
    {
        try
        {
            //Skip leading whitespace
            while (hasNext() && isWhiteSpace(currentChar))
            {
                eat(currentChar);
            }

            if (hasNext())
            {
                if (isDigit(currentChar))
                {
                    return scanNumber();
                }
                else if (isLetter(currentChar))
                {
                    return scanIdentifier();
                }
                else if (isOperator(currentChar))
                {
                    String lexeme = scanOperator();
                    if (lexeme.equals("//"))
                    {
                        skipToNewline();
                        return nextToken();
                    }
                    return lexeme;
                }
                else
                {
                    throw new ScanErrorException("Illegal character found - " + currentChar);
                }
            }
            else
            {
                return "EOF";
            }
        }
        catch (ScanErrorException s)
        {
            System.out.println(s.getMessage());
            try
            {
                eat(currentChar);
            }
            catch (ScanErrorException s2)
            {
                //Ignore
            }
            //System.exit(1);
        }
        return null;
        
    }

    /**
     * Takes in a character and checks if it's a digit
     * 
     * @param c The character to analyze
     * @return True if is a digit, false otherwise
     */
    public static boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }

    /**
     * Takes in a character and checks if it's a letter
     * 
     * @param c The character to analyze
     * @return True if is a letter, false otherwise
     */
    public static boolean isLetter(char c)
    {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    /**
     * Takes in a character and checks if it's white space
     * 
     * @param c The character to analyze
     * @return True if is white space, false otherwise
     */
    public static boolean isWhiteSpace(char c)
    {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    /**
     * Takes in a character and checks if it's an operator
     * 
     * @param c The character to analyze
     * @return True if is operator, false otherwise
     */
    public static boolean isOperator(char c)
    {
        char[] operators = {'=','+','-','*','/','%','(',')',':',';','&','|','^','>','<',','};
        for (char expected: operators)
        {
            if (c == expected)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Scans the input for a number (digits) and returns the lexeme found in the input stream or
     * ScanErrorException if illegal character
     * 
     * @precondition The next character is a number
     * @return A string representing the lexeme (number)
     * 
     * @throws ScanErrorException when illegal character encountered
     * @throws IOException when IO issue encountered
     */
    private String scanNumber() throws ScanErrorException, IOException
    {
        String lexeme = "";
        while (isDigit(currentChar))
        {
            lexeme += currentChar;
            eat(currentChar);
        }
        return lexeme;
    }
    
    /**
     * Scans the input for an identifier (letters) and returns the lexeme found in the input stream or ScanErrorException
     * if illegal character
     * 
     * @precondition The next character is a letter
     * @return A string representing the lexeme (identifier)
     * 
     * @throws ScanErrorException when illegal character encountered
     * @throws IOException when IO issue encountered
     */
    private String scanIdentifier() throws ScanErrorException, IOException
    {
        String lexeme = "";
        while (isLetter(currentChar) || isDigit(currentChar))
        {
            lexeme += currentChar;
            eat(currentChar);
        }
        return lexeme;
    }

    /**
     * Scans the input for an operator and returns the lexeme found in the input stream or ScanErrorException 
     * if illegal character
     * Operators should only be 1 character, with the exception of: <=, >=, <>, :=
     * 
     * @precondition The next character is an operator
     * @return A string representing the lexeme (operator)
     * 
     * @throws ScanErrorException when illegal character encountered
     * @throws IOException when IO issue encountered
     */
    private String scanOperator() throws ScanErrorException, IOException
    {
        String lexeme = "";
        lexeme += currentChar;
        eat(currentChar);

        //Check for special 2-character operators
        if (lexeme.equals("<"))
        {
            if (hasNext() && (currentChar == '>' || currentChar == '='))
            {
                lexeme += currentChar;
                eat(currentChar);
            }
        }
        else if (lexeme.equals(">"))
        {
            if (hasNext() && (currentChar == '='))
            {
                lexeme += currentChar;
                eat(currentChar);
            }
        }
        else if (lexeme.equals(":"))
        {
            if (hasNext() && (currentChar == '='))
            {
                lexeme += currentChar;
                eat(currentChar);
            }
        }
        else if (lexeme.equals("="))
        {
            if (hasNext() && (currentChar == '='))
            {
                lexeme += currentChar;
                eat(currentChar);
            }
        }
        else if (lexeme.equals("/"))
        {
            if (hasNext() && (currentChar == '/'))
            {
                lexeme += currentChar;
                eat(currentChar);
            }
        }
        return lexeme;
    }
}
