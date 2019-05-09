/* 
   Henry Hough
   28 January 2019
   CSE142AV
   TA: Madison Doerr
   Assignment #3: Café Wall illusion

   This program is designed to output parametric cafe
   wall illusions utilizing DrawingPanel.java
   
*/

import java.awt.*;

public class CafeWall {
    
    public static final int MORTAR = 2; //change this for different whitespace between rows
    
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(650, 400);
        panel.setBackground(Color.GRAY);
        Graphics g = panel.getGraphics();
        
/* e.g. draw(g, rows, columns, offset, size, x, y) */
        draw(g, 1,  4,  0,  20, 0,   0);
        draw(g, 1,  5,  0,  30, 50,  70);
        draw(g, 4,  2,  35, 35, 400, 20);
        draw(g, 8,  4,  0,  25, 10,  150);
        draw(g, 6,  3,  10, 25, 250, 200);
        draw(g, 10, 5,  10, 20, 425, 180);
    }
    //pair() places "size" sized box pair(s) at position (x, y)
    //number of pairs determined by "columns"
    public static void pair(Graphics g, int columns, int size, int x, int y) {
        for(int i = 0; i < columns; i++) {
            g.setColor(Color.BLACK);
            g.fillRect(x + (2*i*size), y, size, size);
            g.setColor(Color.BLUE);
            g.drawLine(x + (2*i*size), y, x+size + (2*i*size), y+size);
            g.drawLine(x+size + (2*i*size), y, x + (2*i*size), y+size);
            g.setColor(Color.WHITE);
            g.fillRect(x+size + (2*i*size), y, size, size);
        }
    }
    //draw() adds on to the pair() function
    //allows multiple "rows" as well as a row-alternating "offset"
    public static void draw(Graphics g, int rows, int columns, int offset, int size, int x, int y) {
        for(int i = 0; i < rows; i++) {
            for(int j = (i%2); j == 0; j++) {
                pair(g, columns, size, x, y + (i * size) + (i * MORTAR));
            } //for loops are identical except the bottom one will offset the x-value
            for(int j = (i%2); j == 1; j++) {
                pair(g, columns, size, x + offset, y + (i * size) + (i * MORTAR));
            }
        }
    }
}