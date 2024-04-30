package ImageProcessing.Complexe;

public class MatriceComplexe 
{
    private Complexe  m[][];
    private int       lignes;
    private int       colonnes;
    
    /** Creates a new instance of MatriceComplexe */
    public MatriceComplexe(int l,int c) 
    {
        lignes = l;
        colonnes = c;
        m = new Complexe[l][c];
        for(int i=0 ; i<lignes ; i++)
            for(int j=0 ; j<colonnes ; j++)
                m[i][j] = new Complexe(0.0,0.0);
    }
    
    public void set(int ligne,int colonne,Complexe complexe)
    {
        m[ligne][colonne] = complexe;
    }
    
    public void set(int ligne,int colonne,double partieReelle,double partieImaginaire)
    {
        Complexe c = new Complexe(partieReelle,partieImaginaire);
        set(ligne,colonne,c);
    }
    
    public Complexe get(int ligne,int colonne)
    {
        return m[ligne][colonne];
    }

    public double[][] getPartieReelle()
    {
        double d[][] = new double[lignes][colonnes];
        for(int i=0 ; i<lignes ; i++)
            for(int j=0 ; j<colonnes ; j++)
                d[i][j] = m[i][j].getPartieReelle();
        return d;
    }

    public double[][] getPartieImaginaire()
    {
        double d[][] = new double[lignes][colonnes];
        for(int i=0 ; i<lignes ; i++)
            for(int j=0 ; j<colonnes ; j++)
                d[i][j] = m[i][j].getPartieImaginaire();
        return d;
    }

    public double[][] getModule()
    {
        double d[][] = new double[lignes][colonnes];
        for(int i=0 ; i<lignes ; i++)
            for(int j=0 ; j<colonnes ; j++)
                d[i][j] = m[i][j].getModule();
        return d;
    }
     
    public double[][] getPhase()
    {
        double d[][] = new double[lignes][colonnes];
        for(int i=0 ; i<lignes ; i++)
            for(int j=0 ; j<colonnes ; j++)
                d[i][j] = m[i][j].getPhase();
        return d;
    }
        
    public int getLignes() {
        return lignes;
    }

    public int getColonnes() {
        return colonnes;
    }
    
}
