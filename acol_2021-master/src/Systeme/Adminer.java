package Systeme;

import java.sql.*;

/**
 * Classe de connexion à Adminer
 */
public class Adminer {


    public static Connection connec() {
        Connection con = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String user = "peyrichn";
            String password = "annivdeloic";
            String link = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";

            con = DriverManager.getConnection(link, user, password);
            System.out.println("CONNEXION REUSSIE");

        }catch  (SQLException e) {
            System.err.println("CONNEXION ECHOUE");

            e.printStackTrace();
        }

        // Connexion réussie, on passe à l'initialisation de la Base de données

        return con;

    }

    /**
     * On vérifie si l'identifiant est dans la base de données
     * @param name
     * @param con
     * @return
     */
    public boolean checkUserInDB(String name, Connection con) {
        boolean userInDB = false;
        try {
            Statement stmt = con.createStatement();
            String request = "SELECT * FROM Users WHERE Username ='" + name + "'";
            ResultSet res = stmt.executeQuery(request);
            userInDB = res.next(); // On regarde si le résultat est nul ou pas

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return userInDB;

    }

    /**
     * On vérifie si le mot de passe correspond à l'identifiant
     * @param name
     * @param pwd
     * @param con
     * @return
     */
    public boolean checkPassword(String name, String pwd, Connection con) {
        boolean pwdTrue = false;
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM Users WHERE Username ='"
                    + name + "' AND Password ='" + pwd + "'");
            pwdTrue = res.next(); // On regarde si le résultat est nul ou pas

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return pwdTrue;
    }

    public void initDataBase(int iDUser) {

    }

    /**
     * On récupère l'ID maximal dans notre base
     * @param con
     * @return
     * @throws SQLException
     */
    public int getMaxID(Connection con) throws SQLException {
        String request = "SELECT MAX(IDUser) FROM Users";
         Statement stmt = con.createStatement();
         ResultSet res = stmt.executeQuery(request);
         res.next();
         return res.getInt(1);
    }

}
