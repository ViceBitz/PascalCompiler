package parser;
import java.io.*;

import scanner.Scanner;
import scanner.ScanErrorException;

/**
 * A tester class used to test the Parser class, which processes units of code
 * @author Victor Gong
 * @version 10/4/2023
 * 
 */
public class ParseTester
{
    public static void main(String args[]) throws IOException, ScanErrorException
    {
        for (int i=1;i<=5;i++)
        {
            System.out.println("ParserTest" + i + ".txt" + " ~ Output");
            File f = new File("ParserTest" + i + ".txt");
            InputStream fstream = new FileInputStream(f);
            Scanner sc = new Scanner(fstream);
            Parser ps = new Parser(sc);
            while (sc.hasNext())
            {  
                ps.parseStatement();
            }
            
        }
        
    }
}