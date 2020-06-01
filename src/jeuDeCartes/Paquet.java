package jeuDeCartes;

import modele.Modele;
import java.util.ArrayList;
import java.util.Random;
import jeuDeCartes.CarteZone;
import modele.Zone;

//pqauet carteZone
public class Paquet {
    private int nb=0;//抽了几张牌
    ArrayList<CarteZone>cartes;

    public Paquet(Zone[][] zones,int LARGEUR,int HAUTEUR) {
        this.cartes = new ArrayList<>();
        for (int i = 0; i <= LARGEUR; i++) {
            for (int j = 0; j <= HAUTEUR; j++) {
                cartes.add(new CarteZone(zones[i][j]));
            }
        }
    }
    public void melanger(){//洗牌
        ArrayList<CarteZone> newCartes=new ArrayList<CarteZone>();
        while(newCartes.size()<cartes.size()){
            Random random=new Random();
            CarteZone c=cartes.get(random.nextInt(cartes.size()));
            if(!newCartes.contains(c)){
                newCartes.add(c);
            }
        }
        this.cartes=newCartes;
    }
    public CarteZone tirer(){//抽出第一张 牌放到defausse 返回一个要被阉掉的zone
        CarteZone get;
        if(this.nb<this.cartes.size()-1){
            get=this.cartes.get(nb);
            this.nb++;
        }
        else{//只剩下最后一张，取走最后一张并洗牌
            get=this.cartes.get(this.cartes.size()-1);
            this.melanger();
            this.nb=0;
        }
        return get;
    }

    public static void main(String[] args) {
        Modele modele =new Modele();
        Paquet p=new Paquet(modele.getZones(),modele.LARGEUR,modele.HAUTEUR);
        p.melanger();
        CarteZone z=p.tirer();
        System.out.println(z.getX()+" "+z.getY());
    }
}

