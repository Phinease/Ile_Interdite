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

    /* Melanger le paquet */
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

    /* Objet:Chaque fois tirer le premier carte de paquet et mettre dans la defausse
     * Realiser: 'nb'est represente combien de cartes est tirée alors la 'nb'-ième carte est la premier carte de paquet
     *           quand les joueurs ont déjà tiré 'nb' fois le carte
     *           Apres melanger le paquet, tirer le 'nb'-ième carte,
     *           si c'est la derniere carte de paquet, remettre le 'nb'=0 et melanger le paquet  */
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

//tester
//    public static void main(String[] args) {
//        PaquetCle p=new PaquetCle();
//        p.melanger();
//        CarteCle z=p.tirer();
//        System.out.println(z.getType());
//    }
}
