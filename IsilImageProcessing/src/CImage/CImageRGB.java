package CImage;

import CImage.Exceptions.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class CImageRGB extends CImage 
{
    /** Cree une nouvelle instance de CImageRGB en specifiant la couleur de fond.
     * Tous les pixels de l'image sont initialises avec la couleur c.
     * @param la largeur de l'image (la>0)
     * @param ha hauteur de l'image (ha>0)
     * @param c couleur d'initialisation de l'image
     * @exception CImageRGBException Parametres d'initialisation invalides 
    */
    public CImageRGB(int la,int ha,Color c) throws CImageRGBException
    {
        if (la <= 0 || ha <= 0) throw new CImageRGBException("Dimension de l'image invalide");
        verifieColor(c);
        largeur = la;
        hauteur = ha;
        image = new BufferedImage(largeur,hauteur,BufferedImage.TYPE_INT_ARGB);
        contexte = image.getGraphics();
        setObserver(null);
        setBackground(c);
    }

    /** Cree une nouvelle instance de CImageRGB en specifiant la couleur de fond.
     * Tous les pixels de l'image sont initialises avec la couleur (r,g,b).
     * @param la largeur de l'image (la>0)
     * @param ha hauteur de l'image (ha>0)
     * @param r composante rouge [0,255] de la couleur d'initialisation de l'image
     * @param g composante verte [0,255] de la couleur d'initialisation de l'image
     * @param b composante bleue [0,255] de la couleur d'initialisation de l'image
     * @exception CImageRGBException Parametres d'initialisation invalides 
    */
    public CImageRGB(int la,int ha,int r,int g,int b) throws CImageRGBException
    {
        if (la <= 0 || ha <= 0) throw new CImageRGBException("Dimension de l'image invalide");
        verifieColor(r,g,b);
        Color c = new Color(r,g,b);
        largeur = la;
        hauteur = ha;
        image = new BufferedImage(largeur,hauteur,BufferedImage.TYPE_INT_ARGB);
        contexte = image.getGraphics();
        setObserver(null);
        setBackground(c);
    }

    /** Cree une nouvelle instance de CImageRGB en specifiant la couleur de fond.
     * Tous les pixels de l'image sont initialises avec la couleur (r,g,b).
     * @param la largeur de l'image (la>0)
     * @param ha hauteur de l'image (ha>0)
     * @param r composante rouge [0.0,1.0] de la couleur d'initialisation de l'image
     * @param g composante verte [0.0,1.0] de la couleur d'initialisation de l'image
     * @param b composante bleue [0.0,1.0] de la couleur d'initialisation de l'image
     * @exception CImageRGBException Parametres d'initialisation invalides 
    */
    public CImageRGB(int la,int ha,double r,double g,double b) throws CImageRGBException
    {
        if (la <= 0 || ha <= 0) throw new CImageRGBException("Dimension de l'image invalide");
        verifieColor(r,g,b);
        Color c = new Color((float)r,(float)g,(float)b);
        largeur = la;
        hauteur = ha;
        image = new BufferedImage(largeur,hauteur,BufferedImage.TYPE_INT_ARGB);
        contexte = image.getGraphics();
        setObserver(null);
        setBackground(c);
    }

    /** Cree une nouvelle instance de CImageRGB a partir des 3 matrices de composantes rouge, verte et bleue.
     * Les 3 matrices doivent avoir la meme dimension et contenir des valeurs entieres comprises dans l'intervalle [0,255].
     * C'est la dimension de ces matrices qui definit la dimension de la future image.
     * @param red matrice d'initialisation de la composante rouge de l'image
     * @param green matrice d'initialisation de la composante verte de l'image
     * @param blue matrice d'initialisation de la composante bleue de l'image
     * @exception CImageRGBException si les 3 matrices n'ont pas la meme dimension et/ou contiennent une/des valeur(s) hors de l'intervalle [0,255]. 
    */
    public CImageRGB(int[][] red,int[][] green,int[][] blue) throws CImageRGBException
    {
        // Verification des parametres
        if(red == null || green == null || blue == null) 
            throw new CImageRGBException("Matrice(s) de creation invalide(s)");
        if(red.length != green.length || red.length != blue.length || blue.length != green.length)
            throw new CImageRGBException("Les matrices de creation n'ont pas la meme dimension");
        if(red[0].length != green[0].length || red[0].length != blue[0].length || blue[0].length != green[0].length)
            throw new CImageRGBException("Les matrices de creation n'ont pas la meme dimension");
        
        // Construction de l'image
        largeur = red.length;
        hauteur = red[0].length;
        image = new BufferedImage(largeur,hauteur,BufferedImage.TYPE_INT_ARGB);
        contexte = image.getGraphics();
        setObserver(null);
        setMatricesRGB(red,green,blue);
    }

    
    public CImageRGB(File f) throws IOException
    {
        super();
        charge(f);
    }
    
    private void verifieColor(Color c) throws CImageRGBException
    {
        if (c == null) throw new CImageRGBException("Couleur invalide");
    }
    
    private void verifieColor(int r,int g,int b) throws CImageRGBException
    {
        if (r<0 || r>255) throw new CImageRGBException("Composante rouge invalide");
        if (g<0 || g>255) throw new CImageRGBException("Composante verte invalide");
        if (b<0 || b>255) throw new CImageRGBException("Composante bleue invalide");
    }

    private void verifieColor(double r,double g,double b) throws CImageRGBException
    {
        if (r<0.0 || r>1.0) throw new CImageRGBException("Composante rouge invalide");
        if (g<0.0 || g>1.0) throw new CImageRGBException("Composante verte invalide");
        if (b<0.0 || b>1.0) throw new CImageRGBException("Composante bleue invalide");
    }

    public void setPixel(int x,int y,Color c) throws CImageRGBException 
    {
        verifieColor(c);
        contexte.setColor(c);
        contexte.drawLine(x,y,x,y);
        if (observer != null) observer.update();
    }

    public void setPixel(int x,int y,int r,int g,int b) throws CImageRGBException 
    {
        verifieColor(r,g,b);
        Color c = new Color(r,g,b);
        contexte.setColor(c);
        contexte.drawLine(x,y,x,y);
        if (observer != null) observer.update();
    }

    public void setPixel(int x,int y,double r,double g,double b) throws CImageRGBException 
    {
        verifieColor(r,g,b);
        Color c = new Color((float)r,(float)g,(float)b);
        contexte.setColor(c);
        contexte.drawLine(x,y,x,y);
        if (observer != null) observer.update();
    }
    public Color getPixel(int x,int y) throws CImageRGBException
    {
        int pixels[] = new int[1];
        PixelGrabber pg = new PixelGrabber(image,x,y,1,1,pixels,0,1);
        try 
        {
            pg.grabPixels();
            ColorModel cm = pg.getColorModel();
        
            int r = cm.getRed(pixels[0]);
            int g = cm.getGreen(pixels[0]);
            int b = cm.getBlue(pixels[0]);

            return new Color(r,g,b);
        } 
        catch (InterruptedException ex) 
        {
            throw new CImageRGBException("Probleme avec PixelGrabber");
        }
    }
    
    public void setBackground(Color c) throws CImageRGBException
    {
        verifieColor(c);
        RemplitRect(0,0,largeur-1,hauteur-1,c);
    }

    public void setBackground(int r,int g,int b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color(r,g,b);
        RemplitRect(0,0,largeur-1,hauteur-1,c);
    }

    public void setBackground(double r,double g,double b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color((float)r,(float)g,(float)b);
        RemplitRect(0,0,largeur-1,hauteur-1,c);
    }
    public void DessineLigne(int x1,int y1,int x2,int y2,Color c) throws CImageRGBException
    {
        verifieColor(c);
        contexte.setColor(c);
        contexte.drawLine(x1,y1,x2,y2);
        if (observer != null) observer.update();
    }

    public void DessineLigne(int x1,int y1,int x2,int y2,int r,int g,int b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color(r,g,b);
        contexte.setColor(c);
        contexte.drawLine(x1,y1,x2,y2);
        if (observer != null) observer.update();
    }

    public void DessineLigne(int x1,int y1,int x2,int y2,double r,double g,double b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color((float)r,(float)g,(float)b);
        contexte.setColor(c);
        contexte.drawLine(x1,y1,x2,y2);
        if (observer != null) observer.update();
    }
    
    public void DessineRect(int x1,int y1,int x2,int y2,Color c) throws CImageRGBException
    {
        verifieColor(c);
        contexte.setColor(c);
        contexte.drawRect(x1,y1,(x2-x1),(y2-y1));
        if (observer != null) observer.update();
    }

    public void DessineRect(int x1,int y1,int x2,int y2,int r,int g,int b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color(r,g,b);
        contexte.setColor(c);
        contexte.drawRect(x1,y1,(x2-x1),(y2-y1));
        if (observer != null) observer.update();
    }

    public void DessineRect(int x1,int y1,int x2,int y2,double r,double g,double b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color((float)r,(float)g,(float)b);
        contexte.setColor(c);
        contexte.drawRect(x1,y1,(x2-x1),(y2-y1));
        if (observer != null) observer.update();
    }

    public void RemplitRect(int x1,int y1,int x2,int y2,Color c) throws CImageRGBException
    {
        verifieColor(c);
        contexte.setColor(c);
        contexte.fillRect(x1,y1,(x2-x1+1),(y2-y1+1));
        if (observer != null) observer.update();
    }

    public void RemplitRect(int x1,int y1,int x2,int y2,int r,int g,int b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color(r,g,b);
        contexte.setColor(c);
        contexte.fillRect(x1,y1,(x2-x1+1),(y2-y1+1));
        if (observer != null) observer.update();
    }

    public void RemplitRect(int x1,int y1,int x2,int y2,double r,double g,double b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color((float)r,(float)g,(float)b);
        contexte.setColor(c);
        contexte.fillRect(x1,y1,(x2-x1+1),(y2-y1+1));
        if (observer != null) observer.update();
    }

    public void DessineCercle(int x1,int y1,int x2,int y2,Color c) throws CImageRGBException
    {
        verifieColor(c);
        contexte.setColor(c);
        int rayon = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        contexte.drawOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
        if (observer != null) observer.update();
    }

    public void DessineCercle(int x1,int y1,int x2,int y2,int r,int g,int b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color(r,g,b);
        contexte.setColor(c);
        int rayon = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        contexte.drawOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
        if (observer != null) observer.update();
    }

    public void DessineCercle(int x1,int y1,int x2,int y2,double r,double g,double b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color((float)r,(float)g,(float)b);
        contexte.setColor(c);
        int rayon = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        contexte.drawOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
        if (observer != null) observer.update();
    }

    public void RemplitCercle(int x1,int y1,int x2,int y2,Color c) throws CImageRGBException
    {
        verifieColor(c);
        contexte.setColor(c);
        int rayon = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        contexte.fillOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
        if (observer != null) observer.update();
    }

    public void RemplitCercle(int x1,int y1,int x2,int y2,int r,int g,int b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color(r,g,b);
        contexte.setColor(c);
        int rayon = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        contexte.fillOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
        if (observer != null) observer.update();
    }

    public void RemplitCercle(int x1,int y1,int x2,int y2,double r,double g,double b) throws CImageRGBException
    {
        verifieColor(r,g,b);
        Color c = new Color((float)r,(float)g,(float)b);
        contexte.setColor(c);
        int rayon = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        contexte.fillOval(x1-rayon,y1-rayon,2*rayon,2*rayon);
        if (observer != null) observer.update();
    }

    public void getMatricesRGB(int[][] red,int[][] green,int[][] blue) throws CImageRGBException
    {
        // Verification des param�tres
        if (red != null)
        {
            if (red.length != largeur || red[0].length != hauteur)
                throw new CImageRGBException("La taille de la matrice Red n'est pas valide");
        }
        if (green != null)
        {
            if (green.length != largeur || green[0].length != hauteur)
                throw new CImageRGBException("La taille de la matrice Green n'est pas valide");
        }
        if (blue != null)
        {
            if (blue.length != largeur || blue[0].length != hauteur)
                throw new CImageRGBException("La taille de la matrice Blue n'est pas valide");
        }
        
        // Extraction des donnees de l'image
        int pixels[] = new int[largeur*hauteur];
        PixelGrabber pg = new PixelGrabber(image,0,0,largeur,hauteur,pixels,0,largeur);
        try 
        {
            pg.grabPixels();
            ColorModel cm = pg.getColorModel();
        
            for(int y=0 ; y<hauteur ; y++)
                for(int x=0 ; x<largeur ; x++)
                {
                    if (red != null) red[x][y] = cm.getRed(pixels[y*largeur+x]);
                    if (green != null) green[x][y] = cm.getGreen(pixels[y*largeur+x]);
                    if (blue != null) blue[x][y] = cm.getBlue(pixels[y*largeur+x]);
                }
        } 
        catch (InterruptedException ex) 
        {
            throw new CImageRGBException("Probleme avec PixelGrabber");
        }
    }
    
    public void setMatricesRGB(int[][] red,int[][] green,int[][] blue) throws CImageRGBException
    {
        // Verification des param�tres
        if (red != null)
        {
            if (red.length != largeur || red[0].length != hauteur)
                throw new CImageRGBException("La taille de la matrice Red n'est pas valide");
        }
        if (green != null)
        {
            if (green.length != largeur || green[0].length != hauteur)
                throw new CImageRGBException("La taille de la matrice Green n'est pas valide");
        }
        if (blue != null)
        {
            if (blue.length != largeur || blue[0].length != hauteur)
                throw new CImageRGBException("La taille de la matrice Blue n'est pas valide");
        }

        // Matrices null ?
        if (red == null && green == null && blue == null) return;
        if (red == null || green == null || blue == null)
        {
            int[][] redOld = new int[largeur][hauteur];
            int[][] greenOld = new int[largeur][hauteur];
            int[][] blueOld = new int[largeur][hauteur];
            getMatricesRGB(redOld,greenOld,blueOld);
            if (red == null) red = redOld;
            if (green == null) green = greenOld;
            if (blue == null) blue = blueOld;
        }
        
        // Mise � jour de l'image
        for(int i=0 ; i<largeur ; i++)
            for(int j=0 ; j<hauteur ; j++)
            {
                if (red[i][j] < 0 || red[i][j] > 255)
                    throw new CImageRGBException("Valeur(s) invalide(s) dans matrices RGB");
                if (green[i][j] < 0 || green[i][j] > 255)
                    throw new CImageRGBException("Valeur(s) invalide(s) dans matrices RGB");
                if (blue[i][j] < 0 || blue[i][j] > 255)
                    throw new CImageRGBException("Valeur(s) invalide(s) dans matrices RGB");
                Color c = new Color(red[i][j],green[i][j],blue[i][j]);
                setPixel(i,j,c);
            }
        if (observer != null) observer.update();
    }
    
    public CImageNG getCImageNG()
    {
        int[][] red = new int[largeur][hauteur];
        int[][] green = new int[largeur][hauteur];
        int[][] blue = new int[largeur][hauteur];
        try { getMatricesRGB(red,green,blue); } 
        catch (CImageRGBException ex) { System.out.println("Erreur interne inexpliquee..."); }
        int[][] matrice = new int[largeur][hauteur];
        for(int x=0 ; x<largeur ; x++)
            for(int y=0 ; y<hauteur ; y++)
            {
                int val = (int)(((double)(red[x][y] + green[x][y] + blue[x][y]))/3.0);
                matrice[x][y] = val;
            }
        try { return new CImageNG(matrice); } 
        catch (CImageNGException ex) { System.out.println("Erreur interne inexpliquee..."); }
        return null;
    }
}
