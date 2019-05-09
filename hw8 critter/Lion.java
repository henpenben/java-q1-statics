/* 
   Henry Hough
   6 March 2019
   CSE142AV
   TA: Madison Doerr
   Assignment #8: Critters
   
   this defines a class of critters that tend to move left-right or up-down.
   a random color is picked from a set at creation, & changed after every 3rd move thereafter.
   they will attempt to infect other species in front of them.
*/

import java.awt.*;
import java.util.*;

public class Lion extends Critter
{
    private Color[] colorArr;
    private int colorIndex;
    private int moveCounter;
    private int movesPerColor;
    
    public Lion()
    {
        colorArr = new Color[]{Color.RED, Color.GREEN, Color.BLUE};
        //picks a random index on the color array
        colorIndex = (int) Math.floor(Math.random() * colorArr.length);
        movesPerColor = 3;
    }
    
    public Action getMove(CritterInfo info)
    {
        moveCounter++;
        return actionLogic(info);
    }
    
    public Color getColor() 
    {
        if(moveCounter % movesPerColor == 0)
        {
            colorIndex = (int) Math.floor(Math.random() * colorArr.length);
        }
        return colorArr[colorIndex];
    }
    
    public String toString()
    {
        return "L";
    }
    
    //helper method for getMove
    //takes CritterInfo as input
    //decides what action to perform, then returns it
    private Action actionLogic(CritterInfo info)
    {
        if(info.getFront() == Neighbor.OTHER)
        {
            return Action.INFECT;
        }else if(info.getFront() == Neighbor.WALL || info.getRight() == Neighbor.WALL)
        {
            return Action.LEFT;
        }else if(info.getFront() == Neighbor.SAME)
        {
            return Action.RIGHT;
        }else
        {
            return Action.HOP;
        }

    }
}