package jeuDeCartes;

import modele.Modele;

public class CarteCle {
    public enum CarteCleType{Find,noFind,water}
    private CarteCleType type;
    public CarteCle(int t){
        switch (t){
            case 0:
                this.type=CarteCleType.Find;
                break;
            case 1:
                this.type=CarteCleType.noFind;
                break;
            case 2:
                this.type=CarteCleType.water;
        }
    }

    public CarteCleType getType() {
        return type;
    }
}
