/*Filtrer : f(x,y) -> fourier -> F(u,v) -> H(u,v) -> G(u,v) -> 
fourier inverse -> y(x,y)*/
package ImageProcessing.Linear;

import ImageProcessing.Complexe.*;
import ImageProcessing.Fourier.Fourier;

public class GlobalLinearFiltering {
    
    /*
    ----------------------------
    Global Ideal Low Pass Filter
    ----------------------------
    */
    public static int[][] idealLowPassFilter(int[][] image, int cutOffFrequency) {
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
//                double D = Math.sqrt(u * u + v * v); // Fonctionne pas ??? (Génère un cercle sur le coté gauche de l'image)
                double D = Math.sqrt((u - M / 2) * (u - M / 2) + (v - N / 2) * (v - N / 2)); // On veut un cercle au millieu de l'image
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
        
        // !!! Error: Valeur du niveau de gris invalide !!!
        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                int value = (int) Math.round(realPart[u][v]);
                if (value < 0) value = 0;
                if (value > 255) value = 255;
                imageFiltered[u][v] = value;
            }
        }
   
        return imageFiltered;
    }
    
     /*
    -----------------------------
    Global Ideal High Pass Filter
    -----------------------------
    */
    public static int[][] idealHighPassFilter(int[][] image, int cutOffFrequency) {
        // Convert image in double
        double[][] imageDouble = imageToDouble(image);
        
        // Fourier Transform and center (cross cross ...)
        MatriceComplexe F = Fourier.Fourier2D(imageDouble);
        MatriceComplexe crossCrossF = Fourier.decroise(F);
        
        // Ideal High Pass Filter
        int M = F.getLignes();
        int N = F.getColonnes();
        double[][] H = new double[M][N]; // H(u,v)

        for (int u = 0; u < M; u++) { 
            for (int v = 0; v < N; v++) {
                double D = Math.sqrt((u - M / 2) * (u - M / 2) + (v - N / 2) * (v - N / 2)); // Sinon Kaput 
                if (D >= cutOffFrequency) { // BW filter
                    H[u][v] = 1;
                } else {
                    H[u][v] = 0;
                }
            }
        }
        
        // Apply filter
        crossCrossF = applyFilter(crossCrossF, H, M, N);
        MatriceComplexe unCrossCrossF = Fourier.decroise(crossCrossF);
        
        // Inverse Fourier Transform
        MatriceComplexe inverseFourier = Fourier.InverseFourier2D(unCrossCrossF);
        
        // Image Filtered
        int[][] imageFiltered = toDoubleToInt(inverseFourier, M, N);
   
        return imageFiltered;
    }
    
    /*
    ----------------------------------
    Global Butterworth Low Pass Filter
    ----------------------------------
    */
    public static int[][] lowPassButterworthFilter(int[][] image, int cutOffFrequency, int order) {
        // Convert image in double
        double[][] imageDouble = imageToDouble(image);
        
        // Fourier Transform and center (cross cross ...)
        MatriceComplexe F = Fourier.Fourier2D(imageDouble);
        MatriceComplexe crossCrossF = Fourier.decroise(F);
        
        // Butterworth Low Pass Filter (non-ideal)
        int M = F.getLignes();
        int N = F.getColonnes();
        double[][] H = new double[M][N]; // H(u,v)

        for (int u = 0; u < M; u++) { 
            for (int v = 0; v < N; v++) {
                double D = Math.sqrt((u - M / 2) * (u - M / 2) + (v - N / 2) * (v - N / 2)); 
                H[u][v] = 1 / (1 + Math.pow(D / cutOffFrequency, 2 * order)); // https://www.javatpoint.com/power-of-a-number-in-java
            }
        }
        
        // Apply
        crossCrossF = applyFilter(crossCrossF, H, M, N);
        MatriceComplexe unCrossCrossF = Fourier.decroise(crossCrossF);
        
        // Inverse Fourier
        MatriceComplexe inverseFourier = Fourier.InverseFourier2D(unCrossCrossF);
        
        // Filtered
        int[][] imageFiltered = toDoubleToInt(inverseFourier, M, N);
   
        return imageFiltered; 
    }
    
    /*
    ----------------------------------
    Global Butterworth High Pass Filter
    ----------------------------------
    */
    public static int[][] highPassButterworthFilter(int[][] image, int cutOffFrequency, int order) {
        // Convert image in double
        double[][] imageDouble = imageToDouble(image);
        
        // Fourier Transform and center (cross cross ...)
        MatriceComplexe F = Fourier.Fourier2D(imageDouble);
        MatriceComplexe crossCrossF = Fourier.decroise(F);
        
        // Butterworth Low Pass Filter (non-ideal)
        int M = F.getLignes();
        int N = F.getColonnes();
        double[][] H = new double[M][N]; // H(u,v)

        for (int u = 0; u < M; u++) { 
            for (int v = 0; v < N; v++) {
                double D = Math.sqrt((u - M / 2) * (u - M / 2) + (v - N / 2) * (v - N / 2)); 
                H[u][v] = 1 / (1 + Math.pow(cutOffFrequency / D, 2 * order)); // https://www.javatpoint.com/power-of-a-number-in-java
            }
        }
        
        // Apply
        crossCrossF = applyFilter(crossCrossF, H, M, N);
        MatriceComplexe unCrossCrossF = Fourier.decroise(crossCrossF);
        
        // Inverse Fourier
        MatriceComplexe inverseFourier = Fourier.InverseFourier2D(unCrossCrossF);
        
        // Filtered
        int[][] imageFiltered = toDoubleToInt(inverseFourier, M, N);
   
        return imageFiltered; 
    }
    
    /*
    ---------------
    Image To Double
    ---------------
    */
    static double[][] imageToDouble(int[][] image) {
        double[][] imageDouble = new double[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) { // https://cplusplus.com/forum/beginner/188240/
            for (int j = 0; j < image[0].length; j++) {
                imageDouble[i][j] = (double) image[i][j];
            }
        }
        return imageDouble;
    }
        
    /*
    ------------
    Apply Filter
    ------------
    */
    static MatriceComplexe applyFilter(MatriceComplexe crossCrossF, double[][] H, int M, int N) {
//        int M = H.length; // https://www.codingrooms.com/blog/2d-array-length-java
//        int N = H[0].length;
        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                double realPart = crossCrossF.getPartieReelle()[u][v] * H[u][v];
                double imaginaryPart = crossCrossF.getPartieImaginaire()[u][v] * H[u][v];
                crossCrossF.set(u, v, realPart, imaginaryPart);
            }
        }
        return crossCrossF;
    }
    
    /*
    ----------------
    To Double To int
    ----------------
    */
    static int[][] toDoubleToInt(MatriceComplexe inverseFourier, int M, int N) {
        int[][] imageFiltered = new int[M][N];
        double[][] realPart = inverseFourier.getPartieReelle(); // Hope Imaginary part ~= 0

        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                int value = (int) Math.round(realPart[u][v]);

                // Value between 0 and 255 !!!
                if (value < 0) value = 0;
                if (value > 255) value = 255;

                // Correct
                imageFiltered[u][v] = value;
            }
        }
        return imageFiltered;
    }
}