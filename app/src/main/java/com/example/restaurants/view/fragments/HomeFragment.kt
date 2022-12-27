package com.example.restaurants.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurants.R
import com.example.restaurants.handler.RestaurantClickHandler
import com.example.restaurants.adapter.HomeAdapter
import com.example.restaurants.databinding.FragmentHomeBinding
import com.example.restaurants.model.json.Results
import com.example.restaurants.viewModel.HomeViewModel

class HomeFragment : Fragment(), RestaurantClickHandler {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val dataset: ArrayList<Results> = arrayListOf()


    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getUpdatedText()
        addRecycleView()
        setToolbar()

    }

    private fun addRecycleView(){
        val adapter = HomeAdapter(dataset, this)
        viewModel.uiTextLiveData.observe(viewLifecycleOwner) { updatedActivity ->
            adapter.updateUserList(updatedActivity.results)
            binding.lottieHome.visibility = View.GONE
        }
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())


    }

    override fun clickedRestaurantItem(restaurantsResponse: Results) {
        val bundle = packInfo(restaurantsResponse)
        findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
    }

    private fun packInfo(results: Results): Bundle{
        val bundle = Bundle()
        bundle.putString("name", results.name)
        bundle.putString("location", results.locationId)
        bundle.putString("snippet", results.snippet)
        bundle.putString("picture", results.images[0].sizes?.original?.url)
        results.coordinates?.latitude?.let { bundle.putDouble("latitude", it) }
        results.coordinates?.longitude?.let { bundle.putDouble("longitude", it) }
        bundle.putString("url", results.attribution[1].url)
        return bundle
    }
    private fun setToolbar(){
        requireActivity().setActionBar(binding.toolbar)
        requireActivity().actionBar?.setDisplayShowHomeEnabled(true)
        requireActivity().actionBar?.title = "Home"
    }


}