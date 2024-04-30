package CImage;

import CImage.Observers.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public abstract class CImage 
{
    protected BufferedImage image;
    protected int largeur;
    protected int hauteur;
    
    protected Graphics contexte;
    protected Observer observer;
    
    protected CImage()
    {
        largeur = 256;
        hauteur = 256;
        image = new BufferedImage(largeur,hauteur,BufferedImage.TYPE_INT_ARGB);
        contexte = image.getGraphics();
        observer = null;
    }
    
    public void setObserver(Observer ob)
    {
        observer = ob;
    }
    
    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
    
    public void enregistreFormatPNG(File f) throws IOException
    {
        ImageIO.write(image,"png",f);
    }
    
    public void charge(File f) throws IOException
    {
        image = ImageIO.read(f);
        largeur = image.getWidth();
        hauteur = image.getHeight();
        contexte = image.getGraphics();
        if (getObserver() != null) getObserver().update();
    }

    public Graphics getContexte() {
        return contexte;
    }

    public Observer getObserver() {
        return observer;
    }
}
