package com.lego.myadvanceapplication.ui

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lego.myadvanceapplication.R

fun loadImage(url: String, context: Context) =
    Glide
        .with(context)
//      .placeholder(R.drawable.loading_spinner) //todo make placeholder for images
        .load(url)

fun ImageView.loadImage(url: String) =
    loadImage(url, context)
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
