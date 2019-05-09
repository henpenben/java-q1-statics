/* 
   Henry Hough
   6 March 2019
   CSE142AV
   TA: Madison Doerr
   Assignment #8: Critters
   
   this defines a class of critters that tend to move clockwise around the edge of the frame
   giants will sequentially change their display string after every 6th move to one of four options
   giants infect others in front, else hop if possible, else turn right
*/


import java.awt.*;

public class Giant extends Critter
{
    private String[] stringArr;
    private int stringIndex;
    private int moveCounter;
    private int movesPerString;
    
    public Giant()
    {
        stringArr = new String[]{"fee", "fie", "foe", "fum"};
        movesPerString = 6;
    }
    
    public Action getMove(CritterInfo info)
    {
        moveCounter++;
        return actionLogic(info);
    }
    
    public Color getColor() 
    {
        return Color.GRAY;
    }
    
    public String toString()
    {
        if(moveCounter % movesPerString == 0)
        {
            stringIndex++;
        }
        return stringArr[stringIndex % stringArr.length];
    }
    
    //helper method for getMove
    //takes CritterInfo as input
    //decides what action to perform, then returns it
    private Action actionLogic(CritterInfo info)
    {
        if(info.getFront() == Neighbor.OTHER)
        {
            return Action.INFECT;
        }else if(info.getFront() == Neighbor.EMPTY)
        {
            return Action.HOP;
        }else
        {
            return Action.RIGHT;
        }
    }
}