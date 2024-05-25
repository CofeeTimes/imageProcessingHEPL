/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ImageProcessing.NonLineaire;

import static ImageProcessing.NonLineaire.MorphoElementaire.dilatation;
import static ImageProcessing.NonLineaire.MorphoElementaire.getAtIndex;

/**
 *
 * @author jvinc
 */
public class MorphoComplexe {
    
    public static int[][] dilatationGeodesique(int[][] image,int[][] masqueGeodesique, int nbIter,int tail)
    {
        int[][] ret = image;
        for (int i = 0; i < nbIter; i++)
        {
            ret = dilatation(ret,tail);
            
            for (int u = 0; u < ret.length; u++) 
            {
                for (int v = 0; v < ret[u].length; v++) 
                {
                   if(masqueGeodesique[u][v] == 0)
                   {
                       ret[u][v] = 0;
                   } 
                }
  
            }
        }
        return ret;
    }
    
    public static int[][] reconstructionGeodesique(int[][] image, int[][]masqueGeodesique,int tail) 
    {
        int[][] curentOrder = image;
        int[][] oldOrder  = image;
        int dif;
        do
        {
          
            curentOrder = dilatation(curentOrder,tail);
            
            for (int u = 0; u < curentOrder.length; u++) 
            {
                for (int v = 0; v < curentOrder[u].length; v++) 
                {
                   if(masqueGeodesique[u][v] == 0)
                   {
                       curentOrder[u][v] = 0;
                   } 
                }
  
            }
            
            dif =0;
            for (int u = 0; u < curentOrder.length; u++) 
            {
                for (int v = 0; v < curentOrder[u].length; v++) 
                {
                   dif += Math.abs(curentOrder[u][v]-oldOrder[u][v]); 
                }
            }
            oldOrder = curentOrder;
        
        }while(dif != 0);
        return curentOrder;
    }
    
    public static int[][] filtreMedian(int[][] image, int tailleMasque)
    {
        final int CENTRE = (int)(tailleMasque /2.0);
        int[][] ret = new int[image.length][image[0].length]; 
        
        for (int u = 0; u < image.length; u++) 
        {
            for (int v = 0; v < image[u].length; v++) 
            {
                int[] list = new int[tailleMasque*tailleMasque];
                int count =0;
                for (int Mu = 0; Mu < tailleMasque; Mu++)
                {
                    for (int Mv = 0; Mv < tailleMasque; Mv++) 
                    {
                        list[count] = getAtIndex(image, u + Mu - CENTRE, v+Mv - CENTRE);
                        count ++;
                    }
                }
                ret[u][v] = findMedain(list);
            }
        }
        return ret;
    }
    public static int findMedain(int[] list)
    {
        //met en ordre la liste
        int n = list.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list[j] > list[j + 1]) {
                    int temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
                 
        
        switch(list.length%2)
        {
            case 0:
               return (int) (0.5*(list[(int)(list.length/2)]+list[(int)((list.length/2)-1)]));
            case 1:
            default :
                return list[(int)list.length/2];  
        }
    }
}
