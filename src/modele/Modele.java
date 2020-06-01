package modele;

import control.KeyControl;
import jeuDeCartes.CarteZone;
import jeuDeCartes.Paquet;
import modele.Joueur.Role;

import javax.swing.*;
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
                zones[i][j] = new Zone(this,i,j);
            }
        }
        init();
        for (int i = 0; i < 24; i++) {
            findeTour(3);
        }
    }

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
        HashSet<Integer> hs = new HashSet<>();
        do {
            int tmp = random.nextInt(6);
            hs.add(tmp);
        } while (hs.size() != 4);
        joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                Role.Joueur));
        Iterator<Integer> it = hs.iterator();


        for (int i = 0; i < 2; i++) {
            int x = it.next();
            switch (x){
                case 0:
                    joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                            Role.Pilote));
                    break;
                case 1:
                    joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                            Role.Ingenieur));
                    break;
                case 2:
                    joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                            Role.Explorateur));
                    break;
                case 3:
                    joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                            Role.Navigateur));
                    break;
                case 4:
                    joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                            Role.Plongeur));
                    break;
                case 5:
                    joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                            Role.Messageur));
                    break;
                default:
                    joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                            Role.Joueur));
                    break;
            }
        }
        joueurs.add(new Joueur(this,random.nextInt(LARGEUR)+1,random.nextInt(HAUTEUR)+1,
                Role.Navigateur));

        //init Paquet
        this.PaquetZone=new Paquet(this.zones,this.LARGEUR,this.HAUTEUR);
        this.PaquetZone.melanger();
    }

    public void deplacePilote(Zone e){
        Joueur courant = getJoueurCourant();
        if(courant.getRole() == Role.Pilote){
            courant.deplacePilote(e);
        }
        notifyObservers();
    }


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


    public boolean horsJeu(Zone e){
        if(e.getX() < 0 || e.getX() > LARGEUR){
            return true;
        }
        return e.getY() < 0 || e.getY() > HAUTEUR;
    }


    public void assecherEx(Joueur.DirectionEx e){
        Joueur courant = getJoueurCourant();
        if(courant.testAction()) {
            int x = courant.getPosition().getX();
            int y = courant.getPosition().getY();
            //courant.addAction();
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
            //if (horsJeu(zones[x][y]) || zones[x][y].getStatus() != 1) {
            if (horsJeu(zones[x][y]) || !(zones[x][y].getEtat() instanceof Etat_Inondee)) {
                return;
            }
            boolean asse = zones[x][y].assecher();
            if(asse){courant.addAction();}
        }
    }


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

    //precondition sacDeSable = true
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
    /**Action sac de sable: this.sacDeSable = true si le bouton sacDeSable a ete clicke  **/
    public void activeSable (){this.sableActived = true;}
    public boolean getSable(){return this.sableActived;}


    /**Action Helicoptere**/
    public void activeHeli(){
        this.heliActived = true;
        this.heliJoueurIdx.add(this.joueurIdx);
    }
    public boolean getHeli(){return this.heliActived;}
    public void addHeliJoueur(int idx){this.heliJoueurIdx.add(idx);}

    /**Action special helicoptere**/
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

    //30/05
   /** return true si il existe au moins un joueurs avec qui le joueurs courant peut echange une cle
    * ou si le joueur courant est un messageur
    * **/
   public boolean activeEchange(){
       ArrayList<Integer> js = joueursMemeZone();
       ArrayList<Artefact> clesCourant = this.getJoueurCourant().mesCles();
       boolean cond = (js.size() >0)||(this.getJoueurCourant().getRole() == Joueur.Role.Messageur);
       return cond && this.getJoueurCourant().testAction() &&(clesCourant.size()>0);
   }


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

//30/05
    /**Cle Echange pour Messageur**/
    public void cleEchangeMessageur(int idxJoueur, Artefact cle){
        Joueur courant = this.getJoueurCourant();
        if(courant.getRole() == Joueur.Role.Messageur && this.activeEchange()){
            courant.echangeCle(this.joueurs.get(idxJoueur),cle);
        }
        notifyObservers();
    }

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

    public boolean avoirTousArtefact(){
        int nbT=0;
        for (Joueur j:joueurs){
            nbT+=j.artefacts.size();
        }
        return nbT == 4;
    }

    public boolean gameEnd(){
        for(Joueur j:joueurs){
            if(!j.position.nonSubmerge()){
                return true;
            }
            if(j.position!=zones[heli][heli]){
                return false;
            }
        }
        return avoirTousArtefact();
    }

    public void End(){
        checkEnd(gameEnd());
    }

    public Zone[][] getZones() {
        return zones;
    }
}





