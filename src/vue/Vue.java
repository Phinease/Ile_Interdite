package vue;

import control.KeyControl;
import control.SableControl;
import modele.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Vue extends JFrame implements Observer{
    private final Grille grille;
    public Commandes commandes;
    private final Info info;

    public Vue(Modele modele) throws IOException {
        modele.addObserver(this);
        this.setTitle("L'île interdite");
        this.setLayout(new BorderLayout());

        KeyControl kc = new KeyControl(modele);

        commandes = new Commandes(modele,kc,this);
        this.add(commandes,BorderLayout.NORTH);

        grille = new Grille(modele,commandes);
        this.add(grille,BorderLayout.CENTER);

        info = new Info(modele);
        this.add(info,BorderLayout.EAST);
        this.addKeyListener(kc);
        this.grille.addKeyListener(kc);
        this.commandes.addKeyListener(kc);


        this.setPreferredSize(new Dimension(1010,850));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public void endGameScreen() {
        this.remove(grille);
        this.remove(info);
        this.remove(commandes);

        JLabel label = new JLabel("GAME OVER");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        this.getContentPane().add(label);
        this.repaint();

        try {
            Clip clip_dead = AudioSystem.getClip();
            AudioInputStream ais_dead = AudioSystem.getAudioInputStream(new File("materials/dead.wav"));
            clip_dead.open(ais_dead);
            clip_dead.loop(0);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update() {}
    @Override
    public void end() {
        endGameScreen();
    }
}


class Grille extends JPanel implements MouseListener, NaObserver {
    private final Modele modele;
    private final static int TAILLE = 30;
    private boolean joueurChoisi = false;
    private Zone posChoisi;
    private final Commandes commandes;

    private BufferedImage terre;
    private BufferedImage eau;
    private BufferedImage heli;
    private BufferedImage air;
    private BufferedImage feu;
    private BufferedImage innonde;
    private BufferedImage submerge;

    private BufferedImage joueur1;
    private BufferedImage joueur2;
    private BufferedImage joueur3;
    private BufferedImage joueur4;

    private BufferedImage artefact1;
    private BufferedImage artefact2;
    private BufferedImage artefact3;
    private BufferedImage artefact4;


    Grille(Modele m, Commandes commandes) throws IOException {
        modele = m;
        modele.addNaObserver(this);
        this.commandes =commandes;

        Dimension dimension = new Dimension(
                TAILLE * Modele.LARGEUR, TAILLE * (Modele.HAUTEUR + 5));
        this.setPreferredSize(dimension);
        addMouseListener(this);
        this.setFont(new Font("Verdana", Font.BOLD, 12));

        init();
    }

    private void init() throws IOException {
        terre = ImageIO.read(new File("materials/terre.png"));
        eau = ImageIO.read(new File("materials/water.png"));
        heli = ImageIO.read(new File("materials/heli.png"));
        air = ImageIO.read(new File("materials/air.png"));
        feu = ImageIO.read(new File("materials/feu.png"));
        joueur1 = ImageIO.read(new File("materials/joueur1.png"));
        joueur2 = ImageIO.read(new File("materials/joueur2.png"));
        joueur3 = ImageIO.read(new File("materials/joueur3.png"));
        joueur4 = ImageIO.read(new File("materials/joueur4.png"));
        innonde = ImageIO.read(new File("materials/innonde.png"));
        submerge = ImageIO.read(new File("materials/submerge.png"));
        artefact1 = ImageIO.read(new File("materials/artefact1.png"));
        artefact2 = ImageIO.read(new File("materials/artefact2.png"));
        artefact3 = ImageIO.read(new File("materials/artefact3.png"));
        artefact4 = ImageIO.read(new File("materials/artefact4.png"));
    }

    @Override
    public void na() {}
    @Override
    public void showNaJoueur() {}
    @Override
    public void cleanNaJoueur() {}
    @Override
    public void overNa() {}
    @Override
    public void chosenNaJoueur(Joueur.Jou j) {
        Zone pos = modele.getJoueurs().get(0).getPosition();;
        switch (j){
            case j1:
                pos = modele.getJoueurs().get(0).getPosition();
                break;
            case j2:
                pos = modele.getJoueurs().get(1).getPosition();
                break;
            case j3:
                pos = modele.getJoueurs().get(2).getPosition();
                break;
            case j4:
                pos = modele.getJoueurs().get(3).getPosition();
                break;
        }
        joueurChoisi = true;
        posChoisi = pos;
    }

    public void paintComponent(Graphics g) {
        super.repaint();
        for (int i = 1; i <= Modele.LARGEUR; i++) {
            for (int j = 1; j <= Modele.HAUTEUR; j++) {
                paint(g, modele.getZone(i, j), (i - 1) * TAILLE, (j - 1) * TAILLE);
            }
        }

        ArrayList<Joueur> list_joueur = modele.getJoueurs();
        for (int i = 0; i < list_joueur.size(); i++) {
            Zone pos = list_joueur.get(i).getPosition();
            paintJoueur(g, i, (pos.getX() - 1) * TAILLE + 1, (pos.getY() - 1) * TAILLE + 1);
        }

        Zone pos = modele.getJoueurCourant().getPosition();
        paintJoueurCourant(g, (pos.getX() - 1) * TAILLE + 2, (pos.getY() - 1) * TAILLE + 2);

        if(joueurChoisi){
            paintJoueurChoisi(g, (posChoisi.getX() - 1) * TAILLE + 2, (posChoisi.getY() - 1) * TAILLE + 2);
        }

        g.drawImage(heli, (Modele.heli - 1) * TAILLE, (Modele.heli - 1) * TAILLE, this);
        paintAnnexe(g);
    }

    private void paintAnnexe(Graphics g) {
        String text;
        BufferedImage[] zones = {air, eau, terre, feu};
        g.setColor(Color.BLACK);
        for (int i = 0; i < 4; i++) {
            g.drawImage(zones[i], TAILLE * (1 + i * 5), (Modele.HAUTEUR + 1) * TAILLE, this);
            text = "Zone " + Modele.Artefact.values()[i].toString().toUpperCase();
            g.drawString(text, TAILLE * (2 + i * 5) + 10, (Modele.HAUTEUR + 1) * TAILLE + 20);
        }

        BufferedImage[] joueurs = {joueur1, joueur2, joueur3, joueur4};
        for (int i = 0; i < 4; i++) {
            g.drawImage(joueurs[i], TAILLE * (1 + i * 5), (Modele.HAUTEUR + 2) * TAILLE + 5, this);
            text = modele.getJoueurs().get(i).toString() + " " + (i+1);
            g.drawString(text, TAILLE * (2 + i * 5) + 10, (Modele.HAUTEUR + 2) * TAILLE + 25);
        }

        BufferedImage[] artefacts = {artefact1, artefact2, artefact3, artefact4};
        for (int i = 0; i < 4; i++) {
            g.drawImage(artefacts[i], TAILLE * (1 + i * 5), (Modele.HAUTEUR + 3) * TAILLE + 10, this);
            text = "Artf " + Modele.Artefact.values()[i].toString().toUpperCase();
            g.drawString(text, TAILLE * (2 + i * 5) + 10, (Modele.HAUTEUR + 3) * TAILLE + 30);
        }

        g.drawImage(innonde, TAILLE, (Modele.HAUTEUR + 4) * TAILLE + 15, this);
        text = "Innonde";
        g.drawString(text, TAILLE * 2 + 10, (Modele.HAUTEUR + 4) * TAILLE + 35);

        g.drawImage(submerge, TAILLE * 6, (Modele.HAUTEUR + 4) * TAILLE + 15, this);
        text = "Submerge";
        g.drawString(text, TAILLE * 7 + 10, (Modele.HAUTEUR + 4) * TAILLE + 35);
    }

    private void paintJoueurCourant(Graphics g, int x, int y) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x + 1, y + 1, 8, 8);
    }

    private void paintJoueurChoisi(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.fillRect(x + 1, y + 1, 8, 8);
    }

    private void paintJoueur(Graphics g, int i, int x, int y) {
        switch (i) {
            case 0:
                g.drawImage(joueur1, x, y, this);
                return;
            case 1:
                g.drawImage(joueur2, x, y, this);
                return;
            case 2:
                g.drawImage(joueur3, x, y, this);
                return;
            case 3:
                g.drawImage(joueur4, x, y, this);
                return;
            default:
        }
    }

    private void paint(Graphics g, Zone c, int x, int y) {
        switch (c.getType()) {
            case air:
                g.drawImage(air, x, y, this);
                break;
            case eau:
                g.drawImage(eau, x, y, this);
                break;
            case feu:
                g.drawImage(feu, x, y, this);
                break;
            case terre:
                g.drawImage(terre, x, y, this);
                break;
            default:
                break;
        }
        switch (c.getStatus()) {
            case 1:
                g.drawImage(innonde, x, y, this);
                break;
            case 2:
                g.drawImage(submerge, x, y, this);
                break;
            default:
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            int x = e.getX()/TAILLE+1;
            int y = e.getY()/TAILLE+1;
            if(modele.getJoueurCourant().getRole() == Joueur.Role.Pilote){
                modele.deplacePilote(this.modele.getZone(x,y));
            }
            if(modele.getJoueurCourant().getRole() == Joueur.Role.Navigateur){
                modele.deplaceNavi(this.modele.getZone(x,y));
            }
            joueurChoisi = false;

            if(this.modele.getSable()){
                if(this.modele.assecherSable(x,y)){
                    this.commandes.removeSable();
                }
            }

            if(this.modele.getHeli()&&this.commandes.heliJoueurChosen()){
                if(this.modele.deplaceHeli(x,y)){
                    this.commandes.removeHeli();
                }
            }
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}





