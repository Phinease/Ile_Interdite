package jeuDeCartes;

import modele.Modele;

public class CarteCle {
    public enum CarteCleType{Find,noFind,water}
    private CarteCleType type;
    private Modele modele;
    public CarteCle(CarteCleType type,Modele modele){
        this.modele=modele;
        this.type=type;
    }
}
