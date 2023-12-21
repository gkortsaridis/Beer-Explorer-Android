package gr.gkortsaridis.beerexplorer.data.api

import gr.gkortsaridis.beerexplorer.data.model.Beer
import retrofit2.http.GET

interface ApiService {
    @GET("v2/beers")
    suspend fun getBeers(): List<Beer>
}