package vue;

import modele.Joueur;

interface NaObserver {
    void na();
    void showNaJoueur();
    void cleanNaJoueur();
    void chosenNaJoueur(Joueur.Jou j);
    void overNa();
}
