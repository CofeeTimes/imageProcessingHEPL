/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ImageProcessing.Seuillage;

import ImageProcessing.Histogramme.Histogramme;

/**
 *
 * @author jvinc
 */
public class Seuillage 
{
    public static int[][] seuillageSimple(int[][] image, int seuil)
    {
        int[][] ret = new int [image.length][image[0].length];
        for (int u = 0; u < image.length; u++)
        {
            for (int v = 0; v < image[0].length; v++)
            {
                if(image[u][v] < seuil)
                {
                    ret[u][v] = 0;
                }else
                {
                    ret[u][v] = 255;
                }
            }
         }
        return ret;
    }
    public static int[][] seuillageDouble(int[][] image,int seuil1, int seuil2) 
    {
      int low,high;
        if (seuil1 > seuil2) 
        {
            high = seuil1;
            low = seuil2;
        }else
        {
            high = seuil2;
            low = seuil1;
        }
        
        for (int u = 0; u < image.length; u++)
        {
            for (int v = 0; v < image[0].length; v++)
            {
                if(image[u][v] < low)
                {
                    image[u][v] = 0;
                }else if(image[u][v] < high)
                {
                    image[u][v] = 128;
                }else
                {
                    image[u][v] =255;
                }
            }
        } 
        return image;
    }
    
    public static int[][] seuillageAutomatique(int[][] image) 
    {
        int oldT = 0;
        int curentT = (Histogramme.maximum(image)- Histogramme.minimum(image)) /2;
        int[][] ret = new int[image.length][image[0].length];
       do
       {
          oldT = curentT;
          System.out.println(curentT);
          ret = seuillageSimple(image,curentT);
          
          int sumG1 =0;
          int sumG2 =0;
          int countG1 =0;
          int countG2 =0;
          
           for (int u = 0; u < ret.length; u++)
           {
               for (int v = 0; v < ret[0].length; v++) 
               {
                   if(ret[u][v] == 255)
                   {
                       sumG1 = sumG1 + image[u][v];
                       countG1 ++;
                   }else
                   {
                       sumG2 = sumG2 + image[u][v];
                       countG2 ++;  
                   }
                   //System.out.println(ret[u][v]);
               }
           }
           curentT = (int)((((double)sumG1/countG1)+((double)sumG2/countG2))/2);
          
       }while(oldT != curentT);
       return ret;
    }
}
