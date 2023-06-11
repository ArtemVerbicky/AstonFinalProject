package ru.verb.astonfinalproject.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.verb.astonfinalproject.R

fun ImageView.loadImage(link: String) {
    Glide.with(this)
        .load(link)
        .into(this)
}

fun BottomNavigationView.setListener() {
    setOnItemSelectedListener {
        when(it.itemId) {
            R.id.characters -> {
                true
            }
            R.id.locations -> {
                true
            }
            R.id.episodes -> {
                true
            }
            else -> false
        }
    }
}