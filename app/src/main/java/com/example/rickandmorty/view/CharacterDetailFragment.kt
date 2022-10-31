package com.example.rickandmorty.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.example.rickandmorty.epoxy.CharacterDetailsEpoxyController
import com.example.rickandmorty.viewmodel.CharacterDetailViewModel

import kotlinx.coroutines.ObsoleteCoroutinesApi



class CharacterDetailFragment : Fragment() {


    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val epoxyController = CharacterDetailsEpoxyController()
    private val safeArgs: CharacterDetailFragmentArgs by navArgs()
    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }
    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterDetailBinding.bind(view)

        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) {character ->

            epoxyController.character = character

            if (character == null) {
                Toast.makeText(
                    requireActivity(),
                    "Unsuccessful network call!",
                    Toast.LENGTH_LONG
                ).show()
                return@observe
            }
        }
        viewModel.fetchCharacter(characterId = safeArgs.characterId)

      binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}