import java.awt.*;
import java.util.*;

public class Test
{
    public static void main(String[] args)
    {
    
        char[] list = new char[]{'A', 'B', 'C', 'D', 'E'};
        System.out.println( lookFor(list) );
    }
    
    public static int lookFor(char[] list)
    {
       return Arrays.binarySearch(list, 'C');
    }
}