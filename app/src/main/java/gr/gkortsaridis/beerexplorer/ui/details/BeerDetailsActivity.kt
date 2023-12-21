package gr.gkortsaridis.beerexplorer.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import gr.gkortsaridis.beerexplorer.R
import gr.gkortsaridis.beerexplorer.data.model.Beer
import gr.gkortsaridis.beerexplorer.databinding.ActivityBeerDetailsBinding

@AndroidEntryPoint
class BeerDetailsActivity : AppCompatActivity() {

    /**
     * I probably have the most basic of all basic UIs for this Activity.
     * I wanted to focus more on the passing of the Beer object and
     * the proper data binding aspect of it, since the whole screen is populated
     * in the xml.
     *
     * Also, i know that the VM is empty in this case. I decided to add it in,
     * since it could potentially be used if we wanted to expand the app
     * (e.g. add favorite beers so save locally in Room)
     * Better have and not need, than need and not have :)
     */
    private lateinit var binding : ActivityBeerDetailsBinding
    private val viewModel: BeerDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beer_details)
        val beer = intent.getParcelableExtra("BEER", Beer::class.java)
        binding.beer = beer
        binding.lifecycleOwner = this
    }
}