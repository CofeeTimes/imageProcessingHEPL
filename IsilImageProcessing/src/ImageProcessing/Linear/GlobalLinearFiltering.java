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
        MatriceComplexe F = Fourier.Fourier2D(imageDouble);
        
        // Cross Cross ... to center
        MatriceComplexe crossCrossF = Fourier.decroise(F);

        // Ideal Low Pass Filter
        int M = F.getLignes();
        int N = F.getColonnes();
        double[][] H = new double[M][N]; // H(u,v)

        for (int u = 0; u < M; u++) { 
            for (int v = 0; v < N; v++) {
//                double D = Math.sqrt(u * u + v * v); // Fonctionne pas ???
                double D = Math.sqrt((u - M / 2) * (u - M / 2) + (v - N / 2) * (v - N / 2)); // Dans la littérature ??? Sinon ne fonctionne pas
                if (D <= cutOffFrequency) { // BW filter
                    H[u][v] = 1;
                } else {
                    H[u][v] = 0;
                }
            }
        }
        
        // Apply filter (Multiply and cross cross ...)
        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                double realPart = crossCrossF.getPartieReelle()[u][v] * H[u][v];
                double imaginaryPart = crossCrossF.getPartieImaginaire()[u][v] * H[u][v];
                crossCrossF.set(u, v, realPart, imaginaryPart);
            }
        }
        MatriceComplexe unCrossCrossF = Fourier.decroise(crossCrossF);
        
        // Inverse Fourier Transform
        MatriceComplexe inverseFourier = Fourier.InverseFourier2D(unCrossCrossF);
        
        // To double to int (image)
        int[][] imageFiltered = new int[M][N];
        double[][] realPart = inverseFourier.getPartieReelle(); // Hope Imaginary part ~= 0
        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                imageFiltered[u][v] = (int) Math.round(realPart[u][v]);
            }
        }
   
        return imageFiltered;
    }
}