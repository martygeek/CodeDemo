package us.martypants.rightpointdemo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ImdbData {

    @SerializedName("Response")
    @Expose
    var response: String? = null

    @SerializedName("Search")
    @Expose
    var search: MutableList<Search>? = null

    @Expose
    var totalResults: String? = null

}
