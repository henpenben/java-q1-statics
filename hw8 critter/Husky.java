/* 
   Henry Hough
   6 March 2019
   CSE142AV
   TA: Madison Doerr
   Assignment #8: Critters
   
   this class defines critters that have the following behavior:
   
   1) on creation, immediately rotate to west, as efficiently as possible
   2) migrate to west wall, north side
   3) form solid line on west wall & face eastward
   4) if not enough to finish line, bottom will alternate between looking south and east
   5) if one enters in the middle of the line, it should move south until there is an open spot
   6) if no open spot, begin a second line at the south end of the first line
   7) every 400 steps, enter wave attack state
   7.5) if as west as possible, flip to face east (but do not move yet)
   
   meanwhile:
   -always infect any enemy in front (change to 'A' and yellow)
   -always hop when enemy threat approaches from side and front is clear
   -always turn towards empty and hop next move when threat imminent and front is not clear
   -change displayed string and color based on current behavior
*/ 
import java.awt.*;
import java.util.*;

public class Husky extends Critter
{
    private int moveCounter;
    private boolean groupAttacking;
    private String[] stringDisplayArr;
    private int stringIndex;
    private Color[] colorArr;
    private boolean attackNext;
    private boolean inFormation;
    
    public Husky()
    {
        //moving, defending, enemy threat detected, attacking
        stringDisplayArr = new String[]{"M", "D", "T", "A"}; 
        colorArr = new Color[]{Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};
    }

    public Action getMove(CritterInfo info)
    {
        moveCounter++;
        if(moveCounter > 400)
        {
            groupAttacking = !groupAttacking;
            moveCounter = 0;
        }
        
        formationAppearance();
        return actionLogic(info);
    }

    public Color getColor()
    {
        //searches stringDisplayArr for the index of the currently displayed string
        //finds & returns the color at the index from above
        return colorArr[stringIndex];
    }

    public String toString()
    {
        return stringDisplayArr[stringIndex];
    }
    
    //helper method for getMove
    //takes CritterInfo as input
    //decides what action to perform, then returns it
    //also changes stringIndex based on what action is happening
    private Action actionLogic(CritterInfo info)
    {
        if(info.getFront() == Neighbor.OTHER)
        {
            stringIndex = 3;
            return Action.INFECT;
        }
        else if(attackNext)
        {
            attackNext = false;
            stringIndex = 3;
            return Action.INFECT;
        }else
        {   //threat handling
            if(info.rightThreat() || info.leftThreat() || info.frontThreat() || info.backThreat())
            {
                stringIndex = 2;
                return threatLogic(info);
            }
        }
        
        if(groupAttacking)
        {
            return waveAttack(info);
        }
        
        return moveLogic(info);
    }
    
