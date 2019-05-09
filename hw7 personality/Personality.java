/* 
   Henry Hough
   2 March 2019
   CSE142AV
   TA: Madison Doerr
   Assignment #7: Personality
   
   This program is designed to accept an input file of personality test answers,
   then output a text file with the answers transcribed into resulting types
*/

import java.io.*;
import java.util.*;

public class Personality
{
    public static final int DIMENSIONS = 4;
    public static final int ANSWER_GROUPS = 10;
    public static final int ANSWERS_PER_GROUP = 7;
    
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner console = new Scanner(System.in);
        introduction();
        String inputFilename = getFilename(console, "in");
        String outputFilename = getFilename(console, "out");
        
        Scanner input = new Scanner(new File(inputFilename));
        PrintStream output = new PrintStream(new File(outputFilename));
       
        while(input.hasNextLine())
        {
            String name = input.nextLine(); //grab current dataset (line-pair)
            String answers = input.nextLine();
                        
            int[] percentageArr = new int[DIMENSIONS];
            //count each type of answer, compare to get array of percentage B-answers
            percentageArr = compareCounts( count(answers, 'a'), count(answers, 'b') );
            //translate B-answer percentage into appropriate characters
            String personalityType = typeArrayToString( percentageToType( percentageArr ) );
            //output to file
            output.println(name + ": " + Arrays.toString(percentageArr) + " = " + personalityType);
        }
    
    
    }
    
    //prints beginning blurb
    public static void introduction()
    {
        System.out.println("This program processes a file of answers to the");
        System.out.println("Keirsey Temperament Sorter.  It converts the");
        System.out.println("various A and B answers for each person into");
        System.out.println("a sequence of B-percentages and then into a");
        System.out.println("four-letter personality type.");
        System.out.println();
    }
    
    //asks for a filename and returns it
    //uses inOut parameter to print "input" or "output" depending on what it's asking for
    public static String getFilename(Scanner console, String inOut)
    {
        System.out.print(inOut + "put file name? ");
        return console.nextLine();
    }
    
    //accepts 70-char formatted string of answers and a char to be looking for
    //format: 10 groups, 7 answers & 4 types per group, formatted like: 1223344
    //returns formatted array of counts of the char input
    public static int[] count(String answers, char value)
    {
        int[] counts = new int[DIMENSIONS];
        for(int i = 0; i < ANSWER_GROUPS; i++)
        {
            String group = answers.substring(i * ANSWERS_PER_GROUP, (i + 1) * ANSWERS_PER_GROUP);
            for(int j = 0; j < ANSWERS_PER_GROUP; j++)
            { //  
                if( group.charAt(j) == value || group.charAt(j) == Character.toUpperCase(value) )
                {
                    if(j == 0) //first answer type always at position 0
                    {   counts[j]++; //is it more correct to do counts[0] or counts[j] here?
                    }else //test for which answer type
                    { 
                        for(int k = 0; k < DIMENSIONS - 1; k++)
                        {
                            if(j >= (k*2 + 1) && j < (k*2 + 3))
                            {
                                counts[k+1]++;
                            }
                        }
                    }
                }
            }
        }
        return counts;
    }
    
    //compares two input same-length integer arrays (A & B)
    //returns integer array of same length "A is x% of B at index i"
    public static int[] compareCounts(int[] countA, int[] countB)
    {
        int[] percentArr = new int[DIMENSIONS];
        
        for(int i = 0; i < percentArr.length; i++)
        {
            int answersOfCurrentType = countA[i] + countB[i];
            percentArr[i] = (int) Math.round( ((double) countB[i] / answersOfCurrentType) * 100 );
        }
        
        return percentArr;
    }
    
    //each index of input integer array is tested and assigned a character
    //returns array of characters
    public static char[] percentageToType(int[] percentArr)
    {
        char[] typeCharArr = new char[DIMENSIONS];
        for(int i = 0; i < typeCharArr.length; i++)
        {
            if(percentArr[i] == 50)
            {
                typeCharArr[i] = 'X';
            }else{
                typeCharArr[i] = getChar(i, percentArr[i]);
            }
        }
        return typeCharArr;
    }

    //helper method for percentageToType()
    //uses input index i and integer percent
    //outputs corresponding character that would belong in given index with given percentage value
    public static char getChar(int i, int percent)
    {
        if(percent < 50)
        {
            if(i == 0)
            {
                return 'E';
            }else if(i == 1){
                return 'S';
            }else if(i == 2){
                return 'T';
            }else{/*if(i == 3)*/
                return 'J';
            }
        }else{/*if(percent > 50)*/
            if(i == 0)
            {
                return 'I';
            }else if(i == 1){
                return 'N';
            }else if(i == 2){
                return 'F';
            }else{/*if(i == 3)*/
                return 'P';
            }
        }
    }
    
    //converts input character array into a string (without gaps or commas)
    //returns said string
    public static String typeArrayToString(char[] typeArray)
    {
        String personalityType = "";
        for(int i = 0; i < typeArray.length; i++)
        {
            personalityType += typeArray[i];
        }
        return personalityType;
    }
}