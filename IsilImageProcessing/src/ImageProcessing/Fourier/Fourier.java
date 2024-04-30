package ImageProcessing.Fourier;

import ImageProcessing.Complexe.*;

public class Fourier 
{
    public static MatriceComplexe Fourier2D(double f[][])
    {
        int M = f.length;
        int N = f[0].length;
        MatriceComplexe F = new MatriceComplexe(M,N);
        
        //********** n --> v **********
        double pr1[][] = new double[M][N];
        double pi1[][] = new double[M][N];
        
        for(int m=0 ; m<M ; m++)
        {
            for(int v=0 ; v<=N/2 ; v++)
            {
                pr1[m][v] = 0.0;
                pi1[m][v] = 0.0;
                double cosTheta = 1.0, newCosTheta;
                double sinTheta = 0.0, newSinTheta;
                double cosDTheta = Math.cos(-2.0*Math.PI*(double)v/(double)N);
                double sinDTheta = Math.sin(-2.0*Math.PI*(double)v/(double)N);
                for (int n=0 ; n<N ; n++)
                {
                    pr1[m][v] += f[m][n] * cosTheta;
                    pi1[m][v] += f[m][n] * sinTheta;
                    newCosTheta = cosTheta*cosDTheta - sinTheta*sinDTheta;
                    newSinTheta = sinTheta*cosDTheta + cosTheta*sinDTheta;
                    cosTheta = newCosTheta;
                    sinTheta = newSinTheta;
                }
                pr1[m][v] /= (double)N;
                pi1[m][v] /= (double)N;
                if (v > 0)
                {
                    pr1[m][N-v] = pr1[m][v];
                    pi1[m][N-v] = -pi1[m][v];
                }
            }
        }
        
        //********** m --> u **********
        double pr2[][] = new double[M][N];
        double pi2[][] = new double[M][N];
        
        for(int v=0 ; v<N ; v++)
        {
            for(int u=0 ; u<M ; u++)
            {
                pr2[u][v] = 0.0;
                pi2[u][v] = 0.0;
                double cosTheta = 1.0, newCosTheta;
                double sinTheta = 0.0, newSinTheta;
                double cosDTheta = Math.cos(-2.0*Math.PI*(double)u/(double)M);
                double sinDTheta = Math.sin(-2.0*Math.PI*(double)u/(double)M);
                for (int m=0 ; m<M ; m++)
                {
                    pr2[u][v] += pr1[m][v] * cosTheta - pi1[m][v] * sinTheta;
                    pi2[u][v] += pr1[m][v] * sinTheta + pi1[m][v] * cosTheta;
                    newCosTheta = cosTheta*cosDTheta - sinTheta*sinDTheta;
                    newSinTheta = sinTheta*cosDTheta + cosTheta*sinDTheta;
                    cosTheta = newCosTheta;
                    sinTheta = newSinTheta;
                }
                pr2[u][v] /= (double)M;
                pi2[u][v] /= (double)M;
            }
        }
   
        //********** Remplissage de F **********
        for(int u=0 ; u<M ; u++)
            for(int v=0 ; v<N ; v++)
                F.set(u,v,pr2[u][v],pi2[u][v]);
        
        return F;
    }

    public static MatriceComplexe InverseFourier2D(MatriceComplexe F)
    {
        int M = F.getLignes();
        int N = F.getColonnes();
        MatriceComplexe f = new MatriceComplexe(M,N);
        
        double pr0[][] = F.getPartieReelle();
        double pi0[][] = F.getPartieImaginaire();
        
        //********** v --> n **********
        double pr1[][] = new double[M][N];
        double pi1[][] = new double[M][N];
        
        for(int u=0 ; u<M ; u++)
        {
            for(int n=0 ; n<N ; n++)
            {
                pr1[u][n] = 0.0;
                pi1[u][n] = 0.0;
                double cosTheta = 1.0, newCosTheta;
                double sinTheta = 0.0, newSinTheta;
                double cosDTheta = Math.cos(2.0*Math.PI*(double)n/(double)N);
                double sinDTheta = Math.sin(2.0*Math.PI*(double)n/(double)N);
                for (int v=0 ; v<N ; v++)
                {
                    pr1[u][n] += pr0[u][v] * cosTheta - pi0[u][v] * sinTheta;
                    pi1[u][n] += pr0[u][v] * sinTheta + pi0[u][v] * cosTheta;
                    newCosTheta = cosTheta*cosDTheta - sinTheta*sinDTheta;
                    newSinTheta = sinTheta*cosDTheta + cosTheta*sinDTheta;
                    cosTheta = newCosTheta;
                    sinTheta = newSinTheta;
                }
            }
        }
        
        //********** u --> m **********
        double pr2[][] = new double[M][N];
        double pi2[][] = new double[M][N];
        
        for(int n=0 ; n<N ; n++)
        {
            for(int m=0 ; m<M ; m++)
            {
                pr2[m][n] = 0.0;
                pi2[m][n] = 0.0;
                double cosTheta = 1.0, newCosTheta;
                double sinTheta = 0.0, newSinTheta;
                double cosDTheta = Math.cos(2.0*Math.PI*(double)m/(double)M);
                double sinDTheta = Math.sin(2.0*Math.PI*(double)m/(double)M);
                for (int u=0 ; u<M ; u++)
                {
                    pr2[m][n] += pr1[u][n] * cosTheta - pi1[u][n] * sinTheta;
                    pi2[m][n] += pr1[u][n] * sinTheta + pi1[u][n] * cosTheta;
                    newCosTheta = cosTheta*cosDTheta - sinTheta*sinDTheta;
                    newSinTheta = sinTheta*cosDTheta + cosTheta*sinDTheta;
                    cosTheta = newCosTheta;
                    sinTheta = newSinTheta;
                }
            }
        }
   
        //********** Remplissage de f **********
        for(int m=0 ; m<M ; m++)
            for(int n=0 ; n<N ; n++)
                f.set(m,n,pr2[m][n],pi2[m][n]);
        
        return f;
    }
    
    public static MatriceComplexe decroise(MatriceComplexe F)
    {
        int M = F.getLignes();
        int N = F.getColonnes();
        MatriceComplexe mat = new MatriceComplexe(M,N);
        
        int m,n;
        
        for(m=0 ; m<M/2 ; m++)
            for(n=0 ; n<N/2 ; n++) mat.set(m,n,F.get(M/2+m,N/2+n));
        
        for(m=M/2 ; m<M ; m++)
            for(n=0 ; n<N/2 ; n++) mat.set(m,n,F.get(m-M/2,n+N/2));
        
        for(m=0 ; m<M/2 ; m++)
            for(n=N/2 ; n<N ; n++) mat.set(m,n,F.get(m+M/2,n-N/2));
        
        for(m=M/2 ; m<M ; m++)
            for(n=N/2 ; n<N ; n++) mat.set(m,n,F.get(m-M/2,n-N/2));
        
        return mat;
    }
}
