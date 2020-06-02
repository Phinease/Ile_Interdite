package control;

import modele.Modele;
import vue.Commandes;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HeliControl implements MouseListener {
    private final Modele modele;
    protected final Commandes commandes;

    public HeliControl(Modele modele, Commandes commandes) {
        this.modele = modele;
        this.commandes = commandes;
    }

    @Override
    /**active l'action helicoptere et choisir un joueur sur le meme zone que joueur courant
     * pour deplacer ensemble avec courant apres**/
    public void mouseClicked(MouseEvent e) {
        this.modele.activeHeli();

        JButton b = (JButton)e.getSource();
        switch (b.getText()) {
            case "HELICOPTERE":
                this.commandes.heliJoueurBouton();
                break;
            case "AVEC JOUEUR 1":
                this.modele.addHeliJoueur(0);
                break;
            case "AVEC JOUEUR 2":
                this.modele.addHeliJoueur(1);
                break;
            case "AVEC JOUEUR 3":
                this.modele.addHeliJoueur(2);
                break;
            case "AVEC JOUEUR 4":
                this.modele.addHeliJoueur(3);
                break;
            case "JUST ME" :
                break;
            default:
                break;
        }

        this.commandes.myJFrame().requestFocus();
        modele.notifyObservers();

    }

    @Override
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}
