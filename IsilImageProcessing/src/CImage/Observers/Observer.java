package CImage.Observers;

import CImage.*;

public interface Observer 
{
    public void   setCImage(CImage ci);
    public CImage getCImage();
    public void   update();
}
