package modele;


import java.util.ArrayList;
import java.util.HashMap;
import modele.Modele.Artefact;

public class Joueur {
    private int nbrAction = 0;
    protected Zone position;
    protected Modele modele;
    protected Role role = Role.Joueur;
    protected HashMap<Artefact,Integer> cles = new HashMap<Artefact,Integer>();
    protected ArrayList<Artefact> artefacts = new ArrayList<>();
    public enum DirectionEx{leftup, leftdown, rightdown, rightup}
    public enum Jou{j1, j2, j3, j4};
    public enum Role{Joueur, Pilote, Ingenieur, Explorateur, Navigateur, Plongeur, Messageur};

    public Joueur(Modele m,int x,int y, Role c){
        this.modele = m;
        this.role = c;
        while(modele.zones[x][y].getStatus() != 0){
            x += 1;
        }
        position = modele.getZone(x,y);
        cles.putIfAbsent(Artefact.air,0);
        cles.putIfAbsent(Artefact.eau,0);
        cles.putIfAbsent(Artefact.terre,0);
        cles.putIfAbsent(Artefact.feu,0);
    }

    /**renvoyer le role de joueur**/
    public Role getRole(){ return this.role; }

    public boolean testAction(){
        return this.nbrAction<3;
    }
    protected void resetNbr(){this.nbrAction = 0;}
    public int restNbr(){
        return 3-this.nbrAction;
    }
    public void addAction(){
        nbrAction++;
    }

    public void prioriteInge(){
        if(this.role == Role.Ingenieur) {
            this.nbrAction--;
        }
    }

    public Zone getPosition(){
        return position;
    }
    public HashMap<Artefact,Integer> getCles(){
        return cles;
    }

    public void getCle(int randomNbr){
        if(randomNbr%6 < 3){
            Artefact acte = this.getPosition().getType();
            switch (acte) {
                case air:
                    Cle air = new CleAir();
                    if (air.addCle()) {
                        int n = this.cles.get(Artefact.air);
                        this.cles.replace(Artefact.air, n + 1);
                    }
                    break;
                case eau:
                    Cle eau = new CleEau();
                    if (eau.addCle()) {
                        int n = this.cles.get(Artefact.eau);
                        this.cles.replace(Artefact.eau, n + 1);
                    }
                    break;
                case terre:
                    Cle terre = new CleTerre();
                    if (terre.addCle()) {
                        int n = this.cles.get(Artefact.terre);
                        this.cles.replace(Artefact.terre, n + 1);
                    }
                    break;
                case feu:
                    Cle feu = new CleFeu();
                    if (feu.addCle()) {
                        int n = this.cles.get(Artefact.feu);
                        this.cles.replace(Artefact.feu, n + 1);
                    }
                    break;
            }
        }
    }

    //30/05
    /**return true si "this* possede le cle du type indique*/
    public boolean haveCle(Artefact cle){
        return this.cles.get(cle) >=1;
    }

    /**donner les types de cles possede par le joueur courant**/
    public ArrayList<Artefact> mesCles(){
        ArrayList<Artefact> myKey = new ArrayList<>();
        Artefact[] allKey ={Modele.Artefact.air, Modele.Artefact.eau, Modele.Artefact.terre, Modele.Artefact.feu};
        for(Artefact cle : allKey){
            if(this.cles.get(cle) >0){
                myKey.add(cle);
            }
        }
        return myKey;
    }

    /**donner une cle a un autre joueur
     * precondition "this" possede bien une cle du type donnee
     **/
    public void echangeCle(Joueur joueur, Artefact cle){
        if(cle != Artefact.normal) {
            int n = this.cles.get(cle);
            this.cles.replace(cle, n - 1);
            joueur.receiveCle(cle);
        }
    }

    /** recevoir une cle d'un autre joueur**/
    private void receiveCle(Artefact cle){
        int n = this.cles.get(cle);
        this.cles.replace(cle,n+1);
    }

    //Artefact
    public boolean getArteFact( Modele.Artefact artefact) {
        if (this.cles.get(artefact) == 4 && (!artefacts.contains(artefact))) {
            artefacts.add(artefact);
            this.cles.replace(artefact,0);
            addAction();
            return true;
        } else {
            return false;
        }
    }

    public String getHasArt(Modele.Artefact artefact){
        if(artefacts.contains(artefact)){
            return "Oui";
        } else {
            return "Non";
        }
    }

    //deplacement
    public void move(Zone zone){
        if(zone.nonSubmerge()){
            this.position = zone;
        }
    }

    public void deplacePilote(Zone zone){
        if(this.testAction() && zone.nonSubmerge()) {
            this.position = zone;
            this.addAction();
        }
    }

    public String toString(){
        return this.role.toString();
    }
}



