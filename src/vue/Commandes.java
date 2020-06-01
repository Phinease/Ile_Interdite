package vue;

import control.*;
import modele.Joueur;
import modele.Modele;
import modele.Modele.Artefact;
import control.GetArteFact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Commandes extends JPanel implements ExObserver, NaObserver{

    private final Modele modele;
    private final Vue vue;
    private boolean assBool = true;
    private boolean cle_echange = false;//30/05
    private boolean cles_bouton = false;//30/05
    private boolean sable_bouton;
    private boolean heli_bouton;
    private boolean heli_joueur = false;
    private static final ArrayList<String> artefactes = new ArrayList<>();
    private ArrayList<JButton > cleCourant = new ArrayList<>();
    private ArrayList<JButton> autreJoueur = new ArrayList<>();
    private ArrayList<JButton> heliJoueur = new ArrayList<>();


    JButton next_tour = new JButton("FIN DU TOUR");
    JButton echange = new JButton("ECHANGE CLE");
    JButton cancel = new JButton("CANCEL");
    JButton assecher = new JButton("ASSECHER");

    JButton up = new JButton("UP");
    JButton down = new JButton("DOWN");
    JButton left = new JButton("LEFT");
    JButton right = new JButton("RIGHT");
    JButton mid = new JButton("MID");
    JButton[] directions = {up, down, left, right, mid};

    JButton upright = new JButton("UP RIGHT");
    JButton downright = new JButton("DOWN RIGHT");
    JButton upleft = new JButton("UP LEFT");
    JButton downleft = new JButton("DOWN LEFT");
    JButton[] directionsEx = {upleft, downright, upright, downleft};

    JButton assUR = new JButton("UR");
    JButton assDR = new JButton("DR");
    JButton assUL = new JButton("UL");
    JButton assDL = new JButton("DL");
    JButton[] assEX = {assUR, assDR, assUL, assDL};

    JButton deplacer = new JButton("DEPLACER");
    JButton joueur0 = new JButton("JOUEUR 0");;
    JButton joueur1 = new JButton("JOUEUR 1");;
    JButton joueur2 = new JButton("JOUEUR 2");;
    JButton[] joueurs = {joueur0, joueur1, joueur2};

    JButton sacDeSable = new JButton("SAC DE SABLE");
    JButton heli = new JButton("HELICOPTERE");


    Controleur ass;
    KeyControl kc;
    Controleur finDeTour;
    EchangeCle cleEchange;
    ExControl exploControl;
    NaControl naviControl;
    SableControl sableControl;//31/05
    HeliControl heliControl;


    public Commandes(Modele m,KeyControl kc, Vue vue) {
        this.vue = vue;
        modele = m;
        modele.addExObserver(this);
        modele.addNaObserver(this);

        this.ass = new Assecher(m,this);
        this.kc = kc;
        this.cleEchange = new EchangeCle(modele,this);
        this.finDeTour = new FindeTour(modele,this);
        this.exploControl = new ExControl(modele,this);
        this.naviControl = new NaControl(modele,this);
        this.sableControl = new SableControl(modele,this);//31/05
        this.heliControl = new HeliControl(modele,this);


        next_tour.addKeyListener(kc);
        next_tour.addMouseListener(finDeTour);

        assecher.addMouseListener(ass);
        cancel.addMouseListener(ass);

        Color colorAss = Color.blue;//new Color(65,105,225);
        assecher.setBackground(colorAss);
        assecher.setForeground(Color.white);
        cancel.setBackground(colorAss);
        cancel.setForeground(Color.white);
        for (JButton b: directions) {
            b.addMouseListener(ass);
            b.setBackground(colorAss);
            b.setForeground(Color.white);
        }
        this.add(next_tour);
        this.add(assecher);


        echange.addMouseListener(this.cleEchange);
        echange.setBackground(new Color(255,250,210));
        this.add(echange);
        this.cle_echange = true;
        this.addArtefacts();

        //31/05
        sacDeSable.addMouseListener(sableControl);
        this.add(sacDeSable);
        this.sable_bouton = true;

        heli.addMouseListener(heliControl);
        heli.setBackground(Color.green);
        this.add(heli);
        this.heli_bouton = true;


        for (JButton b: directionsEx) {
            b.addMouseListener(exploControl);
        }

        for (JButton b: assEX) {
            b.addMouseListener(exploControl);
        }

        ArrayList<Joueur> js = modele.getJoueurs();
        int count = 0;
        for (int i = 0; i < js.size(); i++) {
            if(!(js.get(i).getRole() == Joueur.Role.Navigateur)){
                if(count == 3){
                    break;
                }
                joueurs[count] = new JButton("JOUEUR " + (i+1));
                count += 1;
            }
        }

        for (JButton b: joueurs) {
            b.addMouseListener(naviControl);
        }
        deplacer.addMouseListener(naviControl);
    }

    public JFrame myJFrame(){return this.vue;}


    // Assecher Commandes
    public boolean getAssBool(){
        return assBool;
    }

    public void addAssecher(){
        for (JButton b: directions) {
            this.add(b);
        }
        this.add(cancel);

        this.remove(assecher);

        assBool = false;
        repaint();
        this.doLayout();
    }

    public void resetAssecher(){
        for (JButton b: directions) {
            this.remove(b);
        }
        this.remove(cancel);
        this.add(assecher);

        assBool = true;
        repaint();
        this.setPreferredSize(getPreferredSize());
    }


    // Explorateur Commandes
    public void addExAss(){
        for (JButton b: directionsEx) {
            this.remove(b);
        }
        for (JButton b: assEX) {
            this.add(b);
        }
        repaint();
        this.doLayout();
    }

    public void resetExAss(){
        for (JButton b: assEX) {
            this.remove(b);
        }
        for (JButton b: directionsEx) {
            this.add(b);
        }
        repaint();
        this.doLayout();
    }

    //sac de sable
    public void removeSable(){
        this.remove(sacDeSable);
        this.sable_bouton = false;
        repaint();
        this.doLayout();
    }

    //Helicoptere
    public void removeHeli(){
       for(JButton b : heliJoueur){
           this.remove(b);
       }
       heli_joueur = false;


        modele.notifyObservers();
        repaint();
        this.doLayout();


    }

    public void heliJoueurBouton(){
        this.remove(heli);
        this.heli_bouton = false;
        ArrayList<Integer> joueurs = this.modele.joueursMemeZone();
        for(Integer idx : joueurs){
            JButton jr = new JButton("AVEC JOUEUR "+(idx+1));
            jr.addMouseListener(heliControl);
            jr.setBackground(Color.green);
            this.heliJoueur.add(jr);
            this.add(jr);
        }
        this.heli_joueur = true;
        modele.notifyObservers();
        repaint();
        this.doLayout();
    }

    public boolean heliJoueurChosen(){return heli_joueur;}

    // Artefacte Commandes
    private void addArte(String art){
        if(!artefactes.contains(art)){
            JButton arte = new JButton(art);
            GetArteFact getArt = new GetArteFact(modele,this);
            arte.addMouseListener(getArt);
            arte.setBackground(new Color(255,127,80));
            artefactes.add(art);
            this.add(arte);
        }
    }

    public void addArtefacts(){
        ArrayList<Modele.Artefact> artfacts = this.modele.arteApparu();

        if(artfacts.contains(Modele.Artefact.air)){
            this.addArte("GET AIR");
        }
        if(artfacts.contains(Modele.Artefact.eau)){
            this.addArte("GET EAU");
        }
        if(artfacts.contains(Modele.Artefact.terre)){
            this.addArte("GET TERRE");
        }
        if(artfacts.contains(Modele.Artefact.feu)){
            this.addArte("GET FEU");
        }
    }

    // Cles Echange Commandes
    /**
     * Ajouter l'action cleEchange
     * si le joueur courant a au moins une cle
     **/
    //31/05
    public void addClesEchange(){
        Joueur courant = this.modele.getJoueurCourant();
        ArrayList<Artefact> clesCourant = courant.mesCles();

        if(clesCourant.size() !=0 ) {
            if(cle_echange && this.modele.activeEchange()){
                this.remove(echange);
                this.cle_echange = false;
                for (Modele.Artefact cle : clesCourant) {
                    this.addEchangeBouton(cle);
                }
                this.cles_bouton = true;
                System.out.println(this.cles_bouton+"................");
            }else if((courant.getRole() == Joueur.Role.Messageur) && (this.cles_bouton == true)){
                for(JButton b : this.cleCourant){
                    this.remove(b);
                }
                this.cles_bouton = false;
                this.addJoueursBouton(cleEchange);

            }else {
                this.resetEchange();
            }

            repaint();
            this.doLayout();
            this.modele.notifyObservers();
        }

    }

    /** Ajouter le JBotton pour echanger les cles que joueurs courant possede. **/
    private void addEchangeBouton(Modele.Artefact cle){
            JButton cleBouton = new JButton(cle.toString().toUpperCase());
           // EchangeCle cleE = new EchangeCle(modele,this);
            cleBouton.addMouseListener(this.cleEchange);
            cleBouton.setBackground(new Color(255,250,210));
            if(!this.cleCourant.contains(cleBouton)) {
                this.cleCourant.add(cleBouton);
                this.add(cleBouton);
            }
    }

    public boolean getEchange(){return cle_echange;}

    //Pour Messageur
    /**Ajouter les boutons des autres joueurs que Joueur courant
     * pour que un click sur ces bouton informe l'indice du joueur choisi **/
    private void addJoueursBouton(MouseListener ms) {
        this.autreJoueur.clear();
        for (int i = 0; i < this.modele.getJoueurs().size(); i++) {
            if (i != this.modele.getJoueurCourantIdx()) {
                JButton joueurBouton = new JButton("GIVE JOUEUR "+(i+1));
                joueurBouton.addMouseListener(ms);
                this.autreJoueur.add(joueurBouton);
                this.add(joueurBouton);
            }
        }
    }

    /**Si les bputpns de cles sont clique, ajouter les joueurs bouton**/

    /** Reset pour l'echange des cle **/
    public void resetEchange(){
        for (JButton b: this.cleCourant) {
            this.remove(b);
        }

        for (JButton b : this.autreJoueur){
            this.remove(b);
        }
        this.cles_bouton = false;

        this.add(echange);
        this.cle_echange = true;

        repaint();
        this.setPreferredSize(getPreferredSize());
    }


    public void reset(){
        this.resetAssecher();
        this.resetEchange();
        if(!this.sable_bouton) {
            this.add(sacDeSable);
            this.sable_bouton = true;
        }
        if(!heli_bouton){
            this.add(heli);
            this.heli_bouton = true;
        }

        repaint();
        this.doLayout();
    }

    // ExObserver
    @Override
    public void ex() {
        for (JButton b: directionsEx) {
            this.add(b);
        }
        repaint();
        this.doLayout();
    }

    @Override
    public void overEx() {
        for (JButton b: directionsEx) {
            this.remove(b);
        }
        for (JButton b: assEX) {
            this.remove(b);
        }
        repaint();
        this.doLayout();
    }

    @Override
    public void cleanAss() { resetExAss(); }


    // NaObserver
    @Override
    public void na() {
        this.add(deplacer);
    }

    @Override
    public void showNaJoueur() {
        for (JButton b: joueurs) {
            this.add(b);
        }
        this.remove(deplacer);
        repaint();
        this.doLayout();
    }

    @Override
    public void cleanNaJoueur() {
        this.remove(deplacer);
        for (JButton b: joueurs) {
            this.remove(b);
        }
        repaint();
        this.doLayout();
    }

    @Override
    public void chosenNaJoueur(Joueur.Jou j) {}

    @Override
    public void overNa() {
        for (JButton b: directionsEx) {
            this.remove(b);
        }
        this.remove(deplacer);
        for (JButton b: joueurs) {
            this.remove(b);
        }
        repaint();
        this.doLayout();
    }
}