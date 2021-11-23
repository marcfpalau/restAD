/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import RestWS.Image;
import RestWS.restService;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

/**
 *
 * @author alumne
 */
public class test {

    public static void main(String[] args){
         // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Reading data using readLine
            String name = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = null;
            imagenes = datosimagenes.listImagenes();
            //imagenes = datosimagenes.busquedaMultivaluada("1", true, false, false, false, false, false, false, false);
            Iterator<Image> iter = imagenes.iterator();
            while (iter.hasNext()) {
                Image imagen = iter.next();
                System.out.println(imagen.getTitle());
                System.out.println(imagen.getId());
                System.out.println("--------------------");
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
