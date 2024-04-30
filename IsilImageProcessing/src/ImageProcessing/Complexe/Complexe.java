package ImageProcessing.Complexe;

public class Complexe 
{
    private double partieReelle;
    private double partieImaginaire;
    
    /** Creates a new instance of Complexe */
    public Complexe(double pr,double pi) 
    {
        partieReelle = pr;
        partieImaginaire = pi;
    }
    
    public double getModule()
    {
        return Math.sqrt(partieReelle*partieReelle + partieImaginaire*partieImaginaire);
    }
    
    public double getPhase()
    {
        return Math.atan2(partieImaginaire,partieReelle);
    }

    public void additionne(Complexe c)
    {
        partieReelle += c.getPartieReelle();
        partieImaginaire += c.getPartieImaginaire();
    }
    
    public void multiplie(Complexe c)
    {
        double r,i;
        r = partieReelle*c.getPartieReelle() - partieImaginaire*c.getPartieImaginaire();
        i = partieReelle*c.getPartieImaginaire() + partieImaginaire*c.getPartieReelle();
        partieReelle = r;
        partieImaginaire = i;
    }
    
    public Complexe conjugue()
    {
        return new Complexe(partieReelle,-partieImaginaire);
    }
    
    public double getPartieReelle() {
        return partieReelle;
    }

    public void setPartieReelle(double partieReelle) {
        this.partieReelle = partieReelle;
    }

    public double getPartieImaginaire() {
        return partieImaginaire;
    }

    public void setPartieImaginaire(double partieImaginaire) {
        this.partieImaginaire = partieImaginaire;
    }
    
}
