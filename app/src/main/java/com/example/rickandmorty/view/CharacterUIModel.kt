package com.example.rickandmorty.view

import com.example.rickandmorty.domain.models.Character

sealed class CharacterUIModel {
    class Item(val character: Character): CharacterUIModel()
    class Header(val text: String): CharacterUIModel()
}