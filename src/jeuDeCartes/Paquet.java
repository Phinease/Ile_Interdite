package jeuDeCartes;

import modele.Modele;
import modele.Zone;

import java.util.ArrayList;
import java.util.Random;

//pqauet carteZone
public class Paquet {
    private Modele modele;
    private int nb=0;//抽了几张牌
    CarteZone[] cartes;
    ArrayList<CarteZone>defausse;
    public Paquet(Modele modele){
        this.modele=modele;
        cartes=new CarteZone[400];
        this.defausse=new ArrayList<>();
        for (int i=0;i<this.modele.LARGEUR;i++){
            for(int j=0;j<this.modele.HAUTEUR;j++){
                cartes[i+j]=new CarteZone(this.modele.getZone(i,j));
            }
        }
    }
    public void melanger(){//洗牌
        Random random=new Random(400);
        int index;
        ArrayList<Integer>listindex=new ArrayList<>();
        while(listindex.size()!=400){
            do{
                index=random.nextInt();
            }while(!listindex.contains(index));
        }
        CarteZone[]newCartes=new CarteZone[400];
        for(int i=0;i<400;i++){
            newCartes[i]=this.cartes[listindex.get(i)];
        }
        this.cartes=newCartes;
    }
    public Zone Tirer(){//抽出第一张 牌放到defausse 返回一个要被阉掉的zone
        Zone get;
        if(this.nb<400){
            this.nb++;
            get=this.cartes[nb-1].getZone();
        }
        else{//取完了所有
            this.nb=0;
            get=this.cartes[399].getZone();
            this.melanger();
        }
        return get;
    }

    public static void main(String[] args) {
        Modele modele =new Modele();
        Paquet p=new Paquet(modele);
        p.melanger();
        Zone z = p.Tirer();
        System.out.println(z.getX()+" "+z.getY());
    }
}

