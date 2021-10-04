package Market;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

public class Action {
    private int iD;
    private String name;

    //Les listes contiennent toutes les valeurs des 30 tours
    private LinkedList<Double> listOfValues;
    private LinkedList<Double> listOfGain;
    private LinkedList<Double> listOfGainPercent;

    // Variable qui contiennent les valeurs du tour présent
    private double price;
    private int quantity;
    private double gain;
    private double gainPercent;

    public Action(int iD, String name, double defaultValue) {
        this.iD = iD;
        this.name = name;
        listOfValues = new LinkedList<Double>();
        listOfValues.add(defaultValue);
        listOfGain = new LinkedList<Double>();
        listOfGain.add((double) 0);
        listOfGainPercent = new LinkedList<Double>();
        listOfGainPercent.add((double) 0);

        price = listOfValues.getFirst();
        quantity = 100;
        gain = 0;

    }

    /**
     * On calcul le gain total depuis le tour 0
     * @param lap tour actuel
     * @return
     */
    public double[] calcTotalGain(int lap){
        double[] result = new double[2];
        if (lap > 0){
            double init = listOfValues.getFirst();
            double curr = listOfValues.get(lap);
            double gainTot = (new BigDecimal(curr - init).setScale(3, RoundingMode.HALF_UP)).doubleValue();
            double gainPercentTot = (new BigDecimal(gainTot / init * 100).setScale(3, RoundingMode.HALF_UP)).doubleValue();
            result[0] = gainTot;
            result[1] = gainPercentTot;

        }
        return result;

    }

    //Getter

    public int getID() {
        return iD;
    }

    public String getName() {
        return name;
    }

    public double getGain() {
        return gain;
    }

    public double getGainPercent() {return gainPercent;}



    public void addValue(double value) {
        listOfValues.add(value);
    }

    public void addGain(double gain) { listOfGain.add(gain);}

    public void addGainPercent(double gainPercent) {listOfGainPercent.add(gainPercent);}


    public double getLast() {
        return listOfValues.getLast();
    }

    public double getVal(int i) {
        return listOfValues.get(i);
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDefaultValue() {return listOfValues.getFirst();}

    //Setter
    public void setGain(int lap) {
        gain = listOfGain.get(lap);
        gainPercent = listOfGainPercent.get(lap);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        String str = name + "    ID : " + iD + "\n";
        str += "cours: " + price + "\n";
        str += "gain: " + gain + " \t (" + gainPercent + "%)\n";
        return str;
    }

    // Necessaire pour l'affichage des boutons
    public String toStringHtml() {
        String str = "<html>" + "ID : " + iD + "<br> "+ name + "<br>";
        str += "cours: " + price + "<br>";
        str += "gain: " + gain + " (" + gainPercent + "%)</html>";
        return str;
    }

    public String toStringHist(int currentLap) {
        String str = name + "    ID : " + iD + "\n";
        str += "Quantité restante : " + quantity + "\n";
        double[] gainTot = calcTotalGain(currentLap);
        for (int i = 0; i <= currentLap; i++) {
            double hist = listOfValues.get(i);
            double histGain = listOfGain.get(i);
            double histGainPercent = listOfGainPercent.get(i);
            str += "Tour " + i + ": \n";
            str += "cours: " + hist + "\n";
            // La ligne d'après est fausse
            str += "gain: " + histGain + " (" + histGainPercent + "%)\n\n";
        }
        str += "Gain total : " + gainTot[0] + " (" + gainTot[1] + "%)";
        return str;

    }
}
