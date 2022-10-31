package com.example.rickandmorty.view

import com.example.rickandmorty.domain.models.Locations

sealed class LocationsUIModel{
    class Item(val locations: Locations): LocationsUIModel()
    class Header(val text: String): LocationsUIModel()
}
