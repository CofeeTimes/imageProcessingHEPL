/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ImageProcessing.Contours;

import ImageProcessing.Histogramme.Histogramme;
import ImageProcessing.NonLineaire.MorphoElementaire;

/**
 *
 * @author jvinc
 */
public class ContoursNonLineaire {
    
    public static int[][] gradientErosion(int[][] image) 
    {
        int[][] ret = MorphoElementaire.erosion(image,3);
        for (int u = 0; u < ret.length; u++) 
        {
            for (int v = 0; v < ret[0].length; v++) 
            {
                ret[u][v] = image[u][v] - ret[u][v];
            }
        }

        return ret;  
    }
    public static int[][] gradientDilatation(int[][] image)
    {
        int[][] ret = MorphoElementaire.dilatation(image,3);
        for (int u = 0; u < ret.length; u++) 
        {
            for (int v = 0; v < ret[0].length; v++) 
            {
                ret[u][v] = Math.abs(image[u][v] - ret[u][v]) ;
            }
        }

        return ret; 
    }
    
    public static int[][] gradientBeucher(int[][] image)
    {
        int[][] ero = gradientErosion(image);
        int[][] dil = gradientDilatation(image);
        int[][] ret = new int[ero.length][ero[0].length];
        
        for (int u = 0; u < ero.length; u++) 
        {
            for (int v = 0; v < ero[0].length; v++)
            {
                ret[u][v] = (ero[u][v] + dil[u][v]) /2;
            }
        }
        return ret;
    }
    public static int[][] laplacienNonLineaire(int[][] image)
    {
      int[][] ero = gradientErosion(image);
        int[][] dil = gradientDilatation(image);
        int[][] ret = new int[ero.length][ero[0].length];
        
        for (int u = 0; u < ero.length; u++) 
        {
            for (int v = 0; v < ero[0].length; v++)
            {
                ret[u][v] = ((dil[u][v] - ero[u][v]) +255)/2;
                
            }
        }
        System.out.println(Histogramme.maximum(ret)+ "-"+Histogramme.minimum(ret));
        return ret;  
    }
    
}
