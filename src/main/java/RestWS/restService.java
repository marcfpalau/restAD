/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestWS;

import com.google.gson.Gson;
import static com.sun.mail.imap.protocol.INTERNALDATE.format;
import database.accessBD;
import database.imageBD;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author alumne
 */
@Path("restservice")
@RequestScoped
public class restService {

    @Context
    private UriInfo context;
    String webcode = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "<meta charset='utf-8'>"
            + "<title>El título de mi página</title>"
            + "</head>"
            + "<body>";

    String webcode_end = "</body>"
            + "</html>";

    /**
     * Creates a new instance of restService
     */
    public restService() {
    }

    /**
     * POST method to register a new image
     *
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param creator
     * @param capt_date
     * @return
     */
    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerImage(@FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("keywords") String keywords,
            @FormParam("author") String author,
            @FormParam("creator") String creator,
            @FormParam("capture") String capt_date) {
        
        String filename = title.replaceAll(" ", "_");
        filename += ".jpg";
        JSONObject obj = new JSONObject();
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
        if (datosimagenes.insertarImagen(title, description, keywords, author, creator, capt_date, filename)) {
            obj.put("IsSuccessful", true);
        }
        } catch (SQLException | IOException | ParseException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            obj.put("IsSucceful", false);
        }
            String resultat = obj.toString();
        return resultat;
    }

    /**
     * POST method to modify an existing image
     *
     * @param id
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param creator
     * @param capt_date
     * @return
     */
    @Path("modify")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String modifyImage(@FormParam("id") String id,
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("keywords") String keywords,
            @FormParam("author") String author,
            @FormParam("creator") String creator,
            @FormParam("capture") String capt_date) {

        return null;
    }

    /**
     * POST method to delete an existing image
     *
     * @param id
     * @return
     */
    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String deleteImage(@FormParam("id") String id) {
        String result = "<h2>La imagen no se ha podido eliminar del servidor</h2>";
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            if (datosimagenes.eliminarImagen(id)) {
                result = "<h2>Imagen eliminada correctamente del servidor</h2>";
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webcode + result + webcode_end;
    }

    /**
     * GET method to list images
     *
     * @return
     */
    @Path("list")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String listImages() {
        String result = "<h2>Ninguna imagen disponible</h2>";
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.listImagenes();
            Iterator<Image> it = imagenes.iterator();
            if (it.hasNext()) {
                result = "<h2>Listado de imagenes: </h2>";
            }
            while (it.hasNext()) {
                Image img = (Image) it.next();
                if (1 == 1/*user.equals(img.getCreator())*/) {
                    result += "<tr><td>" + img.getTitle() + "</td>"
                            + "<td> <a href='./modificarImagen.jsp?id=" + img.getId() + "'>Modificar</a> </td>"
                            + "<td> <a href='./eliminarImagen.jsp?id=" + img.getId() + "'>Eliminar</a> </td><br/>";
                } else {
                    result += "<tr><td>" + img.getFilename() + "</td>"
                            + "<td> <a href='./images/" + img.getFilename() + "'>Ver</a> </td><br/>";
                }
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webcode + result + webcode_end;
    }

    /**
     * GET method to search images by id
     *
     * @param id
     * @return
     */
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByID(@PathParam("id") int id) {
        String result = "<h2>Ninguna imagen disponible</h2>";
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.listImagenes();
            Iterator<Image> it = imagenes.iterator();
            while (it.hasNext()) {
                Image img = (Image) it.next();
                if (1 == 1/*user.equals(img.getCreator())*/) {
                    result += "<tr><td>" + img.getTitle() + "</td>"
                            //out.println("<td> <a href='./images/" + rs.getString("filename") + "'>Ver</a> </td>");
                            + "<td> <a href='./modificarImagen.jsp?id=" + img.getId() + "'>Modificar</a> </td>"
                            + "<td> <a href='./eliminarImagen.jsp?id=" + img.getId() + "'>Eliminar</a> </td>";
                } else {
                    result += "<tr><td>" + img.getFilename() + "</td>"
                            + "<td> <a href='./images/" + img.getFilename() + "'>Ver</a> </td>";
                }
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webcode + result + webcode_end;
    }

    /**
     * GET method to search images by title
     *
     * @param title
     * @return
     */
    @Path("searchTitle/{title}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByTitle(@PathParam("title") String title) {
        String result = "<h2>Ninguna imagen disponible</h2>";
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.buscarTitle(title);
            Iterator<Image> it = imagenes.iterator();
            if (it.hasNext()) {
                result = "<h2>Listado de imagenes: </h2>";
            }
            while (it.hasNext()) {
                Image img = (Image) it.next();
                result += listar(img);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webcode + result + webcode_end;
    }

    /**
     * GET method to search images by creation date. Date format should be
     * yyyy-mm-dd
     *
     * @param date
     * @return
     */
    @Path("searchCreationDate/{date}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByCreationDate(@PathParam("date") String date) {
        String result = "<h2>Ninguna imagen disponible</h2>";
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.buscarFecha(date);
            Iterator<Image> it = imagenes.iterator();
            if (it.hasNext()) {
                result = "<h2>Listado de imagenes: </h2>";
            }
            while (it.hasNext()) {
                Image img = (Image) it.next();
                result += listar(img);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webcode + result + webcode_end;
    }

    /**
     * GET method to search images by author
     *
     * @param author
     * @return
     */
    @Path("searchAuthor/{author}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByAuthor(@PathParam("author") String author) {
        String result = "<h2>Ninguna imagen disponible</h2>";
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.buscarAuthor(author);
            Iterator<Image> it = imagenes.iterator();
            if (it.hasNext()) {
                result = "<h2>Listado de imagenes: </h2>";
            }
            while (it.hasNext()) {
                Image img = (Image) it.next();
                result += listar(img);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webcode + result + webcode_end;
    }

    /**
     * GET method to search images by keyword
     *
     * @param keywords
     * @return
     */
    @Path("searchKeywords/{keywords}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByKeywords(@PathParam("keywords") String keywords) {
        String result = "<h2>Ninguna imagen disponible</h2>";
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.buscarKeywords(keywords);
            Iterator<Image> it = imagenes.iterator();
            if (it.hasNext()) {
                result = "<h2>Listado de imagenes: </h2>";
            }
            while (it.hasNext()) {
                Image img = (Image) it.next();
                result += listar(img);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webcode + result + webcode_end;
    }

    /**
     * Función usada para retornar el codigo html listando las imagenes
     *
     * @param keywords
     * @return
     */
    private String listar(Image img) {
        String result = "<ul>"
                + "<li>ID:" + img.getId() + "</li>"
                + "<li>Titulo:" + img.getTitle() + "</li> "
                + "<li>Descripción:" + img.getDescription() + "</li>"
                + "<li>Keywords:" + img.getKeywords() + "</li>"
                + "<li>Autor:" + img.getAuthor() + "</li>"
                + "<li>Creador:" + img.getCreator() + "</li> "
                + "<li>Fecha de captura:" + img.getCapture_date() + "</li>"
                + "<li>Fecha de subida:" + img.getStorage_date() + "</li>"
                + "<li>Nombre del archivo:" + img.getFilename() + "</li> "
                + "</ul><br/>";
        return result;
    }

}
