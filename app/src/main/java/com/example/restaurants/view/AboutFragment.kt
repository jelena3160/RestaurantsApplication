package com.example.restaurants.view

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.restaurants.R
import com.example.restaurants.data.User
import com.example.restaurants.databinding.FragmentAboutBinding
import com.example.restaurants.databinding.FragmentRegisterBinding
import com.example.restaurants.viewModel.AboutViewModel

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AboutFragment()
    }

    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AboutViewModel::class.java]

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences("UserInfoPref", Context.MODE_PRIVATE)
        val email = sharedPref.getString("usrEmail", "")

        binding.btnLogout.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

    }

}