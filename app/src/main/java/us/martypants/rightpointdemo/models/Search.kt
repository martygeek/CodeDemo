package us.martypants.rightpointdemo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Search {

    @Expose
    var imdbID: String? = null

    @SerializedName("Poster")
    @Expose
    var poster: String? = null

    @SerializedName("Title")
    @Expose
    var title: String? = null

    @SerializedName("Type")
    @Expose
    var type: String? = null

    @SerializedName("Year")
    @Expose
    var year: String? = null

}
