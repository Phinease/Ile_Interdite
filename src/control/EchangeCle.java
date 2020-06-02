package control;

import modele.Joueur;
import modele.Modele;
import vue.Commandes;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EchangeCle implements MouseListener {
    private final Modele modele;
    private final Commandes commandes;
    private Modele.Artefact cleChoson = Modele.Artefact.normal;

    public EchangeCle(Modele modele,Commandes commandes) {
        this.modele = modele;
        this.commandes = commandes;
    }

    /**Ajouter ou enlever des boutons ainsi que activer l'action echange cle
     * choisir le joueur pour donner le cle si le joueur courant est messageur**/
    public void mouseClicked(MouseEvent e) {
        JButton b = (JButton)e.getSource();
        if(commandes.getEchange()){
            commandes.addClesEchange();
            modele.notifyObservers();
        }else {
            boolean isMessenger = (this.modele.getJoueurCourant().getRole() == Joueur.Role.Messageur);
            switch (b.getText()) {
                case "AIR":
                    if (isMessenger) {
                        this.cleChoson = Modele.Artefact.air;
                    } else {
                        this.modele.cleEchange(Modele.Artefact.air);
                    }
                    break;
                case "EAU":
                    if (isMessenger) {
                        this.cleChoson = Modele.Artefact.eau;
                    } else {
                        this.modele.cleEchange(Modele.Artefact.eau);
                    }
                    break;
                case "TERRE":
                    if (isMessenger) {
                        this.cleChoson = Modele.Artefact.terre;
                    } else {
                        this.modele.cleEchange(Modele.Artefact.terre);
                    }
                    break;
                case "FEU":
                    if (isMessenger) {
                        this.cleChoson = Modele.Artefact.feu;
                    }else {
                        this.modele.cleEchange(Modele.Artefact.feu);
                    }
                    break;
                case "GIVE JOUEUR 1":
                    this.modele.cleEchangeMessageur(0,this.cleChoson);
                    break;
                case "GIVE JOUEUR 2":
                    this.modele.cleEchangeMessageur(1,this.cleChoson);
                    break;
                case "GIVE JOUEUR 3":
                    this.modele.cleEchangeMessageur(2,this.cleChoson);
                    break;
                case "GIVE JOUEUR 4":
                    this.modele.cleEchangeMessageur(3,this.cleChoson);
                    break;
                default:
                    break;
            }
            commandes.addClesEchange();
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
