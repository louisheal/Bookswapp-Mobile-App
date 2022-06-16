package ac.ic.bookapp

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageURL")
public fun bindImage(view: ImageView, imgURL: String?) {
    imgURL?.let {
        val imgURI = imgURL.toUri().buildUpon().scheme("https").build()
        view.load(imgURI)
    }
}