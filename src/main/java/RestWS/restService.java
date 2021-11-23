/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestWS;

import com.google.gson.Gson;
import database.accessBD;
import database.imageBD;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import org.json.JSONArray;

/**
 * REST Web Service
 *
 * @author alumne
 */
@Path("restservice")
@RequestScoped
public class restService {

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
            datosimagenes.insertarImagen(title, description, keywords, author, creator, capt_date, filename);
            obj.put("IsSuccessful", true);

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
    @Produces(MediaType.APPLICATION_JSON)
    public String modifyImage(@FormParam("id") String id,
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("keywords") String keywords,
            @FormParam("author") String author,
            @FormParam("creator") String creator,
            @FormParam("capture") String capt_date) {

        JSONObject obj = new JSONObject();
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            datosimagenes.modificaImagen(id, title, description, keywords, author, capt_date);
            obj.put("IsSuccessful", true);

        } catch (SQLException | IOException | ParseException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            obj.put("IsSucceful", false);
        }
        String resultat = obj.toString();
        return resultat;
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
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteImage(@FormParam("id") String id) {

        JSONObject obj = new JSONObject();
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            datosimagenes.eliminarImagen(id);
            obj.put("IsSuccessful", true);

        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            obj.put("IsSucceful", false);
        }
        String resultat = obj.toString();
        return resultat;
    }

    /**
     * GET method to list images
     *
     * @return
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listImages() {
        String result;
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.listImagenes();
            result = new Gson().toJson(imagenes);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            JSONArray obj = new JSONArray();
            obj.put("error");
            result = obj.toString();
        }
        return result;
    }

    /**
     * GET method to search images by id
     *
     * @param id
     * @return
     */
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByID(@PathParam("id") int id) {
        String result;
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.buscarId(Integer.toString(id));
            result = new Gson().toJson(imagenes);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            JSONArray obj = new JSONArray();
            obj.put("error");
            result = obj.toString();
        }
        return result;

    }

    /**
     * GET method to search images by title
     *
     * @param title
     * @return
     */
    @Path("searchTitle/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByTitle(@PathParam("title") String title) {
        String result;
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.buscarTitle(title);
            result = new Gson().toJson(imagenes);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            JSONArray obj = new JSONArray();
            obj.put("error");
            result = obj.toString();
        }
        return result;
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
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByCreationDate(@PathParam("date") String date) {
        String result;
        try {
            /* Formatear fecha recibida*/
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha_captura = formatter.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fecha_str = sdf.format(fecha_captura);

            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.buscarFecha(fecha_str);
            result = new Gson().toJson(imagenes);
        } catch (SQLException | IOException | ParseException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            JSONArray obj = new JSONArray();
            obj.put("error");
            result = obj.toString();
        }
        return result;
    }

    /**
     * GET method to search images by author
     *
     * @param author
     * @return
     */
    @Path("searchAuthor/{author}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByAuthor(@PathParam("author") String author) {
        String result;
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.buscarAuthor(author);
            result = new Gson().toJson(imagenes);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            JSONArray obj = new JSONArray();
            obj.put("error");
            result = obj.toString();
        }
        return result;
    }

    /**
     * GET method to search images by keyword
     *
     * @param keywords
     * @return
     */
    @Path("searchKeywords/{keywords}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByKeywords(@PathParam("keywords") String keywords) {
        String result;
        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.buscarKeywords(keywords);
            result = new Gson().toJson(imagenes);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            JSONArray obj = new JSONArray();
            obj.put("error");
            result = obj.toString();
        }
        return result;
    }

    /**
     * GET method to search images by keyword
     *
     * @param value
     * @param title
     * @param description
     * @param keywords
     * @param creator
     * @param author
     * @param storage_date
     * @param capture_date
     * @param filename
     * @return
     */
    @Path("searchMultiatribute/")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String searchMultiatribute(@FormParam("value") String value, @FormParam("title") String title,
            @FormParam("description") String description, @FormParam("keywords") String keywords,
            @FormParam("author") String author, @FormParam("creator") String creator,
            @FormParam("capture_date") String capture_date, @FormParam("storage_date") String storage_date,
            @FormParam("filename") String filename) {
        String result;
        Boolean title_check = false;
        Boolean description_check = false;
        Boolean keywords_check = false;
        Boolean author_check = false;
        Boolean creator_check = false;
        Boolean captureDate_check = false;
        Boolean storageDate_check = false;
        Boolean filename_check = false;
        if (title != null) {
            title_check = true;
        }
        if (description != null) {
            description_check = true;
        }
        if (keywords != null) {
            keywords_check = true;
        }
        if (author != null) {
            author_check = true;
        }
        if (creator != null) {
            creator_check = true;
        }
        if (capture_date != null) {
            captureDate_check = true;
        }
        if (storage_date != null) {
            storageDate_check = true;
        }
        if (filename != null) {
            filename_check = true;
        }

        try {
            accessBD basedatos = new accessBD("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            imageBD datosimagenes = new imageBD(basedatos);
            ArrayList<Image> imagenes = datosimagenes.busquedaMultivaluada(value, title_check, description_check, keywords_check,
                    author_check, creator_check, captureDate_check, storageDate_check, filename_check);
            result = new Gson().toJson(imagenes);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(restService.class.getName()).log(Level.SEVERE, null, ex);
            JSONArray obj = new JSONArray();
            obj.put("error");
            result = obj.toString();
        }
        return result;
    }

}
