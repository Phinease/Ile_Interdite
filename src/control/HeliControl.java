package control;

import modele.Modele;
import vue.Commandes;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class HeliControl extends Controleur {
    protected final Commandes commandes;

    public HeliControl(Modele modele, Commandes commandes) {
        super(modele);
        this.commandes = commandes;
    }

    @Override
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
            default:
                break;
        }

        this.commandes.myJFrame().requestFocus();
        modele.notifyObservers();

    }
}
