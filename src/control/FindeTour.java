package control;

import modele.Modele;
import vue.Commandes;

import java.awt.event.MouseEvent;

public class FindeTour extends Controleur  {
    private final Commandes commandes;

    public FindeTour(Modele m,Commandes c){
        super(m);
        commandes = c;
    }

    @Override
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

