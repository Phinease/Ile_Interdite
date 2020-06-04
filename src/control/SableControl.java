package control;

import modele.Modele;
import vue.Commandes;

import java.awt.event.MouseEvent;

public class SableControl extends Assecher {

    public SableControl(Modele m, Commandes c) {
        super(m,c);
    }

    @Override
    // Active l'action sac de sable
    public void mouseClicked(MouseEvent e) {
        this.modele.activeSable();
        this.commandes.myJFrame().requestFocus();
        modele.notifyObservers();
    }
}
