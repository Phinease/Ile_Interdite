package vue;

import modele.Joueur;

import java.util.ArrayList;

public abstract class Observable {
    private final ArrayList<Observer> observers;
    private final ArrayList<ExObserver> exObservers;
    private final ArrayList<NaObserver> naObservers;
   // private final ArrayList<MeObserver> meObservers;


    public Observable() {
        observers = new ArrayList<>();
        exObservers = new ArrayList<>();
        naObservers = new ArrayList<>();
       // meObservers = new ArrayList<>();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void addExObserver(ExObserver o) {exObservers.add(o);}

    public void addNaObserver(NaObserver o) {naObservers.add(o);}

    //public void addMeObserver(MeObserver o) {meObservers.add(o);}

    public void notifyObservers() {
        for(Observer o : observers) {
            o.update();
        }
    }

    public void checkEnd(Boolean dead){
        if(dead) {
            for (Observer o : observers) {
                o.end();
            }
        }
    }

    public void nextEx(){
        for (ExObserver o : exObservers) {
            o.ex();
        }
    }

    public void overEx(){
        for (ExObserver o : exObservers) {
            o.overEx();
        }
    }

    public void nextNa(){
        for (NaObserver o : naObservers) {
            o.na();
        }
    }

    public void overNa(){
        for (NaObserver o : naObservers) {
            o.overNa();
        }
    }

    public void cleanAss(){
        for (ExObserver o : exObservers) {
            o.cleanAss();
        }
    }

    public void deplace(){
        for (NaObserver o : naObservers) {
            o.showNaJoueur();
        }
    }

    public void cleanJoueur(){
        for (NaObserver o : naObservers) {
            o.cleanNaJoueur();
        }
    }

    public void chosen(Joueur.Jou j){
        for (NaObserver o : naObservers) {
            o.chosenNaJoueur(j);
        }
    }
/*
    public void nextMe(){
        for (MeObserver o : meObservers) {
            o.me();
        }
    }

    public void overMe(){
        for (MeObserver o : meObservers) {
            o.overMe();
        }
    }

 */


}
