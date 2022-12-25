package com.example.restaurants.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurants.R
import com.example.restaurants.data.UserPassword
import com.example.restaurants.databinding.FragmentLoginBinding
import com.example.restaurants.databinding.FragmentResetPassordBinding
import com.example.restaurants.viewModel.ResetPasswordViewModel

class ResetPasswordFragment : Fragment() {


    private var _binding: FragmentResetPassordBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ResetPasswordFragment()
    }

    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPassordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]

        binding.btnChangePassword.setOnClickListener{
            viewModel.updatePassword(UserPassword(requireArguments().getString("email"), binding.etNewPassword.text.toString()))
            findNavController().navigate(R.id.action_resetPassordFragment_to_loginFragment)
        }


    }



}