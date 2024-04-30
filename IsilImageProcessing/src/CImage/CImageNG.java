package CImage;

import CImage.Exceptions.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class CImageNG extends CImage 
{
    /** Creates a new instance of CImage */
    public CImageNG(int la,int ha,int val) throws CImageNGException
    {
        if (la <= 0 || ha <= 0) throw new CImageNGException("Dimension de l'image incorrecte");
        verifieNG(val);
        largeur = la;
        hauteur = ha;
        image = new BufferedImage(largeur,hauteur,BufferedImage.TYPE_INT_ARGB);
        contexte = image.getGraphics();
        setBackground(val);
        setObserver(null);
    }
    
    public CImageNG(int[][] matrice) throws CImageNGException
    {
        if(matrice == null) throw new CImageNGException("Matrice de creation null");
        largeur = matrice.length;
        hauteur = matrice[0].length;
        image = new BufferedImage(largeur,hauteur,BufferedImage.TYPE_INT_ARGB);
        contexte = image.getGraphics();
        try { setMatrice(matrice); } 
        catch (CImageNGException ex) { throw new CImageNGException("Erreur interne inexpliquee..."); }
    }
    
    public CImageNG(File f) throws IOException
    {
        super();
        charge(f);
    }

    public CImageNG clone()
    {
        CImageNG cImageNG;
        try 
        { 
            cImageNG = new CImageNG(largeur, hauteur, 0);
            cImageNG.image = image; // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            cImageNG.contexte = cImageNG.image.getGraphics();
            return cImageNG;
        } 
        catch (CImageNGException ex) { System.out.println("Erreur interne inexpliquee"); }
        return null;
    }
    
    private void verifieNG(int val) throws CImageNGException
    {
        if (val < 0 || val > 255) throw new CImageNGException("Valeur du niveau de gris invalide");
    }
    
    public void setPixel(int x,int y,int val) throws CImageNGException
    {
        verifieNG(val);
        Color c = new Color(val,val,val);
        contexte.setColor(c);
        contexte.drawLine(x,y,x,y);
        if (observer != null) observer.update();
    }
    
    public int getPixel(int x,int y) throws CImageNGException
    {
        int pixels[] = new int[1];
        PixelGrabber pg = new PixelGrabber(image,x,y,1,1,pixels,0,1);
        try 
        {
            pg.grabPixels();
            ColorModel cm = pg.getColorModel();
        
            int val = cm.getRed(pixels[0]); // rouge, vert, bleu, c'est pareil !

            return val;
        } 
        catch (InterruptedException ex) 
        {
            throw new CImageNGException("Probleme avec PixelGrabber");
        }
    }
    
    public void setBackground(int val) throws CImageNGException
    {
        RemplitRect(0,0,largeur-1,hauteur-1,val);
    }
    
    public void DessineLigne(int x1,int y1,int x2,int y2,int val) throws CImageNGException
    {
        verifieNG(val);
        Color c = new Color(val,val,val);
        contexte.setColor(c);
        contexte.drawLine(x1,y1,x2,y2);
        if (observer != null) observer.update();
    }
    
    public void DessineRect(int x1,int y1,int x2,int y2,int val) throws CImageNGException
    {
        verifieNG(val);
        Color c = new Color(val,val,val);
        contexte.setColor(c);
        contexte.drawRect(x1,y1,(x2-x1),(y2-y1));
        if (observer != null) observer.update();
    }
    
    public void RemplitRect(int x1,int y1,int x2,int y2,int val) throws CImageNGException
    {
        verifieNG(val);
        Color c = new Color(val,val,val);
        contexte.setColor(c);
        contexte.fillRect(x1,y1,(x2-x1+1),(y2-y1+1));
        if (observer != null) observer.update();
    }

    public void DessineCercle(int x1,int y1,int x2,int y2,int val) throws CImageNGException
    {
        verifieNG(val);
        Color c = new Color(val,val,val);
        contexte.setColor(c);
        int rayon = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        contexte.drawOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
        if (observer != null) observer.update();
    }

    public void RemplitCercle(int x1,int y1,int x2,int y2,int val) throws CImageNGException
    {
        verifieNG(val);
        Color c = new Color(val,val,val);
        contexte.setColor(c);
        int rayon = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        contexte.fillOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
        if (observer != null) observer.update();
    }

    public int[][] getMatrice() throws CImageNGException 
    {
        // Extraction des donnees de l'image
        int pixels[] = new int[largeur*hauteur];
        PixelGrabber pg = new PixelGrabber(image,0,0,largeur,hauteur,pixels,0,largeur);
        try 
        {
            pg.grabPixels();
            ColorModel cm = pg.getColorModel();
        
            int[][] matrice = new int[largeur][hauteur];
            for(int y=0 ; y<hauteur ; y++)
                for(int x=0 ; x<largeur ; x++)
                {
                    matrice[x][y] = cm.getRed(pixels[y*largeur+x]);
                }
            return matrice;
        } 
        catch (InterruptedException ex) 
        {
            throw new CImageNGException("Probleme avec PixelGrabber");
        }
    }
    
    public void setMatrice(int[][] matrice) throws CImageNGException
    {
        if (matrice != null)
        {
            if (matrice.length != largeur || matrice[0].length != hauteur)
                throw new CImageNGException("La taille de la matrice n'est pas valide");
            
            for(int i=0 ; i<largeur ; i++)
                for(int j=0 ; j<hauteur ; j++)
                {
                    verifieNG(matrice[i][j]);
                    Color c = new Color(matrice[i][j],matrice[i][j],matrice[i][j]);
                    contexte.setColor(c);
                    contexte.drawLine(i,j,i,j);
                }
            if (observer != null) observer.update();
        }
    }
    
    public void charge(File f) throws IOException
    {
        CImageRGB cimagergb = new CImageRGB(f);
        CImageNG cimageng;
        cimageng = cimagergb.getCImageNG();
        largeur = cimageng.getLargeur();
        hauteur = cimageng.getHauteur();
        image = cimageng.getImage();
        contexte = image.getGraphics();
            
        if (getObserver() != null) getObserver().update();
    }
    
    public CImageRGB getCImageRGB()
    {
        try 
        {
            int[][] matrice = getMatrice();
            CImageRGB cImageRGB = new CImageRGB(matrice,matrice,matrice);
            return cImageRGB;
        } 
        catch (CImageNGException ex) { System.err.println("Erreur interne inexpliq�e..."); }
        catch (CImageRGBException ex) { System.err.println("Erreur interne inexpliq�e..."); }
        return null;
    }
}
