package modele;

public class Zone {
    private Modele modele;
    private final int x;
    private final int y;
    protected Etat etat ;
    protected Modele.Artefact type = Modele.Artefact.normal;


    public Zone(Modele m, int x, int y){
        this.x = x;
        this.y = y;
        this.modele = m;
        this.etat = new Etat_Normal();
    }

    public void setType(Modele.Artefact type){
        this.type = type;
    }

    public Modele.Artefact getType(){
        return this.type;
    }

    public String toString(){return "posit: " + this.x+" " +this.y;}


    public void evolue(){
        if(etat instanceof Etat_Normal){
            this.etat = new Etat_Inondee();
        }else if (etat instanceof Etat_Inondee){
            this.etat = new Etat_Submerge();
        }else{
            return;
        }
    }

    public boolean assecher(){
        if(etat instanceof Etat_Inondee){
            //System.out.println("zone ass");
            etat = new Etat_Normal();
            return true;
        }
        return false;
    }

    public boolean nonSubmerge(){
        return !(this.etat instanceof Etat_Submerge);
    }

    public Etat getEtat(){return this.etat;}

    public int getStatus() {
        return this.etat.getStatus();
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }

    public boolean equal(Zone z){
        return (z.getX() == this.x) && (z.getY() == this.y);
    }

}
