package gr.gkortsaridis.beerexplorer.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageBindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, url: String?) {
        if(url != null) {
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }
    }
}