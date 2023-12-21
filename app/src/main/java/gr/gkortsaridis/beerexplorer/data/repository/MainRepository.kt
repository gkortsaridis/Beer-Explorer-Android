package gr.gkortsaridis.beerexplorer.data.repository

import gr.gkortsaridis.beerexplorer.data.api.ApiHelper
import gr.gkortsaridis.beerexplorer.data.model.Beer
import gr.gkortsaridis.beerexplorer.utils.DefaultDispatcherProvider
import gr.gkortsaridis.beerexplorer.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
){
    suspend fun getBeers(): List<Beer> = withContext(dispatcherProvider.io()) {
        return@withContext apiHelper.getBeers()
    }
}