package modele;

abstract class Etat{
    abstract public int getStatus();

}

class Etat_Normal extends Etat{
    @Override
    public int getStatus() {
        return 0;
    }
}

class Etat_Inondee extends Etat{
    @Override
    public int getStatus() {
        return 1;
    }
}

class Etat_Submerge extends Etat{
    @Override
    public int getStatus() {
        return 2;
    }
}