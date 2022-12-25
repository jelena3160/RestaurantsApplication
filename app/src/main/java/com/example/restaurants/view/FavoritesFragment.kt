package com.example.restaurants.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurants.adapter.FavoritesAdapter
import com.example.restaurants.databinding.FragmentFavoritesBinding
import com.example.restaurants.model.json.Results
import com.example.restaurants.viewModel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private val dataset: ArrayList<Results> = arrayListOf()
    private lateinit var viewModel: FavoritesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        val preference = StringBuilder()
        preference.append("cuisine-")
        preference.append(viewModel.getFoodPreference())
        viewModel.getUpdatedText("Paris",preference.toString())
        addRecycleView()

    }

    private fun addRecycleView(){
        var adapter = FavoritesAdapter(dataset)

        viewModel.uiTextLiveData.observe(viewLifecycleOwner) { updatedActivity ->
            adapter.updateUserList(updatedActivity.results)
        }
        binding.rvFavorites.setHasFixedSize(true)
        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())


    }
}