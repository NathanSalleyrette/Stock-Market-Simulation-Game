package Modele;

import Market.Action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class PontBrownien {
    private int nbSim;

    public PontBrownien(int nbSim){
        this.nbSim = nbSim;
    }

    /**
     * Génère toutes les valeurs des 30 tours pour l'action action
     * @param action
     * @param volatilite C'est l'écart type de la génération, plus elle est faible, moins les valeurs varient
     */
    public void  generation(Action action, float volatilite){
        double prec = action.getDefaultValue();
        double curr = 0;
        double gain = 0;

        for(int i = 0; i < nbSim; i++) {
            Random r = new Random();
            double value = action.getLast() + volatilite * r.nextGaussian();
            double val = Math.max(value, (double) 1);
            BigDecimal bd = new BigDecimal(val).setScale(3, RoundingMode.HALF_UP);
            curr = bd.doubleValue();
            action.addValue(curr);
            gain = new BigDecimal(curr - prec).setScale(3, RoundingMode.HALF_UP).doubleValue();
            action.addGain(gain);
            action.addGainPercent((new BigDecimal(gain / prec * 100).setScale(3, RoundingMode.HALF_UP)).doubleValue());
            prec = curr;

        }
    }


}

