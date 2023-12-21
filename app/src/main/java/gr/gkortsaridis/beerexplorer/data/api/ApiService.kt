package gr.gkortsaridis.beerexplorer.data.api

import gr.gkortsaridis.beerexplorer.data.model.Beer
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 80
    ): List<Beer>
}