package control;

import modele.Joueur;
import modele.Modele;
import modele.Zone;
import vue.Commandes;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SableControl extends Assecher {

    public SableControl(Modele m, Commandes c) {
        super(m,c);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        this.modele.activeSable();
        this.commandes.myJFrame().requestFocus();
        modele.notifyObservers();

    }
}
