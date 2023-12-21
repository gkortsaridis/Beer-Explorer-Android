package gr.gkortsaridis.beerexplorer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.gkortsaridis.beerexplorer.data.api.ApiHelper
import gr.gkortsaridis.beerexplorer.data.model.Beer
import gr.gkortsaridis.beerexplorer.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val beersDownloaded: MutableList<Beer> = mutableListOf()

    private var beersPageToRetrieve = 1

    private val _beers = MutableSharedFlow<BeersUiStates>()
    val beers: Flow<BeersUiStates> = _beers.asSharedFlow()

    init {
        getBeers(beersPageToRetrieve)
    }

    private fun getBeers(pageNum: Int) {
        viewModelScope.launch {
            _beers.emit(BeersUiStates.Loading)
            try {
                val beers = mainRepository.getBeers(pageNum)
                beersDownloaded.addAll(beers)
                _beers.emit(BeersUiStates.Success(beersDownloaded, beersPageToRetrieve < ApiHelper.maxPagesNum))
            } catch (ex : Exception) {
                _beers.emit(BeersUiStates.Error(ex.message ?: "Error"))
            }
        }
    }

    fun loadNextBeersPage() {
        if(beersPageToRetrieve < ApiHelper.maxPagesNum) {
            beersPageToRetrieve+=1
            getBeers(beersPageToRetrieve)
        }
    }

    sealed class BeersUiStates {
        data class Success(val beers: List<Beer>, val hasMore: Boolean): BeersUiStates()
        data class Error(val message: String): BeersUiStates()
        data object Loading: BeersUiStates()
    }
}