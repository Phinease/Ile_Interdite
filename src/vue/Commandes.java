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
    private boolean cle_echange;
    private boolean cles_bouton = false;
    private boolean sable_bouton;
    private boolean heli_bouton;
    private boolean heli_joueur = false;


    private final ArrayList<String> artefactes = new ArrayList<>();
    private final ArrayList<JButton> cleCourant = new ArrayList<>();
    private final ArrayList<JButton> autreJoueur = new ArrayList<>();
    private final ArrayList<JButton> heliJoueur = new ArrayList<>();


    // Les clés de commandes basiques
    JButton next_tour = new JButton("FIN DU TOUR");
    JButton echange = new JButton("ECHANGE");
    JButton cancel = new JButton("CANCEL");
    JButton assecher = new JButton("ASSECHER");

    // Les clés de directions d'assécher
    JButton up = new JButton("UP");
    JButton down = new JButton("DOWN");
    JButton left = new JButton("LEFT");
    JButton right = new JButton("RIGHT");
    JButton mid = new JButton("MID");
    JButton[] directions = {up, down, left, right, mid};

    // Les clés de direction movement pour l'explorateur
    JButton upright = new JButton("UP RIGHT");
    JButton downright = new JButton("DOWN RIGHT");
    JButton upleft = new JButton("UP LEFT");
    JButton downleft = new JButton("DOWN LEFT");
    JButton[] directionsEx = {upleft, downright, upright, downleft};

    // Les clés de direction d'assécher pour l'explorateur
    JButton assUR = new JButton("UR");
    JButton assDR = new JButton("DR");
    JButton assUL = new JButton("UL");
    JButton assDL = new JButton("DL");
    JButton[] assEX = {assUR, assDR, assUL, assDL};

    // Les clés de Deplacerment de joueur pour le navigateur
    JButton deplacer = new JButton("DEPLACER");
    JButton joueur0 = new JButton("JOUEUR 0");
    JButton joueur1 = new JButton("JOUEUR 1");
    JButton joueur2 = new JButton("JOUEUR 2");
    JButton[] joueurs = {joueur0, joueur1, joueur2};


    JButton sacDeSable = new JButton("SABLE");
    JButton heli = new JButton("HELICOPTERE");
    JButton heli_me = new JButton("JUST ME");

    // Controleur pour les joueurs différentes
    Assecher ass;
    KeyControl kc;
    FindeTour finDeTour;
    EchangeCle cleEchange;
    ExControl exploControl;
    NaControl naviControl;
    SableControl sableControl;
    HeliControl heliControl;

    public Commandes(Modele m,KeyControl kc, Vue vue) {
        this.vue = vue;
        modele = m;
        // Addition dans l'observeur
        modele.addExObserver(this);
        modele.addNaObserver(this);

        // Initiation de Controleurs
        ass = new Assecher(m,this);
        this.kc = kc;
        cleEchange = new EchangeCle(modele,this);
        finDeTour = new FindeTour(modele,this);
        exploControl = new ExControl(modele,this);
        naviControl = new NaControl(modele,this);
        sableControl = new SableControl(modele,this);
        heliControl = new HeliControl(modele,this);

        // Addition de KeyListener et de MouseListener et Couleur de Button
        next_tour.addKeyListener(kc);
        next_tour.addMouseListener(finDeTour);
        assecher.addMouseListener(ass);
        cancel.addMouseListener(ass);

        Color colorAss = Color.cyan;//new Color(65,105,225);
        assecher.setBackground(colorAss);
        assecher.setOpaque(true);
        cancel.setBackground(colorAss);
        assecher.setOpaque(true);
        for (JButton b: directions) {
            b.addMouseListener(ass);
            b.setBackground(colorAss);
            b.setOpaque(true);
        }
        this.add(next_tour);
        this.add(assecher);

        echange.addMouseListener(this.cleEchange);
        echange.setBackground(new Color(255,250,210));
        echange.setOpaque(true);
        this.add(echange);
        this.cle_echange = true;
        this.addArtefacts();

        sacDeSable.addMouseListener(sableControl);
        sacDeSable.setBackground(Color.green);
        this.add(sacDeSable);
        this.sable_bouton = true;

        heli.addMouseListener(heliControl);
        heli.setBackground(Color.green);
        heli.setOpaque(true);
        this.add(heli);
        this.heli_bouton = true;

        heli_me.addMouseListener(heliControl);
        heli_me.setBackground(Color.green);



        for (JButton b: directionsEx) {
            b.addMouseListener(exploControl);
        }

        for (JButton b: assEX) {
            b.addMouseListener(exploControl);
            b.setBackground(colorAss);
            b.setOpaque(true);
        }

        // Button pour Les autres joueurs pour le navigateur
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

        // SetLayout pour les commandes
        this.setLayout(new GridLayout());
    }

    public JFrame myJFrame(){return vue.getjFrame();}


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
        System.out.println("CHECK2");
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

    //Sac de sable
   /**enlever le bouton si l'action a deja utilise une fois**/
    public void removeSable(){
        this.remove(sacDeSable);
        this.sable_bouton = false;
        repaint();
        this.doLayout();
    }

    //Helicoptere
    /**enlever les boutons si l'action a deja utilise une fois**/
    public void removeHeli(){
       for(JButton b : heliJoueur){
           this.remove(b);
       }
       heli_joueur = false;

       modele.notifyObservers();
       repaint();
       this.doLayout();

    }

    /**Si l'action est activer, ajouter les boutons
     * pour les joueurs dans le meme zone que joueur courant
     * **/
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

        if(joueurs.size() != 0) {
            //cliquer sur heli_me si courant veut deplacer tout seul meme si il y a des joueurs dans son zone
            this.heliJoueur.add(heli_me);
            this.add(heli_me);
        }

        this.heli_joueur = true;
        modele.notifyObservers();
        repaint();
        this.doLayout();
    }

    /**return true si les boutons des joueurs pour l'action helicoptere est ajoute**/
    public boolean heliJoueurChosen(){return heli_joueur;}

    // Artefacte Commandes
    /**Ajouter le bouton d'artefacet du nom donne en parametre
     * si cet artefact n'est jamais apparu avant**/
    private void addArte(String art){
        if(!artefactes.contains(art)){
            JButton arte = new JButton(art);
            GetArteFact getArt = new GetArteFact(modele,this);
            arte.addMouseListener(getArt);
            arte.setBackground(new Color(255,127,80));
            arte.setOpaque(true);
            artefactes.add(art);
            this.add(arte);
        }
    }

    /**Ajouter les artefacts apparu dans le modele**/
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
    /**Ajouter l'action cleEchange si activeEchanege == true**/
    public void addClesEchange(){
        Joueur courant = this.modele.getJoueurCourant();
        ArrayList<Artefact> clesCourant = courant.mesCles();
        //Si les conditions ne sont pas satisfait
        if(!this.modele.activeEchange()){
            this.resetEchange();
            return;
        }

        if(cle_echange){
            //Si le bouton echange est apparu, un clique surlequel nous dirige a choisir le cle a echanger
            this.remove(echange);
            this.cle_echange = false;
            for (Modele.Artefact cle : clesCourant) {
                this.addEchangeBouton(cle);
            }
            this.cles_bouton = true;
        }else if((courant.getRole() == Joueur.Role.Messageur) && this.cles_bouton){
            //Si joueur courant est messageur et les boutons de cle apparu,
            // on ajouter les boutons pour choisir un joueur a qui on donne le cle
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

    /** Ajouter le JBotton pour echanger les cles que joueurs courant possede. **/
    private void addEchangeBouton(Modele.Artefact cle){
        JButton cleBouton = new JButton(cle.toString().toUpperCase());
        cleBouton.addMouseListener(this.cleEchange);
        cleBouton.setBackground(new Color(255,250,210));
        cleBouton.setOpaque(true);
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
        this.doLayout();
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
        this.add(deplacer);
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