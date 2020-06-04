package modele;

import control.KeyControl;
import jeuDeCartes.CarteZone;
import jeuDeCartes.Paquet;
import modele.Joueur.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Modele extends vue.Observable {
    public static final int HAUTEUR = 22, LARGEUR = 22;
    public static final int heli = HAUTEUR/2;
    private static final int nbJoueur = 4;
    protected Zone[][] zones;
    private final Random random = new Random();
    protected int joueurIdx = 0;
    protected int naviChoix = 0;
    protected boolean selected = false;
    protected boolean sableActived = false;
    protected boolean heliActived = false;
    protected ArrayList<Integer> heliJoueurIdx = new ArrayList<>();
    protected ArrayList<Joueur> joueurs = new ArrayList<>();
    protected Artefact cleChosen = Artefact.normal;
    public enum Artefact{air, eau, terre, feu, normal}
    public Paquet PaquetZone;


    public Modele() {
        zones = new Zone[LARGEUR+2][HAUTEUR+2];
        for (int i = 0; i < LARGEUR+2 ; i++) {
            for (int j = 0; j < HAUTEUR+2; j++) {
                zones[i][j] = new Zone(i,j);
            }
        }
        init();
        // DEBUG CODE
//        for (int i = 0; i < 24; i++) {
//            findeTour(3);
//        }
    }
    /*Initialiser le modèle avec les zones, les joueurs et les paquets */
    public void init() {
        for (int i = 0; i <= LARGEUR; i++) {
            for (int j = 0; j <= HAUTEUR; j++) {
                zones[i][j].etat = new Etat_Normal();
                if(i <= LARGEUR/2){
                    if(j <= HAUTEUR/2){
                        zones[i][j].setType(Artefact.air);
                    } else {
                        zones[i][j].setType(Artefact.eau);
                    }
                } else {
                    if(j <= HAUTEUR/2){
                        zones[i][j].setType(Artefact.feu);
                    } else {
                        zones[i][j].setType(Artefact.terre);
                    }
                }
            }
        }
        // Random Joueur Par HashSet et RandomInt
        HashSet<Integer> hs = new HashSet<>();
        do {
            int tmp = random.nextInt(6);
            hs.add(tmp);
        } while (hs.size() != 3);
        joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                Role.Joueur));
        Iterator<Integer> it = hs.iterator();
        for (int i = 0; i < 3; i++) {
            int x = it.next();
            System.out.println(x);
            Role r;
            switch (x){
                case 0:
                    r = Role.Pilote;
                    break;
                case 1:
                    r = Role.Ingenieur;
                    break;
                case 2:
                    r = Role.Explorateur;
                    break;
                case 3:
                    r = Role.Navigateur;
                    break;
                case 4:
                    r = Role.Plongeur;
                    break;
                case 5:
                    r = Role.Messageur;
                    break;
                default:
                    r = Role.Joueur;
                    break;
            }
            joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1, r));
        }

        //init Paquet
        this.PaquetZone=new Paquet(this.zones, LARGEUR, HAUTEUR);
        this.PaquetZone.melanger();
    }

    /*Spécialiser le mouvement de Pilote */
    public void deplacePilote(Zone e){
        Joueur courant = getJoueurCourant();
        if(courant.getRole() == Role.Pilote){
            courant.deplacePilote(e);
        }
        notifyObservers();
    }

    // Choisir Joueur pour la navigateur
    public void choisirNa(Joueur.Jou j){
        chosen(j);
        switch (j){
            case j1:
                naviChoix = 0;
                break;
            case j2:
                naviChoix = 1;
                break;
            case j3:
                naviChoix = 2;
                break;
            case j4:
                naviChoix = 3;
                break;
        }
        selected = true;
    }

    /*Spécialiser le mouvement de Navigateur */
    public void deplaceNavi(Zone e){
        Joueur courant = getJoueurCourant();
        if(courant.getRole() == Role.Navigateur && selected && e.nonSubmerge()){
            joueurs.get(naviChoix).move(e);
            courant.addAction();
            selected = false;
        }
        cleanJoueur();
        notifyObservers();
    }

    /*Spécialiser le mouvement de Explorateur */
    public void moveExplorateur(Joueur.DirectionEx e){
        Joueur courant = getJoueurCourant();
        if(courant.testAction()){
            Zone pos = courant.getPosition();
            int new_Y = pos.getY();
            int new_X = pos.getX();
            switch (e){
                case leftup:
                    new_X -= 1;
                    new_Y -= 1;
                    break;
                case leftdown:
                    new_X -= 1;
                    new_Y += 1;
                    break;
                case rightup:
                    new_X += 1;
                    new_Y -= 1;
                    break;
                case rightdown:
                    new_X += 1;
                    new_Y += 1;
                    break;
                default:
                    break;
            }
            if(horsJeu(zones[new_X][new_Y]) || !zones[new_X][new_Y].nonSubmerge()){
                return;
            }
            courant.position = this.getZone(new_X,new_Y);
            courant.addAction();
        }
        notifyObservers();
    }

    /*le mouvement de joueurs normales */
    public void move(KeyControl.Direction e){
        Joueur courant = getJoueurCourant();
        if(courant.testAction()) {
            Zone pos = courant.getPosition();
            int new_Y = pos.getY();
            int new_X = pos.getX();
            switch (e){
                case up:
                    new_Y -= 1;
                    break;
                case down:
                    new_Y += 1;
                    break;
                case left:
                    new_X -= 1;
                    break;
                case right:
                    new_X += 1;
                    break;
                default:
                    break;
            }
            if(horsJeu(zones[new_X][new_Y]) || !zones[new_X][new_Y].nonSubmerge()){
                if(courant.getRole() == Role.Plongeur && !zones[new_X][new_Y].nonSubmerge() && courant.restNbr() > 1){
                    switch (e){
                        case up:
                            new_Y -= 1;
                            break;
                        case down:
                            new_Y += 1;
                            break;
                        case left:
                            new_X -= 1;
                            break;
                        case right:
                            new_X += 1;
                            break;
                        default:
                            break;
                    }
                    courant.addAction();
                } else {
                    return;
                }
            }
            courant.position = this.getZone(new_X,new_Y);
            courant.addAction();
        }
        notifyObservers();
    }


    /*Déterminer si une zone est sortie du jeu  */
    public boolean horsJeu(Zone e){
        if(e.getX() < 1 || e.getX() > LARGEUR){
            return true;
        }
        return e.getY() < 1 || e.getY() > HAUTEUR;
    }


    /*Assecher les zones diagonales */
    public void assecherEx(Joueur.DirectionEx e){
        Joueur courant = getJoueurCourant();
        if(courant.testAction()) {
            int x = courant.getPosition().getX();
            int y = courant.getPosition().getY();
            switch (e) {
                case leftup:
                    y -= 1;
                    x -= 1;
                    break;
                case leftdown:
                    y += 1;
                    x -= 1;
                    break;
                case rightdown:
                    x += 1;
                    y += 1;
                    break;
                case rightup:
                    x += 1;
                    y -= 1;
                    break;
                default:
                    break;
            }
            cleanAss();
            if (horsJeu(zones[x][y]) || !(zones[x][y].getEtat() instanceof Etat_Inondee)) {
                return;
            }
            zones[x][y].assecher();
            courant.addAction();
        }
    }


    /*Assecher les zones autour(up,down,left,right) */
    public boolean assecher(KeyControl.Direction e){
        Joueur courant = getJoueurCourant();
        int x = courant.getPosition().getX();
        int y = courant.getPosition().getY();
        if(courant.testAction()) {
            switch (e) {
                case up:
                    y -= 1;
                    break;
                case down:
                    y += 1;
                    break;
                case right:
                    x += 1;
                    break;
                case left:
                    x -= 1;
                    break;
                case mid :
                    break;
                default:
                    break;
            }
            //if (horsJeu(zones[x][y]) || zones[x][y].getStatus() != 1) {
            if (horsJeu(zones[x][y]) || !(zones[x][y].getEtat() instanceof Etat_Inondee)) {
                return false;
            }
            boolean asse = zones[x][y].assecher();
            if(asse){courant.addAction();}
        }
        return true;
    }

    /**Action speciale: sac de sable, return true si la zone est asseche.
     * x,y coordonee de la zone choisi a assecher
     * precondition: sableActived == true
     * **/
    public boolean assecherSable(int x, int y){
        if(this.zones[x][y].getStatus() == 1){
            this.zones[x][y].assecher();
            getJoueurCourant().addAction();
            sableActived =false;
            notifyObservers();
            return true;
        }
        return false;
    }


    //31/05
    /**activeSable: this.sableActived = true
     * si le bouton sacDeSable a ete clique  **/
    public void activeSable (){this.sableActived = true;}
    public boolean getSable(){return this.sableActived;}


    /**activeHeli:
     * heliActived = true si le bouton helicoptere a ete clique
     * et ajouter le joueur courant dans la liste de joueur a deplacer
     * **/
    public void activeHeli(){
        this.heliActived = true;
        this.heliJoueurIdx.add(this.joueurIdx);
    }
    public boolean getHeli(){return this.heliActived;}
    public void addHeliJoueur(int idx){this.heliJoueurIdx.add(idx);}

    /**Action special helicoptere
     * deplacer joueur courant pour n'importe quel zone non submergee
     * eventuellement avec un autre joueur dans la meme zone**/
    public boolean deplaceHeli(int x, int y){
        Zone z = this.getZone(x,y);
        if(z.nonSubmerge()) {
            for (Integer idx : this.heliJoueurIdx) {
                this.joueurs.get(idx).move(z);
            }
            this.getJoueurCourant().addAction();
            notifyObservers();
            return true;
        }
        return false;
    }

    public Zone getZone(int x,int y){
        return zones[x][y];
    }

    public ArrayList<Joueur> getJoueurs(){
        return this.joueurs;
    }
    public Joueur getJoueurCourant(){ return this.joueurs.get(this.joueurIdx); }
    public int getJoueurCourantIdx(){ return this.joueurIdx; }

    public void chosenCle(Artefact art){ this.cleChosen = art; }

    /**renvoyer les indix des joueurs dans le meme zone que joueur courant**/
    public ArrayList<Integer> joueursMemeZone(){
        ArrayList<Integer> js = new ArrayList<>();
        Joueur courant = this.joueurs.get(this.joueurIdx);
        for(int i = 0; i< this.joueurs.size();i++){
            if(i != joueurIdx && courant.getPosition().equal(joueurs.get(i).getPosition())){
                js.add(i);
            }
        }
        return js;
    }

   /** return true si 1)il existe au moins un joueurs avec qui le joueurs courant peut echange une cle
    * ou si le joueur courant est un messageur
    * et si 2)le joueur courant possede des cles et il lui reste des nbr d'action
    * **/
   public boolean activeEchange(){
       ArrayList<Integer> js = joueursMemeZone();
       ArrayList<Artefact> clesCourant = this.getJoueurCourant().mesCles();
       boolean cond = (js.size() >0)||(this.getJoueurCourant().getRole() == Joueur.Role.Messageur);
       return cond && this.getJoueurCourant().testAction() &&(clesCourant.size()>0);
   }


   /**echange le cle donne en parametre avec le joueur dans le meme zone que courant
    * si il y a plusieurs joueurs dans le meme zone, on echange le cle a un joueur aleatoirement**/
    public void cleEchange(Artefact cle){
        ArrayList<Integer> js = joueursMemeZone();
        Joueur courant = getJoueurCourant();
        if(courant.haveCle(cle)&&js.size()>0 && courant.testAction()){
            if(js.size() == 1){
                courant.echangeCle(joueurs.get(js.get(0)),cle);
            }else{
                int idx = random.nextInt(js.size());
                courant.echangeCle(joueurs.get(js.get(idx)),cle);
            }
        }
        notifyObservers();
    }

    /**Cle Echange pour Messageur : donner le cle au joueur dans les parametre**/
    public void cleEchangeMessageur(int idxJoueur, Artefact cle){
        Joueur courant = this.getJoueurCourant();
        if(courant.getRole() == Joueur.Role.Messageur && this.activeEchange()){
            courant.echangeCle(this.joueurs.get(idxJoueur),cle);
        }
        notifyObservers();
    }


    /*Finir un tour de jeu, commencer un nouveau tour
    * Tirer les cartes pour inonder des zones */
    public void findeTour(int nbr){
        //Inondee par random
//        while (nbr>0){
//            int x = random.nextInt(LARGEUR+1);
//            int y = random.nextInt(HAUTEUR+1);
//            if(zones[x][y].nonSubmerge() && (x != heli || y != heli)){
//                zones[x][y].evolue();
//                nbr -= 1;
//            }
//        }

        //Inondee par Jeu de Carte
        while(nbr>0) {
             CarteZone z = this.PaquetZone.tirer();
             int x=z.getX();
             int y=z.getY();
             if(zones[x][y].nonSubmerge()&& (x != heli || y != heli)){
                 zones[x][y].evolue();
                 nbr-=1;
             }

        }

        int bonneChance = random.nextInt(LARGEUR*HAUTEUR-2);
        this.getJoueurCourant().getCle(bonneChance);
        this.getJoueurCourant().resetNbr();

        this.sableActived = false;

        switch (getJoueurCourant().getRole()){
            case Explorateur:
                cleanAss();
                overEx();
                break;
            case Navigateur:
                overNa();
                break;
            case Messageur:
                //overMe();
                break;
            default:
                break;
        }
        joueurIdx += 1;
        if(joueurIdx == nbJoueur){
            joueurIdx = 0;
        }
        switch (getJoueurCourant().getRole()){
            case Explorateur:
                nextEx();
                break;
            case Navigateur:
                nextNa();
                break;
            case Messageur:
                break;
            default:
                break;
        }
        selected = false;
        this.heliJoueurIdx.clear();

        notifyObservers();
        End();
    }

    /**Si tous les cles pour recuperer un artefacte est apparu,alors cet artefacte est apparu
     * return les artefact apparu pour ajouter les boutons correpondants apres**/
    public ArrayList<Artefact> arteApparu(){
        ArrayList<Artefact> arts= new ArrayList<>();
        ArrayList<Cle> cles= new ArrayList<>();
        cles.add(new CleAir());
        cles.add(new CleEau());
        cles.add(new CleFeu());
        cles.add(new CleTerre());

        for(Cle c : cles){
            if(c.allKey()!= Artefact.normal){
                arts.add(c.allKey());
            }
        }
        return arts;
    }

    /*Déterminer si les joueurs ont trouvé tous les quatre artefact */
    public boolean avoirTousArtefact(){
        int nbT=0;
        for (Joueur j:joueurs){
            nbT+=j.artefacts.size();
        }
        return nbT == 4;
    }

    /*Déterminer s'il existe un joueur est mort
    * le joueur va mourir s'il dans la zone submergée */
    public boolean estMort(){//true->要死
        for(Joueur j:joueurs){
            if(!j.position.nonSubmerge()){
                System.out.println("MORT");
                return true;
            }
        }
        return false;
    }

    /*Déterminer si les joueurs gagnent le jeu
     *le critère de gagner est avoir tous les quatre artefact et tous les joueurs
     * sont dans la zone de héliport */
    public boolean win(){
        for(Joueur j:joueurs){
            if(j.position!=zones[heli][heli]){
                return false;
            }
        }
        return avoirTousArtefact();
    }

    /* Si les joueurs gagnent le jeu, l'ecran va afficher GAME WIN et le jeu est terminéé
     * si un des joueurs est mort, l'ecran va afficher GAME OVER et le jeu est terminéé  */
    public void End(){
        if(estMort()){
            checkEnd(false);
        }
        else if(win()){
            checkEnd(true);
        }
    }
}





