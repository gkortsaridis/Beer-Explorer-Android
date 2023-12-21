package gr.gkortsaridis.beerexplorer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _beers = MutableSharedFlow<BeersUiStates>()
    val beers: Flow<BeersUiStates> = _beers.asSharedFlow()

    init {
        getBeers()
    }

    private fun getBeers() {
        viewModelScope.launch {
            _beers.emit(BeersUiStates.Loading)
            try {
                val beers = mainRepository.getBeers()
                _beers.emit(BeersUiStates.Success(beers))
            } catch (ex : Exception) {
                _beers.emit(BeersUiStates.Error(ex.message ?: "Error"))
            }
        }
    }

    sealed class BeersUiStates {
        data class Success(val beers: List<Beer>): BeersUiStates()
        data class Error(val message: String): BeersUiStates()
        data object Loading: BeersUiStates()
    }
}