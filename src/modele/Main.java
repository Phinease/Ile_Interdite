package modele;

import vue.Vue;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Modele modele = new Modele();
            try {
                Vue vue = new Vue(modele);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
