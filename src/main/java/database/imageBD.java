/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import RestWS.Image;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author alumne
 */
public class imageBD {

    private final accessBD base_dades;

    public imageBD(accessBD BD) {
        base_dades = BD;
    }

    public Boolean insertarImagen(String title, String description, String keywords,
            String author, String creator, String capture_date, String filename) throws SQLException, ParseException {

        PreparedStatement statement;

        String query = "INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        /* Obtener fecha actual */
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha_str = sdf.format(fecha);

        /* Formatar fecha del formulario */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_captura = formatter.parse(capture_date);
        String fechaCaptura_str = sdf.format(fecha_captura);

        statement = base_dades.getConnection().prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setString(3, keywords);
        statement.setString(4, author);
        statement.setString(5, creator);
        statement.setString(6, fechaCaptura_str);
        statement.setString(7, fecha_str);
        statement.setString(8, filename);
        int i = statement.executeUpdate();
        return i > 0;
    }

    //Devuelve un ArrayList con ids a partir de un string de busqueda
    public ArrayList<Integer> buscarImagenes(String tag) throws SQLException {
        ArrayList<Integer> ids = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT id FROM image WHERE title LIKE ? "
                + "OR description LIKE ? OR keywords LIKE ? OR author LIKE ? OR creator LIKE ? OR capture_date LIKE ? "
                + "OR storage_date LIKE ? OR filename LIKE ? ORDER BY id DESC";

        String[] keywords = tag.split(", ");
        for (int i = 0; i < keywords.length; ++i) {
            String palabra = '%' + keywords[i] + '%';
            statement = base_dades.getConnection().prepareStatement(query);
            statement.setString(1, palabra);
            statement.setString(2, palabra);
            statement.setString(3, palabra);
            statement.setString(4, palabra);
            statement.setString(5, palabra);
            statement.setString(6, palabra);
            statement.setString(7, palabra);
            statement.setString(8, palabra);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                ids.add(0);
            } else {
                if (!ids.contains(rs.getInt(1))) {
                    ids.add(rs.getInt(1));
                    while (rs.next()) {
                        if (!ids.contains(rs.getInt(1))) {
                            ids.add(rs.getInt(1));
                        }
                    }
                }
            }

        }

