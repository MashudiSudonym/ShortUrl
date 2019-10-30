package c.m.shorturl.model
import com.google.gson.annotations.SerializedName


data class ContentResponse(
    @SerializedName("shorturl")
    var shorturl: String? = ""
)