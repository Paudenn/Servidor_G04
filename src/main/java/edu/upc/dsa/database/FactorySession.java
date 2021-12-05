package edu.upc.dsa.database;


import edu.upc.dsa.database.Session;
import edu.upc.dsa.database.SessionImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactorySession {
    public static Session openSession() {


        Connection conn = getConnection();

        Session session = new SessionImpl(conn);

        return session;
    }



    private static Connection getConnection() {
        Connection conn = null;
        try {
            /*String url = "jdbc:mysql://localhost/dsag04?autoReconnect=true&useSSL=false";
            String password = "Mazinger72";
            String user = "root";
           conn =
                    DriverManager.getConnection(url,user,password);
            System.out.println("Connectat a la bbdd correctament.");

             */





            conn =
                    DriverManager.getConnection("jdbc:mariadb://localhost:3306/dsag04", "root", "Mazinger72");
            System.out.println("Connectat a la bbdd correctament.");




        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }
}
