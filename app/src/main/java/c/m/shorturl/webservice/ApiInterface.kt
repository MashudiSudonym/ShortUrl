package c.m.shorturl.webservice

import c.m.shorturl.model.ContentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("create.php")
    suspend fun createShortUrl(
        @Query("format") format: String,
        @Query("url") url: String
    ): ContentResponse
}