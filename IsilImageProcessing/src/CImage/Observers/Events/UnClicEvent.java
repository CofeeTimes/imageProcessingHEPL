package CImage.Observers.Events;

import java.util.*;

public class UnClicEvent extends EventObject 
{
    private int x,y;
    
    /** Creates a new instance of SourisClicEvent */
    public UnClicEvent(Object source,int x,int y) 
    {
        super(source);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
