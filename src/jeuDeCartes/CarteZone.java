package jeuDeCartes;

import modele.Modele;
import modele.Zone;

public class CarteZone {
    private Zone zone;
    public CarteZone(Zone zone){
        this.zone=zone;
    }

    public Zone getZone() {
        return zone;
    }
}