    //helper method for actionLogic
    //takes CritterInfo as input
    //determines how to move
    //returns decided movement Action
    private Action moveLogic(CritterInfo info)
    {      
        inFormation = false;
        
        if(info.getDirection() == Direction.NORTH)
        {
            if(info.getRight() == Neighbor.WALL && info.getFront() == Neighbor.EMPTY)
            {
                return Action.HOP;
            }
            else if(info.getRight() != Neighbor.WALL && info.getRight() != Neighbor.SAME)
            {
                return Action.RIGHT;
            }
            else if(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
            {
                return Action.LEFT;
            }
            else if(info.getRight() == Neighbor.SAME && info.getBack() == Neighbor.SAME)
            {
                return Action.HOP;
            }
            else if(info.getRight() == Neighbor.SAME && info.getLeft() == Neighbor.EMPTY)
            {
                inFormation = true;
                return Action.LEFT;
            }
        }
        else if(info.getDirection() == Direction.EAST)
        {
            if(info.getFront() == Neighbor.EMPTY)
            {
                return Action.HOP;
            }
            else if(info.getFront() == Neighbor.SAME)
            {
                return Action.RIGHT;
            }
            else if(info.getFront() == Neighbor.WALL)
            {
                return Action.LEFT;
            }
        }
        else if(info.getDirection() == Direction.SOUTH)
        {
            if(info.getBack() == Neighbor.SAME && info.getLeft() == Neighbor.WALL 
               && info.getFront() != Neighbor.EMPTY)
            {
                inFormation = true;
                return Action.RIGHT;
            }
            else if(info.getLeft() != Neighbor.SAME)
            {
                return Action.LEFT;
            }
            else if(info.getFront() == Neighbor.EMPTY && info.getLeft() != Neighbor.EMPTY)
            {
                return Action.HOP;
            }
            else if(info.getFront() == Neighbor.SAME && info.getLeft() == Neighbor.SAME)
            {
                return Action.RIGHT;
            }
            else if(info.getFront() == Neighbor.WALL)
            {
                return Action.RIGHT;
            }
        }
        else if(info.getDirection() == Direction.WEST)
        {
            if(info.getRight() == Neighbor.WALL && info.getLeft() == Neighbor.SAME 
               && (info.getBack() == Neighbor.WALL || info.getBack() == Neighbor.SAME))
            {
                inFormation = true;
                return Action.INFECT;
            }
            else if(info.getRight() != Neighbor.SAME && info.getBack() == Neighbor.SAME 
                    && info.getLeft() == Neighbor.SAME)
            {
                inFormation = true;
                return Action.RIGHT;
            }
            else if(info.getRight() == Neighbor.SAME && info.getLeft() == Neighbor.EMPTY)
            {
                inFormation = true;
                return Action.LEFT;
            }
            else if(info.getRight() != Neighbor.SAME)
            {
                return Action.RIGHT;
            }
            else if(info.getRight() == Neighbor.SAME && info.getLeft() == Neighbor.SAME)
            {
                inFormation = true;
                return Action.INFECT;
            }
        }
        
        return Action.INFECT;
    }
    
    //helper method for actionLogic
    //takes CritterInfo as input
    //decides what to do in case of a threat
    //returns Action
    private Action threatLogic(CritterInfo info)
    {
        inFormation = false;
        if(info.getFront() == Neighbor.EMPTY)
        {
            return Action.HOP;
        }
        else if(info.rightThreat())
        {
            attackNext = true;
            return Action.RIGHT;
        }
        else if(info.leftThreat())
        {
            attackNext = true;
            return Action.LEFT;
        }
        else if(info.backThreat())
        {
            if(info.getRight() == Neighbor.EMPTY)
            {
                return Action.RIGHT;
            }
            else if(info.getLeft() == Neighbor.EMPTY)
            {
                return Action.LEFT;
            }
        }
        return moveLogic(info);
    }
    
    //bonus method for actionLogic
    //takes CritterInfo as input
    //returns movement action
    //causes built up huskies to wave-sweep the screen every so often
    private Action waveAttack(CritterInfo info)
    {
        inFormation = false;
        stringIndex = 3;
        
        if(info.getDirection() == Direction.NORTH)
        {
            if(info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL)
            {
                return Action.RIGHT;
            }
        }
        else if(info.getDirection() == Direction.EAST)
        {
            if(info.getBack() == Neighbor.EMPTY)
            {
                return Action.RIGHT;
            }
            else if(info.getBack() == Neighbor.SAME)
            {
                return Action.INFECT;
            }
        }
        else if(info.getDirection() == Direction.SOUTH)
        {
            if(info.getRight() == Neighbor.EMPTY)
            {
                return Action.RIGHT;
            }
            else if(info.getRight() != Neighbor.EMPTY)
            {
                return Action.LEFT;
            }
        }
        else if(info.getDirection() == Direction.WEST)
        {
            if(info.getFront() == Neighbor.EMPTY)
            {
                return Action.HOP;
            }
            else if(info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
            {
                return Action.RIGHT;
            }
        }
        
        return Action.HOP;
    }
    
    //very simple method with no input or return
    //simply changes color to green and appearance to 'D' when inFormation is true
    private void formationAppearance()
    {
        if(inFormation)
        {
            stringIndex = 1;
        }
        else
        {
            stringIndex = 0;
        }
    }
}
