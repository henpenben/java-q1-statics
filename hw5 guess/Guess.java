/* 
   Henry Hough
   9 February 2019
   CSE142AV
   TA: Madison Doerr
   Assignment #5: Guessing Game

   This program is designed to play a guessing game with the user,
   then report some simple statistics about the game(s).
*/

import java.util.*;

public class Guess
{
    public static final int RANGE_LOWER = 1;   // pick a number between RANGE_LOWER
    public static final int RANGE_UPPER = 100; // and RANGE_UPPER (default 1-100)
    
    public static void main(String[] args)
    {
        Scanner console = new Scanner( System.in );
        
        introduction();
        
        //plays the initial first round
        int guessesMade = playGame( console, getAnswer() );
        int totalGuesses = guessesMade;
        int bestGuess = guessesMade;
        int gamesPlayed = 1;
        System.out.print( "Do you want to play again? " );
        
        while ( playAgain(console) == 1 )
        {//plays any subsequent rounds should the user choose to
            guessesMade = playGame( console, getAnswer() );
            totalGuesses = totalGuesses + guessesMade;
            gamesPlayed++;
            System.out.print( "Do you want to play again? " );
            
            if( bestGuess > guessesMade)
            {//updates bestGuess with new best if applicable
                bestGuess = guessesMade;
            } 
        }

        reportResults( gamesPlayed , totalGuesses , bestGuess ); 
    }
    
    public static void introduction()
    {//prints the introduction paragraph
        System.out.println( "This program allows you to play a guessing game."         );
        System.out.println( "I will think of a number between " + RANGE_LOWER + " and" );
        System.out.println( "" + RANGE_UPPER + " and will allow you to guess until"    );
        System.out.println( "you get it.  For each guess, I will tell you"             );
        System.out.println( "whether the right answer is higher or lower"              );
        System.out.println( "than your guess."                                         );
    }
    
    public static int playGame( Scanner console , int answer ) 
    {//play the guessing game with user
        int guessesMade = 0;
        int guess = 2147483647;
        
        System.out.println();
        System.out.println( "I'm thinking of a number between " + RANGE_LOWER +
                            " and " + RANGE_UPPER + "..." );
                            
        while( guess != answer )
        {//plays the game until you guess answer
            System.out.print( "Your guess? " );
            
            guess = console.nextInt();

            if( guess < answer )
            {
                System.out.println( "It's higher." );
            }
            else //if( guess > answer )
            {
                System.out.println( "It's lower." );
            }
            
            guessesMade++;
        }
        
        if( guess == answer )
        {//prints the victory message
            System.out.print( "You got it right in " + guessesMade ); 
            if( guessesMade == 1 )
            {
                System.out.println(" guess" );
            }
            else
            {
                System.out.println(" guesses" );
            }
        }
        
        
        return guessesMade;
    }
    
    public static int playAgain( Scanner console )
    { //determine if user wants to play again
        String inquiry = console.next();
        String first = inquiry.substring( 0 , 1 );
        int again = 0;
        
        if( first.equals( "y" ) | first.equals( "Y" ) )
        {
            again = 1;
        }
        else if( first.equals( "n" ) | first.equals( "N" ) )
        {
            again = 0;
        }
        
        return again;
    }
    
    public static void reportResults( int gamesPlayed , int totalGuesses , int bestGuess )
    { //report results of the players games
        double guessesPerGame = round( (double) totalGuesses / gamesPlayed );
    
        System.out.println();
        System.out.println( "Overall results:" );
        System.out.println( "    total games   = " + gamesPlayed    );
        System.out.println( "    total guesses = " + totalGuesses   );
        System.out.println( "    guesses/game  = " + guessesPerGame );
        System.out.print(   "    best game     = " + bestGuess      );
    }
    
    public static int getAnswer()
    { //generate a new random number based on range parameter constants
        Random r = new Random();
        int answer = -2147483647;
        int range = ( RANGE_UPPER - RANGE_LOWER ) + 1;
        
        if( range == 1 )
        {//memes
            System.out.print("\nguessing isn't very hard if you set the bounds like that\n");
        }
        if( RANGE_LOWER < 0 ) //adjustment for negative lower bound
        {
            answer = r.nextInt( range ) - Math.abs( RANGE_LOWER );
        }
        else //normal conditions
        {
            answer = r.nextInt ( range + 2 );
        }
        
        return answer;
    }
    
    public static double round(double number)
    { //rounds input to the first decimal
        return Math.round( number * 10.0 / 10.0 );
    }
}