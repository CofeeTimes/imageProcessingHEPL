/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ImageProcessing.NonLineaire;

/**
 *
 * @author jvinc
 */
public class MorphoElementaire 
{
    public static int[][] erosion(int [][] image,int tailleMasque)
    {
        final int CENTRE = (int)(tailleMasque /2.0);
        int[][] ret = new int[image.length][image[0].length]; 
        
        for (int u = 0; u < image.length; u++) 
        {
            for (int v = 0; v < image[u].length; v++) 
            {
                int min = 257;
                for (int Mu = 0; Mu < tailleMasque; Mu++)
                {
                    for (int Mv = 0; Mv < tailleMasque; Mv++) 
                    {
                        if(min > getAtIndex(image, u + Mu - CENTRE, v+Mv - CENTRE))
                        {
                            min = getAtIndex(image, u + Mu - CENTRE, v+Mv - CENTRE);
                        }
                    }
                }
                ret[u][v] = min;
            }
        }
        return ret;
    }
    
    public static int[][] dilatation(int [][] image,int tailleMasque)
    {
        final int CENTRE = (int)(tailleMasque /2.0);
        int[][] ret = new int[image.length][image[0].length]; 
        
        for (int u = 0; u < image.length; u++) 
        {
            for (int v = 0; v < image[u].length; v++) 
            {
                int max = -1;
                for (int Mu = 0; Mu < tailleMasque; Mu++)
                {
                    for (int Mv = 0; Mv < tailleMasque; Mv++) 
                    {
                        if(max < getAtIndex(image, u + Mu - CENTRE, v+Mv - CENTRE))
                        {
                            max = getAtIndex(image, u + Mu - CENTRE, v+Mv - CENTRE);
                        }
                    }
                }
                ret[u][v] = max; 
            }

        }
        return ret; 
    }
    
    public static int[][] ouverture(int [][] image,int tailleMasque) 
    {
        return erosion(dilatation(image,tailleMasque),tailleMasque);
    }
    
    public static int[][] fermeture(int [][] image,int tailleMasque)
    {
        return dilatation(erosion(image,tailleMasque),tailleMasque);
    }
    
    public static int getAtIndex(int[][] image, int u, int v )
    {
        int sizeU = image.length;
        if(u < 0 || u >= sizeU)
        {
            return 0;
        }
        
        int sizeV = image[u].length;
        
        if(v < 0 || v >= sizeV)
        {
            return 0;
        }
        return image[u][v];
    }
}
