// https://web.pdx.edu/~jduh/courses/Archive/geog481w07/Students/Ludwig_ImageConvolution.pdf
/*
Formule cours masque 3x3 : sum(i = -1 to i = 1) sum(j = -1 to j = 1) h indice(i + 1, j + 1) * x(m + i, n + j)
-1 et 1 = +-centre masque
Formule généralisée : sum(i = -k to i = k) sum(j = -k to j = k) h indice(i + k, j + k) * x(m + i, n + j)
*/
package ImageProcessing.Linear;

import ImageProcessing.Complexe.*;
import ImageProcessing.Fourier.Fourier;

public class LocalLinearFiltering {
    
    /*
    -----------------------
    Convolution Mask Filter
    -----------------------
    */
    public static int[][] convolutionMaskFilter(int[][] image, double[][] mask) {
        // Mask
        int sizeMask = mask.length;
        if (sizeMask%2 == 0) {
//            throw new Exception("n cannot be father");
        throw new IllegalArgumentException("n cannot be father");
        }
        
        // Centering mask & image size
        int centerMask = (sizeMask - 1) / 2;
        int M = image.length; // https://www.codingrooms.com/blog/2d-array-length-java
        int N = image[0].length;
        
        int[][] imageFiltered = new int[M][N];
        
        // Scan image matrix and applying mask
        for (int m = centerMask; m < M - centerMask; m++) {
            for (int n = centerMask; n < N - centerMask; n++) {
                double sum = 0;
                
                // Applying
                for (int i = -centerMask ; i <= centerMask; i++) {
                    for (int j = -centerMask; j <= centerMask; j++) {
                        sum += mask[centerMask + i][centerMask + j] * image[m + i][n + j];
                    }
                }
                //imageFiltered[m][n] = (int )Math.round(sum);
                int value = (int) Math.round(sum);
                if (value < 0) value = 0;
                if (value > 255) value = 255;
                imageFiltered[m][n] = value;
            }
        }
        
        return imageFiltered;
    }
}
