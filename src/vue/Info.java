package vue;

import modele.Joueur;
import modele.Modele;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Info extends JPanel implements Observer{
    JTable table;
    String[] names;
    Object[][] data;
    int nbJoueur;
    ArrayList<Joueur> joueurs;
    Modele modele;


    public Info(Modele m){
        modele = m;
        joueurs = modele.getJoueurs();
        nbJoueur = joueurs.size();
        modele.addObserver(this);
        data = new Object[10][nbJoueur+1];
        names = new String[nbJoueur+1];

        names[0] = "INFO";
        data [0][0] = "INFOS";
        data [1][0] = "NB ACT";
        data [2][0] = "AIR";
        data [3][0] = "EAU";
        data [4][0] = "FEU";
        data [5][0] = "TERRE";
        data [6][0] = "CLE AIR";
        data [7][0] = "CLE EAU";
        data [8][0] = "CLE TERRE";
        data [9][0] = "CLE FEU";

        for (int i = 1; i < nbJoueur+1; i++) {
            int j = i-1;
            names[i] = "Joueur " + (j+1);
            data[0][i] = "JOUEUR " + (j+1);
            data[1][i] = 0;
            for (int k = 2; k < 6; k++) {
                data[k][i] = joueurs.get(j).getHasArt(Modele.Artefact.values()[k-2]);
            }
            HashMap<Modele.Artefact,Integer> cles = joueurs.get(j).getCles();
            for (int k = 6; k < 10; k++) {
                data[k][i] = cles.get(Modele.Artefact.values()[k-6]);
            }
        }
        data[1][this.modele.getJoueurCourantIdx()+1] = this.modele.getJoueurCourant().restNbr();

        table = new JTable(data,names);
        table.setFont(new Font("Verdana", Font.BOLD, 12));
        this.add(table);
        table.setRowHeight(40);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setOpaque(false);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
        table.setShowGrid(false);
    }

    @Override
    public void update() {
        for (int i = 1; i < nbJoueur+1; i++) {
            int j = i-1;
            data[1][i] = 0;
            for (int k = 2; k < 6; k++) {
                data[k][i] = joueurs.get(j).getHasArt(Modele.Artefact.values()[k-2]);
            }
            HashMap<Modele.Artefact,Integer> cles = joueurs.get(j).getCles();
            for (int k = 6; k < 10; k++) {
                data[k][i] = cles.get(Modele.Artefact.values()[k-6]);
            }
        }
        data[1][this.modele.getJoueurCourantIdx()+1] = this.modele.getJoueurCourant().restNbr();
        table.updateUI();
        repaint();
    }

    @Override
    public void end() {}
}


