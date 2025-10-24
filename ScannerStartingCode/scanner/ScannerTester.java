package scanner;
import java.io.*;

/**
 * @author Victor Gong
 * @version 9/5/2023
 * 
 */
public class ScannerTester
{
    public static void main(String args[]) throws IOException
    {
        File f1 = new File("ScannerTest.txt");
        File f2 = new File("scannerTestAdvanced.txt");
        InputStream fstream1 = new FileInputStream(f1);
        InputStream fstream2 = new FileInputStream(f2);
        Scanner sc1 = new Scanner(fstream1);
        Scanner sc2 = new Scanner(fstream2);
        while (sc1.hasNext()){
            System.out.println(sc1.nextToken());
        }
        /*
        while (sc2.hasNext()){
            System.out.println(sc2.nextToken());
        }
        */
        
    }
}