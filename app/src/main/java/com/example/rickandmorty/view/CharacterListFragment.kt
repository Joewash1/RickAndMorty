package com.example.rickandmorty.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterListBinding
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.epoxy.CharacterListPagingEpoxyController
import com.example.rickandmorty.viewmodel.CharactersViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharacterListFragment : Fragment(R.layout.fragment_character_list) {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels()

    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterListBinding.bind(view)

        val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData: PagingData<CharacterUIModel> ->
                epoxyController.submitData(pagingData)
            }
        }
        binding.epoxyRecyclerView.setController(epoxyController)
        }

    private fun onCharacterSelected(id: Int){
        val directions = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(characterId = id)
        findNavController().navigate(directions)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    }
