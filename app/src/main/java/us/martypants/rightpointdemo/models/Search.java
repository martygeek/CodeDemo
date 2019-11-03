
package us.martypants.rightpointdemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Search {

    @Expose
    private String imdbID;
    @SerializedName("Poster")
    private String poster;
    @SerializedName("Title")
    private String title;
    @SerializedName("Type")
    private String type;
    @SerializedName("Year")
    private String year;

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
