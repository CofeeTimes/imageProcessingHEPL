/*Filtrer : f(x,y) -> fourier -> F(u,v) -> H(u,v) -> G(u,v) -> 
fourier inverse -> y(x,y)*/
package ImageProcessing.Linear;

import ImageProcessing.Complexe.*;
import ImageProcessing.Fourier.Fourier;

public class GlobalLinearFiltering {
    
    public static int[][] idealLowPassFilter(int[][] image, int cutOffFrequency){
        // Image to double -> arg of Fourier2D
        double[][] imageDouble = new double[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) { // https://cplusplus.com/forum/beginner/188240/
            for (int j = 0; j < image[0].length; j++) {
                imageDouble[i][j] = (double) image[i][j];
            }
        }
        
        // Fourier Transform
        MatriceComplexe fourier = Fourier.Fourier2D(imageDouble);

        // Ideal Low Pass Filter
        int M = fourier.getLignes();
        int N = fourier.getColonnes();
        double[][] H = new double[M][N]; // H(u,v)

        for (int u = 0; u < M; u++) { 
            for (int v = 0; v < N; v++) {
                double D = Math.sqrt(u * u + v * v);
//                double D = Math.sqrt((u - M / 2) * (u - M / 2) + (v - N / 2) * (v - N / 2)); // Dans la littérature ???
                if (D <= cutOffFrequency) { // BW filter
                    H[u][v] = 1;
                } else {
                    H[u][v] = 0;
                }
            }
        }
        
        // Inverse Fourier Transform
        MatriceComplexe inverseFourier = Fourier.InverseFourier2D(fourier);
        int[][] imageFiltered = new int[M][N];
        double[][] realPart = inverseFourier.getPartieReelle();
        double[][] imaginaryPart = inverseFourier.getPartieImaginaire();
        return imageFiltered;
    }
}