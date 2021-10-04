package Market;

// C'est l'entitée marché, celle qui gère les tours, les valeurs des actions,
// l'actualisation des tours etc.

import java.util.HashMap;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.Map;

public class Market {
    private int lap = 0;
    private int maxLap;
    private LinkedList<Action> marche;

    public Market(int maxLap, LinkedList<Action> actions){
        this.maxLap = maxLap;
        marche = actions;
    }

    public void updateLap() {
        if (this.lap < this.maxLap){
            lap++;
            for(Action action: marche){
                action.setPrice(action.getVal(lap));
                action.setGain(lap);
            }

        }

    }

    public LinkedList<Action> getMarket(){return marche;}

    public double getValueAction(int iDAction) {
        return marche.get(iDAction).getPrice();
    }

    public void addAction(Action act){
        marche.add(act);
    }

    public int getLap(){
        return lap;
    }

    @Override
    public String toString() {
        String str ="";
        for (Action act: marche){
            str += act.toString();
        }
        return str;
    }


}
