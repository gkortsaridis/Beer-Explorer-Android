package gr.gkortsaridis.beerexplorer.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import gr.gkortsaridis.beerexplorer.R
import gr.gkortsaridis.beerexplorer.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        collectBeers()
    }

    private fun collectBeers() = lifecycleScope.launch {
        viewModel.beers.collect { beersUiState ->
            when(beersUiState) {
                is MainViewModel.BeersUiStates.Success -> {
                    Log.i("TEST",  beersUiState.beers.toString())
                }
                is MainViewModel.BeersUiStates.Error -> {
                    Log.i("TEST",  beersUiState.message.toString())
                }
                is MainViewModel.BeersUiStates.Loading -> {
                    Log.i("TEST",  "Loading")
                }
            }

        }
    }
}