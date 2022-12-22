package com.example.restaurants.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.restaurants.R
import com.example.restaurants.data.User
import com.example.restaurants.databinding.FragmentRegisterBinding
import com.example.restaurants.viewModel.RegisterViewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.tvHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        val sharedPref = activity?.getSharedPreferences("UserInfoPref", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()

        binding.btnRegister.setOnClickListener {
            if(validateFields()) {
                val name = binding.etName.text.toString()
                val lastName = binding.etLastName.text.toString()
                val email = binding.etEmailR.text.toString()
                val password = binding.etPasswordR.text.toString()
                val foodPreference = binding.sFoodPreference.selectedItem.toString()
                editor?.apply{
                    putString("usrName", name)
                    putString("usrLastName", lastName)
                    putString("usrEmail", email)
                    putString("foodPreference", foodPreference)
                    apply()
                }
                insertDataToDatabase(name, lastName,email,password,foodPreference)
                findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
            }
        }

        // Setting spinner for restaurant preference
        val listPreference = resources.getStringArray(R.array.foodPreference)
        val spinner = view.findViewById<Spinner>(R.id.sFoodPreference)
        if (spinner != null) {
            val adapter = context?.let { ArrayAdapter(it, R.layout.dropdown_item, listPreference) }
            spinner.adapter = adapter
        }

        // Changing visibility of password
        var firstTime = true
        binding.iVisible.setOnClickListener {
            if(firstTime) {
                binding.etPasswordR.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.iVisible.setBackgroundResource(R.drawable.hidden_psw)
                firstTime = !firstTime
            } else {
                binding.etPasswordR.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etPasswordR.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.iVisible.setBackgroundResource(R.drawable.visible_psw)
                firstTime = !firstTime
            }
        }
    }

    private fun validateFields(): Boolean {
        var nameEntered = true
        var lastNameEntered = true
        var emailEntered = true
        var passwordEntered = true
        var repeatPasswordEntered = true
        var foodPreferenceEntered = true
        val listPreference = resources.getStringArray(R.array.foodPreference)

        if(binding.etName.text.toString().isEmpty()) {
            binding.etName.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))
            nameEntered = false
        }
        if(binding.etLastName.text.toString().isEmpty()) {
            binding.etLastName.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))
            lastNameEntered = false
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmailR.text.toString()).matches()){
            binding.etEmailR.error = "Invalid email address"
            binding.etEmailR.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))
            emailEntered = false
        }
        if(binding.etPasswordR.text.toString().isEmpty()) {
            binding.etPasswordR.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))
            passwordEntered = false
        }
        if(binding.etPasswordRepeatR.text.toString().isEmpty()) {
            binding.etPasswordRepeatR.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))
            repeatPasswordEntered = false
        }
        if(binding.etPasswordRepeatR.text.toString() != binding.etPasswordR.text.toString()) {
            binding.etPasswordRepeatR.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))
            binding.etPasswordRepeatR.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.checkmark, 0)
            binding.etPasswordRepeatR.error = "Password mismatch"
            repeatPasswordEntered = false
        } else {
            binding.etPasswordRepeatR.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.correct_psw, 0)
        }
        if(binding.sFoodPreference.selectedItem == listPreference[0]){
            binding.sFoodPreference.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))
            foodPreferenceEntered = false
        }

        if (!nameEntered || !lastNameEntered || !emailEntered || !passwordEntered
            || !repeatPasswordEntered || !foodPreferenceEntered)
            return false

        return true
    }

    private fun insertDataToDatabase(name:String, lastName: String, email:String, password:String, foodPreference: String){
        val user = User(email,name,lastName,password,foodPreference)
        viewModel.addUser(user)
        Toast.makeText(requireContext(), user.toString(), Toast.LENGTH_LONG).show()
    }





}
