package control;

import modele.Modele;
import java.awt.event.*;


public class Controleur implements MouseListener {
    protected Modele modele;

    public Controleur(Modele modele) {
        this.modele = modele;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       this.modele.findeTour(3);
    }


    @Override
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}


