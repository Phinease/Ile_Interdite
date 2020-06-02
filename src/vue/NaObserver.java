package vue;

import modele.Joueur;

interface NaObserver {
    // Observer pour le navigateur
    void na();
    void showNaJoueur();
    void cleanNaJoueur();
    void chosenNaJoueur(Joueur.Jou j);
    void overNa();
}
