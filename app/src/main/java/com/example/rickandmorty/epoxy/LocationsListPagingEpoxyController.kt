package com.example.rickandmorty.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.LocationModelLayoutBinding
import com.example.rickandmorty.databinding.ModelCharacterListTitleBinding
import com.example.rickandmorty.view.LocationsUIModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import java.util.*

@OptIn(ObsoleteCoroutinesApi::class)
class LocationsListPagingEpoxyController():  PagingDataEpoxyController<LocationsUIModel>() {
    override fun buildItemModel(currentPosition: Int, item: LocationsUIModel?): EpoxyModel<*> {
        return when (item!!) {
            is LocationsUIModel.Item -> {
                val location = (item as LocationsUIModel.Item).locations
                LocationGridItemEpoxyModel(
                    name = location.name,
                    id = location.id
                ).id(item.locations.id)
            }

            is LocationsUIModel.Header -> {
                val headerText = (item as LocationsUIModel.Header).text
                LocationsGridTitleEpoxyModel(headerText)
                    .id(headerText)
            }
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        LocationsGridTitleEpoxyModel("Locations").id("locations header")
        (models.subList(0, models.size) as List<LocationGridItemEpoxyModel>).groupBy {
            it.name[0].uppercaseChar()
        }.forEach { mapEntry ->
            val location = mapEntry.key.toString().uppercase(Locale.US)
            LocationsGridTitleEpoxyModel(location).id(location).addTo(this)
            super.addModels(mapEntry.value)
        }
    }



    data class LocationGridItemEpoxyModel(
            val name: String,
            val id: Int
        ) : ViewBindingKotlinModel<LocationModelLayoutBinding>(R.layout.location_model_layout) {
            override fun LocationModelLayoutBinding.bind() {
                    locationName.text = name
                root.setOnClickListener {

                }
            }
        }

        data class LocationsGridTitleEpoxyModel(val title: String) :
            ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
            override fun ModelCharacterListTitleBinding.bind() {
                titleTV.text = title
            }

            override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
                return totalSpanCount
            }
        }
}