package com.example.restaurants.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.restaurants.R
import com.example.restaurants.databinding.FragmentLoginBinding
import com.example.restaurants.viewModel.LoginViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val bundle: Bundle = Bundle()

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val sharedPref =
            requireActivity().getSharedPreferences("UserInfoPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        binding.tvJoinNow.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            if (validateFields()) {
                viewModel.getUserByEmail(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                viewModel.user.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        editor.apply {
                            putString("usrName", it.firstName)
                            putString("usrLastName", it.lastName)
                            putString("usrEmail", it.email)
                            putString("foodPreference", it.restaurantPreference)
                            apply()
                        }
                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                        }
                    } else
                        Toast.makeText(requireContext(), "Incorrect email or password", Toast.LENGTH_LONG).show()
                })
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
                bundle.putString("email", binding.etEmail.text.toString())
                findNavController().navigate(R.id.action_loginFragment_to_resetPassordFragment, bundle)
            }
        }
    }

    private fun validateFields(): Boolean {
        var emailEntered = true
        var passwordEntered = true

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()){
            binding.etEmail.error = "Invalid email address"
            binding.etEmail.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))
            emailEntered = false
        }
        if(binding.etPassword.text.toString().isEmpty()) {
            binding.etPassword.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))
            passwordEntered = false
        }
        if (!emailEntered || !passwordEntered)
            return false
        return true
    }


}