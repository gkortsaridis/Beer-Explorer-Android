package gr.gkortsaridis.beerexplorer.ui.main

import gr.gkortsaridis.beerexplorer.CoroutineTestRule
import gr.gkortsaridis.beerexplorer.data.api.ApiHelper
import gr.gkortsaridis.beerexplorer.data.model.Beer
import gr.gkortsaridis.beerexplorer.data.repository.MainRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var mainRepository: MainRepository

    private val apiHelper = mockk<ApiHelper>(relaxed = true)
    private val mockBeer = mockk<Beer>(relaxed = true)
    @Before
    fun setup() {
        mainRepository = MainRepository(apiHelper, coroutinesTestRule.testDispatcherProvider)
    }

    @Test
    fun `test beersUiState flow when beers are returned from the API`() = runTest(UnconfinedTestDispatcher()) {
        every { runBlocking { mainRepository.getBeers(any()) } } returns listOf(mockBeer, mockBeer, mockBeer)

        launch {
            viewModel = MainViewModel(mainRepository)
            var beersUiState = viewModel.beers.first()
            assert(beersUiState.javaClass == MainViewModel.BeersUiStates.Loading::class.java)
            beersUiState = viewModel.beers.first()
            assert(beersUiState.javaClass == MainViewModel.BeersUiStates.Success::class.java)
            val beers = (beersUiState as MainViewModel.BeersUiStates.Success).beers
            assert(beers.size == 3)
        }
    }

    @Test
    fun `test beersUiState flow when error is returned from the API`() = runTest(UnconfinedTestDispatcher()) {
        every { runBlocking { mainRepository.getBeers(any()) } } throws Exception("Exception")

        launch {
            viewModel = MainViewModel(mainRepository)
            var beersUiState = viewModel.beers.first()
            assert(beersUiState.javaClass == MainViewModel.BeersUiStates.Loading::class.java)
            beersUiState = viewModel.beers.first()
            assert(beersUiState.javaClass == MainViewModel.BeersUiStates.Error::class.java)
            val message = (beersUiState as MainViewModel.BeersUiStates.Error).message
            assert(message == "Exception")
        }
    }
}