package control;

import modele.Joueur;
import modele.Modele;
import vue.Commandes;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExControl implements MouseListener {
    private Modele modele;
    private Commandes commandes;

    public ExControl(Modele m,Commandes cm){
        modele = m;
        this.commandes = cm;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton b = (JButton)e.getSource();
        switch (b.getText()){
            case "UP RIGHT":
                modele.moveExplorateur(Joueur.DirectionEx.rightup);
                break;
            case "UP LEFT":
                modele.moveExplorateur(Joueur.DirectionEx.leftup);
                break;
            case "DOWN RIGHT":
                modele.moveExplorateur(Joueur.DirectionEx.rightdown);
                break;
            case "DOWN LEFT":
                modele.moveExplorateur(Joueur.DirectionEx.leftdown);
                break;
            case "UR":
                modele.assecherEx(Joueur.DirectionEx.rightup);
                break;
            case "DR":
                modele.assecherEx(Joueur.DirectionEx.leftup);
                break;
            case "UL":
                modele.assecherEx(Joueur.DirectionEx.rightdown);
                break;
            case "DL":
                modele.assecherEx(Joueur.DirectionEx.leftdown);
                break;
            case "CANCEL":
                break;
        }
        this.commandes.myJFrame().requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
