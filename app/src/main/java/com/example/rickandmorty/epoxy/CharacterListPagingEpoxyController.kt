package com.example.rickandmorty.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.EpoxyModelLayoutBinding
import com.example.rickandmorty.databinding.ModelCharacterListTitleBinding
import com.example.rickandmorty.model.Result
import com.example.rickandmorty.view.CharacterUIModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ObsoleteCoroutinesApi
import java.util.*


@ObsoleteCoroutinesApi
class CharacterListPagingEpoxyController(private val onCharacterSelected: (Int) -> Unit) :
    PagingDataEpoxyController<CharacterUIModel>() {

    override fun buildItemModel(currentPosition: Int,
                                item:CharacterUIModel?): EpoxyModel<*> {
        return when (item!!) {
            is CharacterUIModel.Item -> {
                val character = (item as CharacterUIModel.Item).character
                CharacterGridItemEpoxyModel(
                    imageUrl = character!!.image,
                    name = character.name,
                    id = character.id,
                    onCharacterSelected = onCharacterSelected
                ).id(item.character.id)
            }

            is CharacterUIModel.Header -> {
                val headerText = (item as CharacterUIModel.Header).text
                CharacterGridTitleEpoxyModel(headerText).id(headerText)
            }
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        CharacterGridTitleEpoxyModel("Main Family").id("main_family_header").addTo(this)
        super.addModels(models.subList(0, 5))

        (models.subList(5, models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.name[0].uppercaseChar()
        }.forEach { mapEntry ->
            val character = mapEntry.key.toString().uppercase(Locale.US)
            CharacterGridTitleEpoxyModel(character).id(character).addTo(this)
            super.addModels(mapEntry.value)
        }
    }

    data class CharacterGridItemEpoxyModel(
        val imageUrl: String,
        val name: String,
        val onCharacterSelected: (Int) -> Unit,
        val id: Int
    ) : ViewBindingKotlinModel<EpoxyModelLayoutBinding>(R.layout.epoxy_model_layout) {
        override fun EpoxyModelLayoutBinding.bind() {
            Picasso.get().load(imageUrl).into(characterPicIV)
            charName.text = name

            root.setOnClickListener {
                 onCharacterSelected(id)
            }
        }
    }

    data class CharacterGridTitleEpoxyModel(val title: String) :
        ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
        override fun ModelCharacterListTitleBinding.bind() {
            titleTV.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}