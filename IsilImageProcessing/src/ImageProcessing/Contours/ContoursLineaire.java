/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ImageProcessing.Contours;

import ImageProcessing.Linear.FiltrageLineaireLocal;

/**
 *
 * @author jvinc
 */
public class ContoursLineaire 
{
    public static int[][] gradientPrewitt(int[][] image,int dir)
    {
        double[][] masque = new double [3][3];
        
        switch(dir)
        {
            case 1 :
                for (int u = 0; u < 3; u++)
                {
                    masque[u][0]= 1.0/3;
                    masque[u][1] = 0;
                    masque[u][2] = -1.0/3;
                }
                break;
            case 2:
            default :
                for (int v = 0; v < 3; v++)
                {
                    masque[0][v]= 1.0/3;
                    masque[1][v] = 0;
                    masque[2][v] = -1.0/3;
                }
                break;
        }
        int[][] ret = FiltrageLineaireLocal.filtreMasqueConvolution(image,masque);
        
        for (int u = 0; u < ret.length; u++)
        {
            for (int v = 0; v < ret[0].length; v++)
            {
                ret[u][v] = (ret[u][v]+255)/2;
            }
        }
        return ret;
    }
    public static int[][] gradientSobel(int[][] image,int dir) 
    {
        double[][] masque = new double [3][3];
        
        switch(dir)
        {
            case 1 :
                masque[0][0]= 1.0/4;
                masque[0][1] = 0;
                masque[0][2] = -1.0/4;
                masque[1][0]= 2.0/4;
                masque[1][1] = 0;
                masque[1][2] = -2.0/4;
                masque[2][0]= 1.0/4;
                masque[2][1] = 0;
                masque[2][2] = -1.0/4;
                break;
            case 2:
            default :
                masque[0][0]= 1.0/4;
                masque[1][0] = 0;
                masque[2][0] = -1.0/4;
                masque[0][1]= 2.0/4;
                masque[1][1] = 0;
                masque[2][1] = -2.0/4;
                masque[0][2]= 1.0/4;
                masque[1][2] = 0;
                masque[2][2] = -1.0/4;
                break;
        }
        int[][] ret = FiltrageLineaireLocal.filtreMasqueConvolution(image,masque);
        
        for (int u = 0; u < ret.length; u++)
        {
            for (int v = 0; v < ret[0].length; v++)
            {
                ret[u][v] = (ret[u][v]+255)/2;
            }
        }
        return ret;
    }
    public static int[][] laplacien4(int[][] image)
    {
        double[][] masque = new double [3][3];
        masque[0][0]= 0;
        masque[0][1] = 1.0/4.0;
        masque[0][2] = 0;
        masque[1][0]= 1/4.0;
        masque[1][1] = -4/4.0;
        masque[1][2] = 1/4.0;
        masque[2][0]= 0;
        masque[2][1] = 1/4.0;
        masque[2][2] = 0;

        int[][] ret = FiltrageLineaireLocal.filtreMasqueConvolution(image,masque);
        
        for (int u = 0; u < ret.length; u++)
        {
            for (int v = 0; v < ret[0].length; v++)
            {
                ret[u][v] = (ret[u][v]+255)/2;
            }
        }
        return ret;
    }
    public static int[][] laplacien8(int[][] image)
    {
        double[][] masque = new double [3][3];
        masque[0][0]= 1.0/8.0;
        masque[0][1] = 1.0/8.0;
        masque[0][2] = 1.0/8.0;
        masque[1][0]= 1/8.0;
        masque[1][1] = -8/8.0;
        masque[1][2] = 1/8.0;
        masque[2][0]= 1/8.0;
        masque[2][1] = 1/8.0;
        masque[2][2] = 1/8.0;

        int[][] ret = FiltrageLineaireLocal.filtreMasqueConvolution(image,masque);
        
        for (int u = 0; u < ret.length; u++)
        {
            for (int v = 0; v < ret[0].length; v++)
            {
                ret[u][v] = (ret[u][v]+255)/2;
            }
        }
        return ret;
    }
    
}
