package com.example.rickandmorty.view

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.Api.Network
import com.example.rickandmorty.Api.Service
import com.example.rickandmorty.R
import com.example.rickandmorty.model.GetCharacterResponse
import com.example.rickandmorty.model.Result
import com.example.rickandmorty.viewmodel.SharedViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {

    val viewModel: SharedViewModel by lazy{
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profileIV = findViewById<ImageView>(R.id.profileImage)
        val nameTV = findViewById<TextView>(R.id.name)
        val aliveorDeadTV = findViewById<TextView>(R.id.aliveOrDead)
        val orginTV = findViewById<TextView>(R.id.location)
        val speciesTV = findViewById<TextView>(R.id.speciesLabel)

        viewModel.refreshCharacter(68)
        viewModel.charactersLiveData.observe(this){response ->
            if (response == null) {
                Toast.makeText(
                        this@MainActivity,
                        "Unsuccessful network call!",
                        Toast.LENGTH_LONG
                    ).show()
                return@observe
            }
            Picasso.get().load(response.image).into(profileIV)
            nameTV.text = response.name
            aliveorDeadTV.text = response.status
            orginTV.text = response.location.name
            speciesTV.text = response.species
        }

    }
}