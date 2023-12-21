package gr.gkortsaridis.beerexplorer.data.api

import javax.inject.Inject

class ApiHelper @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getBeers() = apiService.getBeers()
}