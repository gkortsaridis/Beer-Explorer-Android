package gr.gkortsaridis.beerexplorer.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.gkortsaridis.beerexplorer.data.model.Beer
import gr.gkortsaridis.beerexplorer.databinding.RvItemBeerBinding
import gr.gkortsaridis.beerexplorer.databinding.RvItemLoadMoreBinding

class BeersAdapter : RecyclerView.Adapter<BeersAdapter.BeersItemVH>(){

    companion object {
        const val VIEW_TYPE_BEER = 0
        const val VIEW_TYPE_LOAD_MORE = 1
    }

    private var beerItems: List<AdapterItem> = listOf()
    private var clickListener: ClickListener? = null

    override fun getItemViewType(position: Int) =
        when(beerItems[position]) {
            is AdapterItem.BeerItem -> VIEW_TYPE_BEER
            is AdapterItem.LoadMoreItem -> VIEW_TYPE_LOAD_MORE
        }

    override fun getItemCount() = beerItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersItemVH {
        if(viewType == VIEW_TYPE_LOAD_MORE) {
            val binding = RvItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BeersItemVH.LoadMoreVH(binding)
        } else {
            val binding = RvItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BeersItemVH.BeerVH(binding)
        }
    }


    override fun onBindViewHolder(holder: BeersItemVH, position: Int) {
        when(holder) {
            is BeersItemVH.BeerVH -> holder.bind((beerItems[position] as AdapterItem.BeerItem).beer, clickListener)
            is BeersItemVH.LoadMoreVH -> holder.bind(clickListener)
        }
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    fun setBeersToDisplay(beers: List<AdapterItem>) {
        this.beerItems = beers
        notifyDataSetChanged()
    }

    sealed class BeersItemVH(view: View) : RecyclerView.ViewHolder(view){
        data class BeerVH(private val binding: RvItemBeerBinding) : BeersItemVH(binding.root) {
            fun bind(beer: Beer, clickListener: ClickListener?) {
                binding.beer = beer
                binding.root.setOnClickListener { clickListener?.onItemClick(beer) }
            }
        }
        data class LoadMoreVH(private val binding: RvItemLoadMoreBinding) : BeersItemVH(binding.root) {
            fun bind(clickListener: ClickListener?) {
                binding.root.setOnClickListener { clickListener?.onLoadMoreClick() }
            }
        }
    }

    sealed class AdapterItem {
        data class BeerItem(val beer: Beer) : AdapterItem()
        data object LoadMoreItem : AdapterItem()
    }

    interface ClickListener {
        fun onItemClick(beer: Beer)
        fun onLoadMoreClick()
    }
}