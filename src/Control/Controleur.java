package Control;

import Modele.Modele;
import control.ZoneCliquable;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Color;

// Phinease： 我需要一个KeyListener 给joueur移动（上下左右）用和N,M键，且区分鼠标和键盘的检测

public class Controleur implements MouseListener {
    private Modele modele;
    public Controleur(Modele modele) {
        this.modele = modele;
    }


    public void clicGauche(ZoneCliquable z) {
        if(z instanceof FindeTour){
            z.clicGauche();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            this.clicGauche();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}

class FindeTour extends ZoneCliquable {
    private Modele modele;

    public FindeTour(int x, int y, Modele m){
        super("Fin de Tour",x,y);
        this.modele = m;
    }

    @Override
    public void clicGauche() {
        this.modele.inondationAlea(3);
    }

    @Override
    public void clicDroit() {}
}
