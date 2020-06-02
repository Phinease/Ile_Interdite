package jeuDeCartes;

import java.util.ArrayList;
import java.util.Random;

public class PaquetCle {
    private int nb=0;
    ArrayList<CarteCle> cartes=new ArrayList<>();
    public PaquetCle(){

        for(int i=0;i<100;i++){
            if(i<30) {
                this.cartes.add(new CarteCle(0));
            }else if(i<90){
                this.cartes.add(new CarteCle(1));
            }else{
                this.cartes.add(new CarteCle(2));
            }
        }
        ArrayList<CarteCle> newCartes=new ArrayList<>();
        while(newCartes.size()<this.cartes.size()){
            Random random=new Random();
            CarteCle c=this.cartes.get(random.nextInt(cartes.size()));
            if(!newCartes.contains(c)){
                newCartes.add(c);
            }
        }
        this.cartes=newCartes;
    }
    public void melanger(){
        ArrayList<CarteCle> newCartes=new ArrayList<>();
        while(newCartes.size()<this.cartes.size()){
            Random random=new Random();
            CarteCle c=this.cartes.get(random.nextInt(cartes.size()));
            if(!newCartes.contains(c)){
                newCartes.add(c);
            }
        }
        System.out.println("Paquet Cle a bien melangé ！");
        this.cartes=newCartes;
    }
    public CarteCle tirer(){
        CarteCle get;
        if(this.nb<this.cartes.size()-1){
            get=this.cartes.get(nb);
            this.nb++;
        }
        else{
            get=this.cartes.get(this.cartes.size()-1);
            this.melanger();
            this.nb=0;
        }
        System.out.println("Tirer: resultat est "+get.getType());
        return get;
    }

    public static void main(String[] args) {
        PaquetCle p=new PaquetCle();
        p.melanger();
        CarteCle z=p.tirer();
        System.out.println(z.getType());
    }
}