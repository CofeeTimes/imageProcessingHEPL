package ImageProcessing.Histogramme;

public class Histogramme 
{
    public static int[] Histogramme256(int mat[][])
    {
        int M = mat.length;
        int N = mat[0].length;
        int histo[] = new int[256];
        
        for(int i=0 ; i<256 ; i++) histo[i] = 0;
        
        for(int i=0 ; i<M ; i++)
            for(int j=0 ; j<N ; j++)
                if ((mat[i][j] >= 0) && (mat[i][j]<=255)) histo[mat[i][j]]++;
        
        return histo;
    }
    public static int minimum(int[][] image)
    {
        int M = image.length;
        int N = image[0].length;
        int min = 256;
        
        for (int u = 0; u < M; u++) 
        {
            for (int v = 0; v < N; v++) 
            {
                if(image[u][v] < min)
                {
                    min = image[u][v];
                }
            }
        }
        return min;
    }
    public static int maximum(int[][] image)
    {
        int M = image.length;
        int N = image[0].length;
        int max = -1;
        
        for (int u = 0; u < M; u++) 
        {
            for (int v = 0; v < N; v++) 
            {
                if(image[u][v] > max)
                {
                    max = image[u][v];
                }
            }
        }
        return max;
    }
    public static int luminance(int[][] image) 
    {
        int M = image.length;
        int N = image[0].length;
        int sum = 0;
        
        for (int u = 0; u < M; u++) 
        {
            for (int v = 0; v < N; v++) 
            {
                sum += image[u][v];
            }
        }
        return sum/(M*N); 
    }
    public static double contraste1(int[][] image)
    {
        int M = image.length;
        int N = image[0].length;
        final int AVG = luminance(image);
        double contraste = 0;
        
        for (int u = 0; u < M; u++) 
        {
            for (int v = 0; v < N; v++) 
            {
                contraste += Math.pow(image[u][v]-AVG,2);
            }
        }
        return Math.sqrt(contraste/(N*M));
    }
    public static double contraste2(int[][] image)
    {
        return (maximum(image)-minimum(image))/(maximum(image)+minimum(image));
    }
    
    public static int[][] rehaussement(int[][] image, int[] courbeTonale)
    {
        int M = image.length;
        int N = image[0].length;
        int[][] ret = new int[M][N];
        
        for (int u = 0; u < M; u++) 
        {
            for (int v = 0; v < N; v++) 
            {
                ret[u][v] = courbeTonale[image[u][v]]; 
            }
        }
        return ret;
    }
    
    public static int[] creeCourbeTonaleLineaireSaturation(int smin, int smax)
    {
        int[] ret = new int[256];
        
        for (int i = 0; i < 256; i++) 
        {
            if(i < smin)
            {
                ret[i] = 0;
            }else if (i > smax)
            {
                ret[i] =255;
            }else
            {
                ret[i] = (int)(255*((double)(i-smin)/(double)(smax-smin)));
            } 
        }
        return ret;
    }
    
    public static int[] creeCourbeTonaleGamma(double gamma) 
    {
        int[] ret = new int[256];
        
        for (int i = 0; i < 256; i++) 
        {
            ret[i] = (int)(255*Math.pow(i/255.0, 1/gamma));
        }
        return ret;
    }
    public static int[] creeCourbeTonaleNegatif() 
    {
        int[] ret = new int[256];
        
        for (int i = 0; i < 256; i++) 
        {
            ret[i] = 255 - i;
        }
        return ret; 
    }
    
    public static int[] creeCourbeTonaleEgalisation(int[][] image)
    {
        int[] his = Histogramme256(image);
        double[] hisNorm = new double[256];
        
        for (int i = 0; i < 256; i++) 
        {
            
            hisNorm[i] = (double)(his[i]) / (double)(image.length * image[0].length);
        }
        System.out.println();
        double sum = 0;
        for (int i = 0; i < 256; i++) 
        {
            sum += hisNorm[i];
            his[i] = (int)(255*sum);
        }
        return his;
    }
}
