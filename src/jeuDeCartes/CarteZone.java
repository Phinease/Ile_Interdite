package jeuDeCartes;


import modele.Zone;

public class CarteZone {
    private final int x;
    private final int y;
    public CarteZone(Zone zone){
        this.x = zone.getX();
        this.y = zone.getY();
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
