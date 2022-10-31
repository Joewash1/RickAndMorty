package com.example.rickandmorty.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentLocationsListBinding
import com.example.rickandmorty.epoxy.CharacterDetailsEpoxyController
import com.example.rickandmorty.epoxy.LocationsListPagingEpoxyController
import com.example.rickandmorty.viewmodel.LocationsListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationsListFragment : Fragment(R.layout.fragment_locations_list) {

    private var _binding: FragmentLocationsListBinding? = null
    private val binding get() = _binding!!


    private val viewModel: LocationsListViewModel by viewModels()
    val epoxyController = LocationsListPagingEpoxyController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locations_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLocationsListBinding.bind(view)



        viewModel.locationByIdLiveData.observe(viewLifecycleOwner) { location ->
            epoxyController

            if(location == null){
                Toast.makeText(
                    requireActivity(),
                    "Unsuccessful network call!",
                    Toast.LENGTH_LONG
                ).show()
                return@observe
            }
        }


        lifecycleScope.launch {
            viewModel.flow.collectLatest { value: PagingData<LocationsUIModel> ->
                epoxyController.submitData(value)
            }
        }
        binding.epoxyRecyclerView.setController(epoxyController)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}