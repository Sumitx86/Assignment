package  com.structure.task.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.structure.task.R


fun ImageView.loadImage(uri: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.gray_bg)
        .centerCrop()
        .error(R.mipmap.ic_launcher_round)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}