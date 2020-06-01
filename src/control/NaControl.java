package control;

import modele.Joueur;
import modele.Modele;
import vue.Commandes;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NaControl implements MouseListener {
    Modele modele;
    private Commandes commandes;

    public NaControl(Modele m, Commandes commandes) {
        modele = m;
        this.commandes = commandes;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton b = (JButton)e.getSource();
        switch (b.getText()){
            case "DEPLACER":
                modele.deplace();
                break;
            case "JOUEUR 1":
                modele.choisirNa(Joueur.Jou.j1);
                break;
            case "JOUEUR 2":
                modele.choisirNa(Joueur.Jou.j2);
                break;
            case "JOUEUR 3":
                modele.choisirNa(Joueur.Jou.j3);
                break;
            case "JOUEUR 4":
                modele.choisirNa(Joueur.Jou.j4);
                break;
            default:
                break;
        }
        this.commandes.myJFrame().requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
