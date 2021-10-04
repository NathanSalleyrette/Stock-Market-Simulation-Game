package User;

import Market.Action;
import Market.Market;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class User {
    private String name;
    private double money;
    private HashMap<Action, Integer> portfolio; // Voir Patron slide 20
    private double valuePortfolio;

    public User(String name) {
        this.name = name;
        this.money = 10000;
        this.portfolio = new HashMap<Action, Integer>();
    }

    /**
     * L'utilisateur achète une certaine quantité d'une action
     * @param id id de l'action
     * @param quantite quantité désirée par l'utilisateur
     * @param m
     * @throws Exception
     */
    public void buy(int id, int quantite, Market m) throws Exception {
        Action act = m.getMarket().get(id);
        int resQuantity = act.getQuantity() - quantite;
        if (resQuantity < 0) {
            throw new Exception("Quantité d'actions disponibles insuffisantes");
        }
        double totalPrice = act.getPrice() * quantite;
        if (money - totalPrice < 0) {
            throw new Exception("Fond insuffisant");
        }
        act.setQuantity(resQuantity) ;
        if (portfolio.containsKey(act)) {
            portfolio.put(act, portfolio.get(act) + quantite);
        }else{
            portfolio.put(act, quantite);
        }
        money -= totalPrice;
        valuePortfolio += totalPrice;
        money =  (new BigDecimal(money).setScale(3, RoundingMode.HALF_UP)).doubleValue();
        valuePortfolio =  (new BigDecimal(valuePortfolio).setScale(3, RoundingMode.HALF_UP)).doubleValue();

    }

    /**
     * L'utilisateur souhaite vendre une certaine quantité d'actions
     * @param id id de l'action souhaité
     * @param quantite quantité souhaité
     * @param m le marché dans lequel on achète
     * @throws Exception
     */
    public void sell(int id, int quantite, Market m) throws Exception{
        Action act = m.getMarket().get(id);
        if (!portfolio.containsKey(act)) {
            throw new Exception("Vous ne possédez pas cette action");
        }
        if (portfolio.get(act) < quantite) {
            throw new Exception(("Vous ne possédez pas assez de cette action"));
        }
        double price = act.getPrice() * quantite;
        money += price;
        valuePortfolio -= price;

        money =  (new BigDecimal(money).setScale(3, RoundingMode.HALF_UP)).doubleValue();
        valuePortfolio =  (new BigDecimal(valuePortfolio).setScale(3, RoundingMode.HALF_UP)).doubleValue();

        int newQuantity = portfolio.get(act) - quantite;
        if (newQuantity == 0) {
            portfolio.remove(act);
            if (portfolio.isEmpty()) {
                valuePortfolio = 0;
            }
        }
        else portfolio.put(act, newQuantity);
    }

    /**
     * Calcul de la valeur actuelle du portefeuille
     */
    public void calc() {
        double totalPortfolio =  0;
        for(Map.Entry map: portfolio.entrySet()) {
            totalPortfolio += ((Action) map.getKey()).getPrice() * ((Integer) map.getValue());
        }

        valuePortfolio = totalPortfolio;
        valuePortfolio =  (new BigDecimal(valuePortfolio).setScale(3, RoundingMode.HALF_UP)).doubleValue();

    }

    public double getValuePortfolio() {return valuePortfolio;}

    public double getMoney() {return money;}

    public String getName() {return name;}

    public String toStringPortfolio(){
        String str = "Argent disponible : " + money + "\n";
        for(Map.Entry map: portfolio.entrySet()) {
            str += ((Action) map.getKey()).toString();
            str += "Quantité : " + ((Integer) map.getValue()) + "\n\n";
        }
        str += "Total Valeur portefeuille : " + valuePortfolio;
        return str;
    }

    public String toStringMoney(){
        return "Cash : " + money;
    }

    public String toStringInformPortfolio(){
        return "Portefeuille : " + valuePortfolio;
    }
}
