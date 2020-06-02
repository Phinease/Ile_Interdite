package modele;

import  modele.Modele.Artefact;

abstract class Cle {
    /**
     * notre modele ont 4 type de cle et chaque type ont 4 cle au total
     * **/
    protected final int nbrMax = 4 ;

    /**ajouter des cle si il reste des cle non apparu**/
    abstract boolean addCle();

    /**return le type de cle si tous les cles de ce type sont apparu,
     * sinon return Artefact.normal **/
    abstract Artefact allKey();
}

class CleAir extends Cle{
    private static int nbr = 0;

    public boolean addCle(){
        if(nbr <nbrMax){
            nbr ++;
            return true;
        }
        return false;
    }

    public Modele.Artefact allKey() {
        if (nbr == this.nbrMax) {
            return Modele.Artefact.air;
        }
        return Modele.Artefact.normal;
    }

}

class CleEau extends Cle{
    private static int nbr = 0;

    public boolean addCle(){
        if(nbr <nbrMax){
            nbr++;
            return true;
        }
        return false;
    }

    public Modele.Artefact allKey() {
        if (nbr == this.nbrMax) {
            return Modele.Artefact.eau;
        }
        return Modele.Artefact.normal;
    }
}

class CleTerre extends Cle{
    private static int nbr = 0;

    public boolean addCle(){
        if(nbr <nbrMax){
            nbr++;
            return true;
        }
        return false;
    }
    public Modele.Artefact allKey() {
        if (nbr == this.nbrMax) {
            return Modele.Artefact.terre;
        }
        return Modele.Artefact.normal;
    }
}

class CleFeu extends Cle{
    private static int nbr = 0;

    public boolean addCle(){
        if(nbr <nbrMax){
            nbr++;
            return true;
        }
        return false;
    }

    public Modele.Artefact allKey() {
        if (nbr == this.nbrMax) {
            return Modele.Artefact.feu;
        }
        return Modele.Artefact.normal;
    }
}

