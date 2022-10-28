package com.example.rickandmorty

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String, imageView: ImageView){
    Picasso.get().load(url).into(imageView);
}