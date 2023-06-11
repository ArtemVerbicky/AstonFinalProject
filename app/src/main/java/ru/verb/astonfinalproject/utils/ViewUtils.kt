package ru.verb.astonfinalproject.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(link: String) {
    Glide.with(this)
        .load(link)
        .into(this)
}