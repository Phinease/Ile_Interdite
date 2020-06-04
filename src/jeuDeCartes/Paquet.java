package jeuDeCartes;

import java.util.ArrayList;
import java.util.Random;
import modele.Zone;

//pqauet carteZone
public class Paquet {
    private int nb = 0;//combien de cartes est tirée
    ArrayList<CarteZone> cartes;

    public Paquet(Zone[][] zones,int LARGEUR,int HAUTEUR) {
        this.cartes = new ArrayList<>();
        for (int i = 0; i <= LARGEUR; i++) {
            for (int j = 0; j <= HAUTEUR; j++) {
                cartes.add(new CarteZone(zones[i][j]));
            }
        }
    }

    /* mélanger le paquet */
    public void melanger(){
        ArrayList<CarteZone> newCartes=new ArrayList<>();
        while(newCartes.size()<cartes.size()){
            Random random=new Random();
            CarteZone c=cartes.get(random.nextInt(cartes.size()));
            if(!newCartes.contains(c)){
                newCartes.add(c);
            }
        }
        System.out.println("Paquet Inondee a bien melangé ！");
        this.cartes=newCartes;
    }

    /* Objet:Chaque fois tirer le premier carte de paquet et mettre dans la defausse
    * Realiser: 'nb'est represente combien de cartes est tirée alors la 'nb'-ième carte est la premier carte de paquet
    *           quand les joueurs ont déjà tiré 'nb' fois le carte
    *           Apres melanger le paquet, tirer le 'nb'-ième carte,
    *           si c'est la derniere carte de paquet, remettre le 'nb'=0 et melanger le paquet  */
    public CarteZone tirer(){
        CarteZone get;
        if(this.nb<this.cartes.size()-1){
            get=this.cartes.get(nb);
            this.nb++;
        }
        else{
            get=this.cartes.get(this.cartes.size()-1);
            this.melanger();
            this.nb=0;
        }
        System.out.println("Tirer carte: Inondee x="+get.getX()+" y= "+get.getY());
        return get;
    }

//tester
//    public static void main(String[] args) {
//        Modele modele =new Modele();
//        Paquet p=new Paquet(modele.getZones(), Modele.LARGEUR, Modele.HAUTEUR);
//        p.melanger();
//        CarteZone z=p.tirer();
//        System.out.println(z.getX()+" "+z.getY());
//    }
}

