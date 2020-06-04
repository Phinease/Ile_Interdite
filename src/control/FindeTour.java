package control;

import modele.Modele;
import vue.Commandes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FindeTour implements MouseListener {
    private final Modele modele;
    private final Commandes commandes;

    public FindeTour(Modele m,Commandes c){
        this.modele = m;
        commandes = c;
    }

    @Override
    // reset et passer au joueur suivant ainsi que inonder 3 zones par le paquet de carte
    public void mouseClicked(MouseEvent e) {
        this.modele.findeTour(3);
        commandes.addArtefacts();
        commandes.reset();
        this.commandes.myJFrame().requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

