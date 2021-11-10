/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumne
 */
public class accessBD {
    
        private Connection connection = null;
    
    //Constructor conexió amb la base de dades
    public accessBD(String ClientDriver, String DadesBD) throws SQLException, IOException{
        try{
            try {
                Class.forName(ClientDriver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(accessBD.class.getName()).log(Level.SEVERE, null, ex);
            }
         connection = DriverManager.getConnection(DadesBD);
         
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    //Acces a la base de dades
    public Connection getConnection(){
        return connection;
    }
    
    //Finalització de connexió
    public void fiConexio() throws SQLException{
         if (connection != null) {
            connection.close();
        }
    }
    
}

