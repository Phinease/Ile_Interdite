package control;

import modele.Joueur;
import modele.Modele;
import vue.Commandes;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Assecher implements MouseListener {
    protected final Modele modele;
    protected final Commandes commandes;
    private int nbrAss = 0;

    public Assecher(Modele m, Commandes c){
        this.modele = m;
        this.commandes = c;
    }

    @Override
    // Ajouter ou enlever des boutons ainsi que activer l'action assecher du zones choisi
    public void mouseClicked(MouseEvent e) {
        if(commandes.getAssBool()){
            commandes.addAssecher();
            if(modele.getJoueurCourant().getRole() == Joueur.Role.Explorateur){
                commandes.addExAss();
            }
            modele.notifyObservers();
        }else {
            boolean isIngenieur = (this.modele.getJoueurCourant().getRole() == Joueur.Role.Ingenieur);
            JButton b = (JButton)e.getSource();

            boolean asse = false;
            switch (b.getText()){
                case "UP":
                    asse = this.modele.assecher(KeyControl.Direction.up);
                    this.nbrAss++;
                    break;
                case "DOWN":
                    asse = this.modele.assecher(KeyControl.Direction.down);
                    this.nbrAss++;
                    break;
                case "LEFT":
                    asse = this.modele.assecher(KeyControl.Direction.left);
                    this.nbrAss++;
                    break;
                case "RIGHT":
                    asse = this.modele.assecher(KeyControl.Direction.right);
                    this.nbrAss++;
                    break;
                case "MID":
                    asse = this.modele.assecher(KeyControl.Direction.mid);
                    this.nbrAss++;
                    break;
                case "CANCEL":
                    this.nbrAss++;
                    break;
            }
            if(!isIngenieur || (this.nbrAss >1)){
                commandes.resetAssecher();
                this.nbrAss = 0;
                if(asse) {
                    this.modele.getJoueurCourant().prioriteInge();
                }
            }
            if(modele.getJoueurCourant().getRole() == Joueur.Role.Explorateur){
                commandes.resetExAss();
            }
            modele.notifyObservers();
        }
        this.commandes.myJFrame().requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }


}

