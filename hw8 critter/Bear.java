/* 
   Henry Hough
   6 March 2019
   CSE142AV
   TA: Madison Doerr
   Assignment #8: Critters
   
   this defines a class of critters that tend to move counterclockwise around the
   edge of the frame. bears alternate between "/" and "\" and are randomly chosen to be
   white or black. they will attempt to infect other species in front of them.
*/


import java.awt.*;

public class Bear extends Critter
{
    private boolean isPolarBear;
    private boolean bSlash;
    
    public Bear(boolean polar)
    {
        isPolarBear = polar;
    }

    public Action getMove(CritterInfo info)
    {
        bSlash = !bSlash;
        
        return actionLogic(info);
        
    }
    
    public Color getColor() 
    {
        if(isPolarBear)
        {
            return Color.BLACK;
        }else
        {
            return Color.WHITE;
        }
    }
    
    public String toString()
    {
        if(bSlash)
        {
            return "\\";
        }else
        {
            return "/";
        }
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
            return Action.LEFT;
        }
    }

}