@file:Suppress("DEPRECATION")

package com.example.airlift.ui.bindingadapters

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.airlift.R
import com.jsibbold.zoomage.ZoomageView

@BindingAdapter("loadImageFromURL")
fun loadImage(
    imgView: ImageView,
    photoUrl
    : String
) {

    // make the circular progress bar to be shown as a placeholder
    val circularProgressDrawable = CircularProgressDrawable(imgView.context)
    circularProgressDrawable.strokeWidth = 7f
    circularProgressDrawable.centerRadius = 40f

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        circularProgressDrawable.colorFilter =
            BlendModeColorFilter(
                ContextCompat.getColor(imgView.context, R.color.purple_200),
                BlendMode.SRC_IN
            )
    } else {
        circularProgressDrawable.setColorFilter(
            ContextCompat.getColor(
                imgView.context,
                R.color.purple_200
            ), PorterDuff.Mode.SRC_IN
        )
    }

    circularProgressDrawable.start()
    Glide.with(imgView.context)
        .load(
            photoUrl
        ).error(R.drawable.ic_image)
        .placeholder(circularProgressDrawable)
        .into(imgView)
}

@BindingAdapter("loadImageIntoZoomable")
fun loadImageIntoZoomable(
    imgView: ZoomageView,
    photoUrl
    : String
) {

    // make the circular progress bar to be shown as a placeholder
    val circularProgressDrawable = CircularProgressDrawable(imgView.context)
    circularProgressDrawable.strokeWidth = 7f
    circularProgressDrawable.centerRadius = 40f

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        circularProgressDrawable.colorFilter =
            BlendModeColorFilter(
                ContextCompat.getColor(imgView.context, R.color.purple_200),
                BlendMode.SRC_IN
            )
    } else {
        circularProgressDrawable.setColorFilter(
            ContextCompat.getColor(
                imgView.context,
                R.color.purple_200
            ), PorterDuff.Mode.SRC_IN
        )
    }

    circularProgressDrawable.start()
    Glide.with(imgView.context)
        .load(
            photoUrl
        ).error(R.drawable.ic_image)
        .placeholder(circularProgressDrawable)
        .into(imgView)
}


