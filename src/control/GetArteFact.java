package control;

import modele.Modele;
import modele.Modele.Artefact;
import vue.Commandes;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GetArteFact implements MouseListener {
    private final Modele modele;
    private final Commandes commandes;

    public GetArteFact(Modele modele,Commandes commandes) {
        this.modele = modele;
        this.commandes = commandes;
    }

    // recupere artefacte et enleve le bouton
    public void mouseClicked(MouseEvent e) {
        JButton b = (JButton)e.getSource();
        boolean get = false;
        switch (b.getText()) {
            case "GET AIR":
                get = this.modele.getJoueurCourant().getArteFact(Artefact.air);
                break;
            case "GET EAU":
                get = this.modele.getJoueurCourant().getArteFact(Artefact.eau);
                break;
            case "GET TERRE":
                get = this.modele.getJoueurCourant().getArteFact(Artefact.terre);
                break;
            case "GET FEU":
                get = this.modele.getJoueurCourant().getArteFact(Artefact.feu);
                break;
            default:
                break;
        }
        if (get) { this.commandes.remove(b); }
        modele.notifyObservers();
        this.commandes.myJFrame().requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

}
