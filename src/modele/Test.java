package Modele;

import Vue.Vue;

import java.awt.*;

public class Test {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Modele modele = new Modele();
            Vue vue = new Vue(modele);
        });
    }
}
