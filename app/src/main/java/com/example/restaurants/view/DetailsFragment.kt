package com.example.restaurants.view

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.restaurants.R
import com.example.restaurants.databinding.FragmentDetailsBinding
import com.example.restaurants.databinding.FragmentFavoritesBinding
import com.example.restaurants.viewModel.DetailsViewModel
import com.example.restaurants.viewModel.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import java.net.URL

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        setToolbar()
        unpackInfo()


        val latitude = requireArguments().getDouble("latitude")
        val longitude = requireArguments().getDouble("longitude")
        val url = requireArguments().getString("url")

        binding.tvGoogleLocation.setOnClickListener{
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$latitude,$longitude?z=15"))
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.tvMore.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        binding.tvNavigate.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$latitude,$longitude"))
            intent.setPackage("com.google.android.apps.maps")
            intent.resolveActivity(requireContext().packageManager)?.let {
                startActivity(intent)
            }
        }
    }

    private fun setToolbar(){
        requireActivity().setActionBar(binding.toolbar)
        requireActivity().actionBar?.setDisplayShowHomeEnabled(true)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        requireActivity().actionBar?.title = "Details"

    }

    private fun unpackInfo(){
        Glide.with(requireContext()).load(requireArguments().getString("picture")).into(binding.iRestaurantPicture)
        binding.tvName.text = requireArguments().getString("name")
        binding.tvLocationD.text = requireArguments().getString("location")
        binding.tvSnippet.text = requireArguments().getString("snippet")

    }



}