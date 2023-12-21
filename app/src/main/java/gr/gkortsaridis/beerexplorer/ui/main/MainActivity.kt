package gr.gkortsaridis.beerexplorer.ui.main

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gr.gkortsaridis.beerexplorer.R
import gr.gkortsaridis.beerexplorer.data.model.Beer
import gr.gkortsaridis.beerexplorer.databinding.ActivityMainBinding
import gr.gkortsaridis.beerexplorer.ui.details.BeerDetailsActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BeersAdapter.ClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val adapter = BeersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        adapter.setClickListener(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.toolbar.title = getString(R.string.app_name)

        collectBeers()
    }

    private fun collectBeers() = lifecycleScope.launch {
        val dialog = ProgressDialog(this@MainActivity)
        dialog.setMessage("Loading...")

        viewModel.beers.collect { beersUiState ->
            when(beersUiState) {
                is MainViewModel.BeersUiStates.Success -> {
                    dialog.hide()
                    adapter.setBeersToDisplay(BeerAdapterListCreator.createBeerList(beersUiState.beers, true))
                }
                is MainViewModel.BeersUiStates.Error -> {
                    dialog.hide()
                    Toast.makeText(this@MainActivity, beersUiState.message, Toast.LENGTH_LONG).show()
                }
                is MainViewModel.BeersUiStates.Loading -> { dialog.show() }
            }
        }
    }

    override fun onItemClick(beer: Beer) {
        startActivity(
            Intent(this@MainActivity, BeerDetailsActivity::class.java).apply {
                putExtra("BEER", beer)
            }
        )
    }

    override fun onLoadMoreClick() {

    }
}