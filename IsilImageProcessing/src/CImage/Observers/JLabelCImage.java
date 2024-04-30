package CImage.Observers;

import CImage.*;
import javax.swing.*;

public class JLabelCImage extends JLabel implements Observer
{
    private CImage cimage;
    
    public JLabelCImage() 
    {
        super();
        setVerticalAlignment(TOP);
        setHorizontalAlignment(LEFT);

        cimage = null;
    }
    
    public JLabelCImage(CImage ci)
    {
        super();
        setVerticalAlignment(TOP);
        setHorizontalAlignment(LEFT);

        cimage = null;
        setCImage(ci);
    }
    
    public void setCImage(CImage ci)
    {
        if (cimage != null) cimage.setObserver(null);
        cimage = ci;
        cimage.setObserver(this);
        update();
    }
    
    public void update()
    {
        setIcon(new ImageIcon(cimage.getImage()));
    }
    
    public CImage getCImage()
    {
        return cimage;
    }
}
