package com.example.restaurants.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurants.R
import com.example.restaurants.adapter.RestaurantsAdapter
import com.example.restaurants.databinding.FragmentHomeBinding
import com.example.restaurants.model.json.Results
import com.example.restaurants.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = HomeFragment()
    }

    private val dataset: ArrayList<Results> = arrayListOf()


    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.btn.setOnClickListener{viewModel.getUpdatedText()}

        addRecycleView()

    }

    private fun addRecycleView(){
        var adapter = RestaurantsAdapter(dataset)

        viewModel.uiTextLiveData.observe(viewLifecycleOwner) { updatedActivity ->
            adapter.updateUserList(updatedActivity.results)
        }
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())


    }


}