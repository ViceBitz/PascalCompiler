package parser;
import java.io.*;

import ast.Program;
import emitter2.Emitter;
import environment.Environment;
import scanner.Scanner;
import scanner.ScanErrorException;

/**
 * A tester class used to test the Parser class, which processes code
 * @author Victor Gong
 * @version 10/24/2023
 * 
 */
public class ParseTester
{
    public static void main(String args[]) throws IOException, ScanErrorException
    {
        int TEST_NUMBER = 3;
        for (int i=1;i<=TEST_NUMBER;i++)
        {
            System.out.println("ParserTest" + i + ".txt" + " ~ Output");
            File f = new File("ParserTest" + i + ".txt");
            InputStream fstream = new FileInputStream(f);
            Scanner sc = new Scanner(fstream);
            Parser ps = new Parser(sc);
            Environment env = new Environment();
            Emitter e = new Emitter("output" + i + ".asm");

            Program pgrm = ps.parseProgram();
            pgrm.compile(e);
            pgrm.exec(env);
            
        }
        
    }
}