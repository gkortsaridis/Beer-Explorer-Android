package gr.gkortsaridis.beerexplorer.ui.main

import gr.gkortsaridis.beerexplorer.data.model.Beer

object BeerAdapterListCreator {
    fun createBeerList(beers: List<Beer>, hasMore: Boolean): List<BeersAdapter.AdapterItem> {
        val beerList: MutableList<BeersAdapter.AdapterItem> = beers.map { BeersAdapter.AdapterItem.BeerItem(it) }.toMutableList()
        if(hasMore) {
            beerList.add(BeersAdapter.AdapterItem.LoadMoreItem)
        }
        return beerList
    }
}