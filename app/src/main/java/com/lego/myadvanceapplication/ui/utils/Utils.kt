package com.lego.myadvanceapplication.ui.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.lego.myadvanceapplication.R
import timber.log.Timber

fun loadImage(url: String, context: Context) =
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.ic_launcher_foreground)

fun ImageView.loadImage(url: String, thumbnailURL: String = "") =
    loadImage(url, context)
        .thumbnail(
            Glide
                .with(context)
                .load(thumbnailURL)
        )
        .into(this)


fun ImageView.loadCropImage(url: String) =
    loadImage(url, context)
        .centerCrop()
        .into(this)


fun ImageView.loadGif(url: String) =
    Glide
        .with(this)
        .asGif()
        .load(url)
        .into(this)

fun String.isTypeOrEmpty(): Boolean {
    return this == "image" || this == "default" || this == "self" || this == "nsfw" || this.isEmpty()
}

fun showErrorOrLog(view: View, text: String, showToast: Boolean = false) {
    Timber.d(text)
    if (showToast) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .show()
    }
}
