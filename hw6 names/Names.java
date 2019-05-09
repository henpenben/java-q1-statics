/* 
   Henry Hough
   23 February 2019
   CSE142AV
   TA: Madison Doerr
   Assignment #6: Names

   This program is designed to let the user choose a name,
   then, if applicable, show the popularity of the name graphically.
*/

import java.util.*;
import java.awt.*;
import java.io.*;

public class Names
{                                               // names2 | names
    public static final int NUMBER_OF_DECADES = 14; // 10 | 14
    public static final int COLUMN_WIDTH = 70; //      90 | 70
    public static final int STARTING_YEAR = 1880; // 1920 | 1880
    public static final int PANEL_WIDTH = COLUMN_WIDTH * NUMBER_OF_DECADES;


    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner input = new Scanner(new File("names.txt"));
        
        introduction();
        String foundInfo = findInfo(input, getSexAndName());        
        
        if(foundInfo.equals("not found"))
        { //check if not found
            System.out.println("name/sex combination not found");
        }else{ //create and populate drawing panel
            drawGraph(foundInfo);
        }       
    }
    
    //just prints the introductory blurb
    public static void introduction()
    {
        System.out.println("This program allows you to search through the");
        System.out.println("data from the Social Security Administration");
        System.out.println("to see how popular a particular name has been");
        System.out.println("since " + STARTING_YEAR);
        System.out.println();
    }
    //prompts user to choose a name and associated sex to look up
    //returns both with format "sexname" (ie "mhenry")
    public static String getSexAndName()
    {
        Scanner console = new Scanner(System.in);
        System.out.print("name? ");
        String givenName = console.next();
        System.out.print("sex (M or F)? ");
        String givenSex = console.next();
        
        return givenSex + givenName;
    }
    
    //checks to see if name is present in data, returns the appropriate line if so
    //if name/sex combination not present, returns "not found"
    public static String findInfo(Scanner input, String sexAndName) 
    {   
        while( input.hasNextLine() )
        {
            String currentLine = input.nextLine();
            Scanner line = new Scanner( currentLine );
            if( sexAndName.substring(1).equalsIgnoreCase( line.next() ) 
                &&sexAndName.substring(0, 1).equalsIgnoreCase( line.next() ))
            {
                return currentLine;
            }
        }
        return "not found";
    }  
    
    //controls drawing using methods setupGraph(), findY(), and drawData()
    //accepts string of info found from findInfo() method
    public static void drawGraph(String foundInfo)
    {
        DrawingPanel panel = new DrawingPanel(PANEL_WIDTH, 550);
        Graphics g = panel.getGraphics();
        setupGraph(g);
        Scanner data = new Scanner(foundInfo);
        String foundName = data.next();
        String foundSex = data.next();
          
        int rating = data.nextInt();
        int y2 = 0;
        for(int i = 0; i < NUMBER_OF_DECADES - 1; i++)
        {
            int nextRating = data.nextInt();
            y2 = findY(nextRating);
            drawData(g, (i * COLUMN_WIDTH), findY(rating), y2, rating, foundName, foundSex);
            rating = nextRating;
        }
        g.drawString(foundName + " " + foundSex + " " + rating, (NUMBER_OF_DECADES - 1)
                     * COLUMN_WIDTH, y2); 
    }

    //sets up the graph lines, columns, and dates
    public static void setupGraph(Graphics g)
    {
        g.setColor(Color.BLACK);
        for(int i = 0; i < NUMBER_OF_DECADES; i++) //column lines and year labels
        {
            g.drawLine( (i * COLUMN_WIDTH), 0, (i * COLUMN_WIDTH), 550 );
            String year = String.valueOf( STARTING_YEAR + i * 10 );
            g.drawString( year, (i * COLUMN_WIDTH), 550 );
        }
        for(int i = 0; i < 2; i++) //horizontal lines
        {
            g.drawLine( 0,  (i * 500 + 25), PANEL_WIDTH, (i * 500 + 25) ); 
        }
    } 
    
    //finds next x-coordinate for the given line of information and returns
    public static int findY(int rating)
    {
        if(rating == 0)
        {
            return 525;
        }else if (0 < rating && rating <= 1000){ //linearly scale rating 1-1000 to Y-coord 25-524
            return (int) Math.round( (((499.0 * (rating - 1)) / (999)) + 25));
        }else{
            System.out.println("rating out of range!");
            return 525;
        }
    }
    
    //draws name, sex, rating and lines to the panel
    public static void drawData(Graphics g, int x, int y, int y2, int rating,
                                String foundName, String foundSex)
    {
        g.setColor( Color.RED );
        g.drawString( foundName + " " + foundSex + " " + rating, x, y );
        g.drawLine( x, y, x + COLUMN_WIDTH, y2 );
    }
}