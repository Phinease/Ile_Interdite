package control;

import modele.Modele;
import modele.Modele.Artefact;
import vue.Commandes;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class GetArteFact extends Controleur {
    private Commandes commandes;

        public GetArteFact(Modele modele,Commandes commandes) {
            super(modele);
            this.commandes = commandes;
        }

//30/05
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

}
