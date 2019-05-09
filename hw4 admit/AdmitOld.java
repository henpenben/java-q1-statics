
import java.util.*;

public class AdmitOld
{
    double examScore;

    public static void main(String[] args)
    {
    leadingParagraph();
    Scanner console = new Scanner(System.in);
    testType(console);
    
    System.out.println("end");
    
    }
    public static void leadingParagraph()
    { //prints the beginning blurb
    System.out.print("This program compares two applicants to\ndetermine which one");
    System.out.print(" seems like the stronger\napplicant. For each candidate I");
    System.out.print(" will need\neither SAT or ACT scores plus a weighted GPA.");
    System.out.println();
    }

    public static double testType(Scanner console)
    { //ensures user inputs either "1" or "2" and routes them
        System.out.print("\ndo you have 1) SAT scores or 2) ACT scores? ");
        int testType = console.nextInt();
        double score;
        if (testType == 1)
        {
            score = scoreSAT(console);
        }
        else if (testType == 2)
        {
            score = scoreACT(console);
        }
        else
        {
            System.out.println("Try inputting the value \"1\" for SAT scores,");
            System.out.println("or \"2\" for ACT scores");
            testType(console);
        }
        return score;
    }
    
    public static double scoreSAT(Scanner console)
    { //asks for SAT score data inputs
        System.out.print("SAT math? ");
        double math = console.nextDouble();
        System.out.print("SAT critical reading? ");
        double reading = console.nextDouble();
        System.out.print("SAT writing? ");
        double writing = console.nextDouble();
        
        double score = computeSAT(math, reading, writing);
        return score;
    }
    
    public static double computeSAT(double math, double reading, double writing)
    { //computes score (SAT)
        return (2 * math + reading + writing) / 32;
    }
    
    public static double computeACT(double english, double math, double reading, double science)
    { //computes score (ACT)
        return (english + 2 * math + reading + science) / 1.8;
    }
        
    public static double scoreACT(Scanner console)
    { //asks for ACT score data inputs
        System.out.print("ACT English? ");
        double english = console.nextDouble();
        System.out.print("ACT math? ");
        double math = console.nextDouble();
        System.out.print("ACT reading? ");
        double reading = console.nextDouble();
        System.out.print("ACT science? ");
        double science = console.nextDouble();
        
        double score = computeACT(english, math, reading, science);
        return score;
    }
}