/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestWS;

/**
 *
 * @author alumne
 */
public class Image {

    private int id;
    private String title;
    private String description;
    private String keywords;
    private String author;
    private String creator;
    private String capture_date;
    private String storage_date;
    private String filename;

    public Image(int id, String title, String description, String keywords, String author, String creator, String capture_date, String storage_date, String filename) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.author = author;
        this.creator = creator;
        this.capture_date = capture_date;
        this.storage_date = storage_date;
        this.filename = filename;
    }
    
    public Image(){
    }
    
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreator() {
        return creator;
    }

    public String getCapture_date() {
        return capture_date;
    }

    public String getStorage_date() {
        return storage_date;
    }

    public String getFilename() {
        return filename;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCapture_date(String capture_date) {
        this.capture_date = capture_date;
    }

    public void setStorage_date(String storage_date) {
        this.storage_date = storage_date;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}