        return ids;
    }
    
    //Devuelve un ArrayList con Imagenes a partir de su id
    public ArrayList<Image> buscarId(String tag) throws SQLException {
        ArrayList<Image> imagenes = null;
        PreparedStatement statement;
        String query = "SELECT id, title, description, keywords, author, creator, capture_date, "
                + "storage_date, filename FROM image WHERE id LIKE ? ORDER BY id DESC";

        String palabra = '%' + tag + '%';
        statement = base_dades.getConnection().prepareStatement(query);
        statement.setString(1, palabra);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            imagenes = new ArrayList<>();
            Image imagen_first = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                    rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                    rs.getString("filename"));

            imagenes.add(imagen_first);
            while (rs.next()) {
                Image imagen = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                        rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                        rs.getString("filename"));

                imagenes.add(imagen);
            }
        }

        return imagenes;
    }

    //Devuelve un ArrayList con Imagenes a partir de su titulo
    public ArrayList<Image> buscarTitle(String tag) throws SQLException {
        ArrayList<Image> imagenes = null;
        PreparedStatement statement;
        String query = "SELECT id, title, description, keywords, author, creator, capture_date, "
                + "storage_date, filename FROM image WHERE title LIKE ? ORDER BY id DESC";

        String palabra = '%' + tag + '%';
        statement = base_dades.getConnection().prepareStatement(query);
        statement.setString(1, palabra);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            imagenes = new ArrayList<>();
            Image imagen_first = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                    rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                    rs.getString("filename"));

            imagenes.add(imagen_first);
            while (rs.next()) {
                Image imagen = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                        rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                        rs.getString("filename"));

                imagenes.add(imagen);
            }
        }

        return imagenes;
    }

    //Devuelve un ArrayList con Imagenes a partir de su fecha de creacion formato dd/mm/yyyy
    public ArrayList<Image> buscarFecha(String tag) throws SQLException {
        ArrayList<Image> imagenes = null;
        PreparedStatement statement;
        String query = "SELECT id, title, description, keywords, author, creator, capture_date, "
                + "storage_date, filename FROM image WHERE storage_date LIKE ? ORDER BY id DESC";

        String palabra = '%' + tag + '%';
        statement = base_dades.getConnection().prepareStatement(query);
        statement.setString(1, palabra);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            imagenes = new ArrayList<>();
            Image imagen_first = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                    rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                    rs.getString("filename"));

            imagenes.add(imagen_first);
            while (rs.next()) {
                Image imagen = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                        rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                        rs.getString("filename"));

                imagenes.add(imagen);
            }
        }

        return imagenes;
    }
    

    //Devuelve un ArrayList con Imagenes a partir de sus keywords
    public ArrayList<Image> buscarKeywords(String tag) throws SQLException {
        ArrayList<Image> imagenes = null;
        PreparedStatement statement;
        String query = "SELECT id, title, description, keywords, author, creator, capture_date, "
                + "storage_date, filename FROM image WHERE keywords LIKE ? ORDER BY id DESC";

        String palabra = '%' + tag + '%';
        statement = base_dades.getConnection().prepareStatement(query);
        statement.setString(1, palabra);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            imagenes = new ArrayList<>();
            Image imagen_first = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                    rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                    rs.getString("filename"));

            imagenes.add(imagen_first);
            while (rs.next()) {
                Image imagen = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                        rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                        rs.getString("filename"));

                imagenes.add(imagen);
            }
        }

        return imagenes;
    }

    //Devuelve un ArrayList con Imagenes a partir de su creador
    public ArrayList<Image> buscarAuthor(String tag) throws SQLException {
        ArrayList<Image> imagenes = null;
        PreparedStatement statement;
        String query = "SELECT id, title, description, keywords, author, creator, capture_date, "
                + "storage_date, filename FROM image WHERE author LIKE ? ORDER BY id DESC";

        String palabra = '%' + tag + '%';
        statement = base_dades.getConnection().prepareStatement(query);
        statement.setString(1, palabra);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            imagenes = new ArrayList<>();
            Image imagen_first = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                    rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                    rs.getString("filename"));

            imagenes.add(imagen_first);
            while (rs.next()) {
                Image imagen = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                        rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                        rs.getString("filename"));

                imagenes.add(imagen);
            }
        }

        return imagenes;
    }
    
    
    
    //Devuelve una imagen a partir de su id
    public Image getImage(int id) throws SQLException {

        Image imagen = null;
        PreparedStatement statement;
        String query = "SELECT id, title, description, keywords, author, creator, capture_date, "
                + "storage_date, filename FROM image WHERE id = ?";

        statement = base_dades.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            imagen = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                    rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                    rs.getString("filename"));
        }

        return imagen;
    }

    public Boolean eliminarImagen(String id) throws SQLException {
        PreparedStatement statement;
        String query = "DELETE FROM image WHERE id=?";
        statement = base_dades.getConnection().prepareStatement(query);
        statement.setString(1, id);
        int i = statement.executeUpdate();
        return i > 0;
    }

    public ArrayList<Image> listImagenes() throws SQLException {
        ArrayList<Image> imagenes = null;
        PreparedStatement statement;
        String query = "SELECT * FROM image";
        statement = base_dades.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            imagenes = new ArrayList<>();
            Image imagen_first = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                    rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                    rs.getString("filename"));

            imagenes.add(imagen_first);
            while (rs.next()) {
                Image imagen = new Image(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("keywords"),
                        rs.getString("author"), rs.getString("creator"), rs.getString("capture_date"), rs.getString("storage_date"),
                        rs.getString("filename"));

                imagenes.add(imagen);
            }
        }

        return imagenes;
    }

    public Boolean modificaImagen(String id, String title, String description, String keywords, String author, String capture_date) throws SQLException, ParseException {
        PreparedStatement statement;
        String query = "UPDATE image SET title = ?,"
                + "description = ?,"
                + "keywords = ?,"
                + "author = ?,"
                + "capture_date = ? "
                + "WHERE id=?";

        /* Formatear fecha recibida*/
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_captura = formatter.parse(capture_date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha_str = sdf.format(fecha_captura);

        statement = base_dades.getConnection().prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setString(3, keywords);
        statement.setString(4, author);
        statement.setString(5, fecha_str);
        statement.setString(6, id);
        int i = statement.executeUpdate();
        return i > 0;
    }


}
