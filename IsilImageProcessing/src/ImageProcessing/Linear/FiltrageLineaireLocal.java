/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ImageProcessing.Linear;

/**
 *
 * @author jvinc
 */
public class FiltrageLineaireLocal 
{
   public static int[][] filtreMasqueConvolution(int[][] image, double [][] masque)
   {
       
       
      int[][] ret = new int[image.length][image[0].length];
      int centre = (int)(masque.length/2.0); 
      
       for (int u = 0; u < image.length; u++) 
       {
           for (int v = 0; v < image[u].length; v++) 
           {
               //Application du filtre sur chaque pixels de l'images original
               double sum =0;
               for (int mU = 0; mU < masque.length; mU++) 
               {
                   for (int mV = 0; mV < masque[mU].length; mV++) 
                   {
                       sum += masque[mU][mV] * getAtIndex(image,u+mU-centre,v+mV-centre);
                   }
                   
               }
//                ret[u][v] = (int)sum;
                int value = (int) Math.round(sum);
                if (value < 0) value = 0;
                if (value > 255) value = 255;
                ret[u][v] = value;
           }
       }
       
       return ret;
   } 
   
   public static int[][] filtreMoyenneur(int[][] image, int tailleMasque) 
   {
       double [][] masque = new double [tailleMasque][tailleMasque];
       final double MOYENNE = 1.0/(tailleMasque*tailleMasque);
       for (int u = 0; u < tailleMasque; u++) 
       {
           for (int v = 0; v < tailleMasque; v++) 
           {
                masque[u][v] = MOYENNE;
                
           }
       }
       return filtreMasqueConvolution(image,masque);
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
