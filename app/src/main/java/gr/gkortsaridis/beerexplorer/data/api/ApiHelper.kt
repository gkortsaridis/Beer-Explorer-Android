package gr.gkortsaridis.beerexplorer.data.api

import javax.inject.Inject

class ApiHelper @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getBeers(pageNum: Int) = apiService.getBeers(
        pageNum,
        beersPerPage
    )

    companion object {
        const val beersPerPage = 80
        const val maxBeersNum = 400 //Learnt by manually going through the API
        const val maxPagesNum = maxBeersNum / beersPerPage
    }
}