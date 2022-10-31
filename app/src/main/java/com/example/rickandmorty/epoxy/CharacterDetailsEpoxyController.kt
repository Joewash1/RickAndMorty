package com.example.rickandmorty.epoxy

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ModelCharacterDetailsDataBinding
import com.example.rickandmorty.databinding.ModelCharacterDetailsHeaderBinding
import com.example.rickandmorty.databinding.ModelCharacterDetailsImageBinding
import com.example.rickandmorty.domain.models.Character
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController : EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var character: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            //show loading state
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        if (character == null) {
            //todo error state
            return
        }
        //add the Header model
        HeaderEpoxyModel(
            names = character!!.name,
            status = character!!.status,
        ).id("header").addTo(this)

        //add header model
        ImageEpoxyModel(imageUrl = character!!.image).id("header").addTo(this)

        //Add details models
        HeaderEpoxyModel.DetailsEpoxyModel(
            title = "Origin",
            description = character!!.origin.name
        ).id("origin").addTo(this)
        HeaderEpoxyModel.DetailsEpoxyModel(
            title = "Species",
            description = character!!.species
        ).id("species").addTo(this)

    }


    data class ImageEpoxyModel(
        val imageUrl: String,
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(imageUrl).into(profileImage)
        }
    }

    data class HeaderEpoxyModel(
        val names: String,
        val status: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            name.text = names
            aliveOrDead.text = status
        }


        data class DetailsEpoxyModel(
            val title: String,
            val description: String,
        ) : ViewBindingKotlinModel<ModelCharacterDetailsDataBinding>(R.layout.model_character_details_data) {
            override fun ModelCharacterDetailsDataBinding.bind() {
                location.text = title
                speciesLabel.text = description
            }
        }

    }
}