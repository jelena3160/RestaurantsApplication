package com.example.restaurants.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.restaurants.R
import com.example.restaurants.databinding.FragmentLoginBinding
import com.example.restaurants.viewModel.LoginViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.tvJoinNow.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            viewModel.getUserByEmail(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            viewModel.user.observe(viewLifecycleOwner, Observer {
                if(it != null) {
                    println(it.email)
                    if(findNavController().currentDestination?.id == R.id.loginFragment){
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    }
                }
                else
                    println("invalid")
            })
        }
    }
}