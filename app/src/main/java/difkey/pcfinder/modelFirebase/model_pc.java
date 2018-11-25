package difkey.pcfinder.modelFirebase;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class model_pc {
    public String annee;
    public String dimensions;
    public String marque;
    public String modele;
    public String poids;
    public String processeur;
    public String ram;
    public String resolution;
    public String stockage;
    public String taille;


    public model_pc() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public model_pc(String modele,
                    String processeur,
                    String annee,
                    String ram,
                    String marque,
                    String dimensions,
                    String resolution,
                    String stockage,String taille,
                    String poids) {
        this.modele = modele;
        this.processeur = processeur;
        this.annee = annee;
        this.ram = ram;
        this.marque = marque;
        this.poids = poids;
        this.dimensions = dimensions;
        this.resolution = resolution;
        this.stockage = stockage;
        this.taille = taille;
    }

    public String toString(){
        //TODO
        return "";
    }

}
