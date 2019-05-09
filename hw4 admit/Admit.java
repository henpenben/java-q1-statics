/* 
   Henry Hough
   3 February 2019
   CSE142AV
   TA: Madison Doerr
   Assignment #4: Admissions

   This program is designed to compare two students' SAT or ACT scores
   in combination with their weighted GPA to determine which student
   would be a more competitive applicant to a college
*/

import java.util.*;

public class Admit
{

    public static void main(String[] args)
    {
        Scanner console = new Scanner(System.in);
        introBlurb();
        
        System.out.println("Information for applicant #1:");
        double applicant1 = getScore(console);
        
        System.out.println("Information for applicant #2:");
        double applicant2 = getScore(console);
        
        compare(applicant1, applicant2);
    }
    
    public static void introBlurb()
    { //prints blurb at start
        System.out.println("This program compares two applicants to ");
        System.out.println("determine which one seems like the stronger ");
        System.out.println("applicant.  For each candidate I will need ");
        System.out.println("either SAT or ACT scores plus a weighted GPA.");
        System.out.println();
    }
    
    public static double getScore(Scanner console)
    { //sums subscores
        return examScore(console) + GPAScore(console);
    }
    
    public static double examScore(Scanner console)
    { //outputs exam score as a double
        int testNumber = testType(console);
        
        if(testNumber == 1)
        {
            double examScore = calcScoreSAT(console);
            return examScore;
        }
        else if(testNumber == 2)
        {
            double examScore = calcScoreACT(console);
            return examScore;
        }
        else
        {
            System.out.println("Try inputting 1 or 2.");
            examScore(console);
        }
        
        return 0;
    }
    
    public static int testType(Scanner console)
    { //asks for exam type
        System.out.print("    do you have 1) SAT scores or 2) ACT scores? ");
        int testNumber = console.nextInt();
        return testNumber;
    }
    
    public static double GPAScore(Scanner console)
    { //asks for GPA information and calculates GPA score
        System.out.print("    overall GPA? ");
        double overallGPA = console.nextDouble();
        System.out.print("    max GPA? ");
        double maxGPA = console.nextDouble();
        System.out.print("    Transcript Multiplier? ");
        double multiplier = console.nextDouble();
        
        double GPAscore = 100.0 * multiplier * (overallGPA / maxGPA);
        System.out.println("    GPA score = " + round(GPAscore));
        System.out.println();
        return GPAscore;
    }
    
    public static double calcScoreSAT(Scanner console)
    { //asks for SAT scores and computes compound score
        System.out.print("    SAT math? ");
        int math = console.nextInt();
        System.out.print("    SAT critical reading? ");
        int reading = console.nextInt();
        System.out.print("    SAT writing? ");
        int writing = console.nextInt();
        
        double scoreSAT = (2.0 * math + reading + writing) / 32.0;
        System.out.println("    exam score = " + round(scoreSAT));
        
        return scoreSAT;
    }
    
    public static double calcScoreACT(Scanner console)
    { //asks for ACT scores and computes compound score
        System.out.print("    ACT English? ");
        int english = console.nextInt();
        System.out.print("    ACT math? ");
        int math = console.nextInt();
        System.out.print("    ACT reading? ");
        int reading = console.nextInt();
        System.out.print("    ACT science? ");
        int science = console.nextInt();
        
        double scoreACT = (english + 2.0 * math + reading + science) / 1.8;
        System.out.println("    exam score = " + round(scoreACT));
        
        return scoreACT;
    }
    
    public static void compare(double one, double two)
    { //compares the two applicants
        System.out.println("First applicant overall score  = " + round(one));
        System.out.println("Second applicant overall score = " + round(two));
        if(one > two)
        {
            System.out.println("The first applicant seems to be better");
        }
        else if(two > one)
        {
            System.out.println("The second applicant seems to be better");
        }
        else if(one == two)
        {
            System.out.println("The two applicants seem to be equal");
        }
        else
        {
            System.out.println("Your input data didn't make sense...");
        }
    
    }
    
    public static double round(double number)
    { //rounds input to the first decimal
        return Math.round(number * 10.0) / (10.0);
    }
    
}