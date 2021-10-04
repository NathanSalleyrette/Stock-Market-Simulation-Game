package Systeme;

// C'est ici qu'on se connecte à la base de donnés pour récupérer les donnés ou en créer de nouvelles
// si c'est une nouvelle partie ou un nouveau joueur

import Graphique.EndWindow;
import Graphique.Fenetre;
import Market.*;
import Modele.PontBrownien;
import User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;
import java.sql.*;

public class Systeme {
    private static Fenetre win;
    private Connection dataBase;
    private Adminer con;
    private static Market market;
    private static User user;
    private static PontBrownien p;
    public Systeme() {
            try {
                System.out.println("RAPPEL : Il est nécessaire de se connecter au VPN de l'ensimag (ou d'être dans les locaux)");
                // On se connecte à la base de donnés
                boolean connect = true;
                String username = "Test";
                    con = new Adminer();
                    dataBase = con.connec();
                    Statement stmt = dataBase.createStatement();
                    Scanner sc = new Scanner(System.in);
                    String password;
                    System.out.println("Veuillez entrer nom d'utilisateur: ");
                    username = sc.nextLine();
                    user = new User(username);

                    if (!con.checkUserInDB(username, dataBase)) {
                        System.out.println("Vous n'existez pas, On rajoute cet utilisateur dans la Base");
                        System.out.println("Veuillez créer votre mot de passe");
                        password = sc.nextLine();
                        String request = "INSERT INTO USERS Values(" + (con.getMaxID(dataBase) + 1) + ",'" + username + "','" + password + "')";
                        stmt.executeQuery(request);
                        System.out.println("Bienvenue !!");


                    } else {

                        System.out.println("Veuillez entrer votre mot de passe:\n");
                        password = sc.nextLine();
                        if (con.checkPassword(username, password, dataBase)) {
                            System.out.println("Bienvenue !!");
                        } else {
                            System.out.println("Mot de passe erroné");
                            System.out.println("Veuillez relancer le jeu");
                            connect = false;
                        }
                    }

                if (connect) {
                    p = new PontBrownien(30);
                    win = new Fenetre(user.getName());
                    market = new Market(30, new LinkedList<Action>());
                    initMarket();
                    htmlAction();
                    win.setTextPortefeuille(user.toStringPortfolio());
                    win.setTextInformLabel(user.toStringMoney(), user.toStringInformPortfolio());
                    updateHistBut(win.getLastIDHist());

                    for (Action act : market.getMarket()) {
                        float vol = ((float) Math.random()) * 20;
                        p.generation(act, vol);
                    }
                    System.out.println("");
                }


            } catch (SQLException e) {
                System.err.println("failed");
                e.printStackTrace(System.err);
            }
        }


    public static void buy(int iDAction, int quantity) throws Exception {
        user.buy(iDAction, quantity, market);
        win.setTextPortefeuille(user.toStringPortfolio());
        win.setTextInformLabel(user.toStringMoney(), user.toStringInformPortfolio());



    }

    public static void sell(int iDAction, int quantity) throws Exception {
        user.sell(iDAction, quantity, market);
        win.setTextPortefeuille(user.toStringPortfolio());
        win.setTextInformLabel(user.toStringMoney(), user.toStringInformPortfolio());
    }

    /**
     * L'affichage du texte sur les boutons doit être fait en html
     */
    public static void htmlAction(){
        for (Action act: market.getMarket())
        win.setTextButtonsHtml(act.getID(), act.toStringHtml());

    }

    /**
     * Met à jour l'affichage du bouton n°IDAction
     * @param iDAction
     */
    public static void updateHistBut(int iDAction){
        Action act = market.getMarket().get(iDAction);
        String str = act.toStringHist(market.getLap());
        win.setTextHist(str);
        win.setLastIDHist(iDAction);
    }

    /**
     * Fonction qui est lancé à chaque appuie sur le bouton tour suivant, elle met à jour tous les cours des actions
     *la nouvelle valeur du portefeuille
     */
    public static void updateLap(){

        market.updateLap();
        htmlAction();
        user.calc();
        win.setTextNextLap(market.getLap());
        win.setTextPortefeuille(user.toStringPortfolio());
        win.setTextInformLabel(user.toStringMoney(), user.toStringInformPortfolio());
        updateHistBut(win.getLastIDHist());

        if (market.getLap() == 30){
            endWindow();
        }

    }

    /**
     * Fenetre final qui s'affiche lorsqu'on arrive au 30ieme tour ou lorsqu'on appuie sur le bouton quitter
     */
    public static void endWindow(){
        EndWindow finalWindow = new EndWindow(null, "FIN", true, user.getMoney()+user.getValuePortfolio());
    }

    public static void quit(){
        win.dispose();
    }

    /**
     * On initialise le marché, pendant ce temps, toutes les valeurs par défaut sont égales à 200
     * mais au fur et à mesure, nous randomiserons ces valeurs
     */
    public static void initMarket(){
        market.addAction(new Action(1,   "RENAULT",              200));
        market.addAction(new Action(2,   "HERMES",               200));
        market.addAction(new Action(3,   "ARCELORMITTAL",        200));
        market.addAction(new Action(4,   "WORLDLINE",            200));
        market.addAction(new Action(5,   "TELEPERFORMANCE",      200));
        market.addAction(new Action(6,   "DASSAULT SYSTEMES",    200));
        market.addAction(new Action(7,   "L'OREAL",              200));
        market.addAction(new Action(8,   "LVMH",                 200));
        market.addAction(new Action(9,   "AIR LIQUIDE",          200));
        market.addAction(new Action(10,  "STMICROELECTRONICS",   200));
        market.addAction(new Action(11,  "ESSILORLUXOTTICA",       200));
        market.addAction(new Action(12,  "PERNOD RICARD",         200));
        market.addAction(new Action(13,  "KERING",                200));
        market.addAction(new Action(14,  "SCHNEIDER ELECTRIC",    200));
        market.addAction(new Action(15,  "LEGRAND",               200));
        market.addAction(new Action(16,  "VIVENDI",               200));
        market.addAction(new Action(17,  "CAPGEMINI",             200));
        market.addAction(new Action(18,  "SAFRAN",                200));
        market.addAction(new Action(19,  "ALSTOM",                200));
        market.addAction(new Action(20,  "VEOLIA ENVIRONNEMENT", 200));
        market.addAction(new Action(21,  "SANOFI",                200));
        market.addAction(new Action(22,  "DANONE",                200));
        market.addAction(new Action(23,  "VINCI",                 200));
        market.addAction(new Action(24,  "AIRBUS",                200));
        market.addAction(new Action(25,  "CARREFOUR",             200));
        market.addAction(new Action(26,  "SAINT GOBAIN",          200));
        market.addAction(new Action(27,  "ENGIE",                 200));
        market.addAction(new Action(28,  "MICHELIN",              200));
        market.addAction(new Action(29,  "THALES",                200));
        market.addAction(new Action(30,  "BOUYGUES",              200));
        market.addAction(new Action(31,  "ORANGE",                200));
        market.addAction(new Action(32,  "ATOS",                  200));
        market.addAction(new Action(33,  "TOTAL",                 200));
        market.addAction(new Action(34,  "PUBLICIS GROUPE",       200));
        market.addAction(new Action(35,  "AXA",                   200));
        market.addAction(new Action(36,  "CREDIT AGRICOLE",       200));
        market.addAction(new Action(37,  "BNP PARIBAS",           200));
        market.addAction(new Action(38,  "PEUGEOT",               200));
        market.addAction(new Action(39,  "SOCIETE GENERALE",      200));
    }
}
