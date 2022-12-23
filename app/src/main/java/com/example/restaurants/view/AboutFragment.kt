package com.example.restaurants.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.restaurants.R
import com.example.restaurants.data.FoodPreference
import com.example.restaurants.data.UserLastName
import com.example.restaurants.data.UserName
import com.example.restaurants.databinding.FragmentAboutBinding
import com.example.restaurants.viewModel.AboutViewModel
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private var pickedPhoto: Uri? = null
    private var pickedBitMap: Bitmap? = null

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

        // Setting spinner for food preference
        val listPreference = resources.getStringArray(R.array.foodPreference)
        val spinner = view.findViewById<Spinner>(R.id.sFoodPreferenceAbout)
        if (spinner != null) {
            val adapter = context?.let { ArrayAdapter(it, R.layout.dropdown_item_about, listPreference) }
            spinner.adapter = adapter
        }
        // Initially
        binding.etNameAbout.setText(viewModel.getName())
        binding.etLastNameAbout.setText(viewModel.getLastName())
        binding.etEmailAbout.setText(viewModel.getEmail())
        val pos = viewModel.getFoodPreference()
        val index = listPreference.indexOf(pos)
        binding.sFoodPreferenceAbout.setSelection(index)
        binding.sFoodPreferenceAbout.isEnabled = false
        binding.etNameAbout.isEnabled = false
        binding.etLastNameAbout.isEnabled = false
        binding.etEmailAbout.isEnabled = false

        // Uploading image from library
        // TODO Save image to database in order to stay
        binding.iAddProfilePic.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            }
            else {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, 2)
            }
        }
        var saveIcon = false
        binding.iNameChanger.setOnClickListener {
            if(!saveIcon) {
                binding.etNameAbout.isEnabled = true
                binding.iNameChanger.setImageResource(R.drawable.ic_baseline_save_24)
                saveIcon = true
            } else {
                binding.etNameAbout.isEnabled = false
                binding.iNameChanger.setImageResource(R.drawable.marker)
                viewModel.setName(binding.etNameAbout.text.toString())
                viewModel.updateName(UserName(viewModel.getEmail()!!,viewModel.getName()))
                saveIcon = false
            }
        }

        var saveIconLastName = false
        binding.iLastNameChanger.setOnClickListener {
            if(!saveIconLastName) {
                binding.etLastNameAbout.isEnabled = true
                binding.iLastNameChanger.setImageResource(R.drawable.ic_baseline_save_24)
                saveIconLastName = true
            } else {
                binding.etLastNameAbout.isEnabled = false
                binding.iLastNameChanger.setImageResource(R.drawable.marker)
                viewModel.setLastName(binding.etLastNameAbout.text.toString())
                viewModel.updateLastName(UserLastName(viewModel.getEmail()!!, viewModel.getLastName()))
                saveIconLastName = false
            }
        }

        var saveIconPreference = false
        binding.iFoodPreferenceChanger.setOnClickListener {
            if(!saveIconPreference) {
                binding.sFoodPreferenceAbout.isEnabled = true
                binding.iFoodPreferenceChanger.setImageResource(R.drawable.ic_baseline_save_24)
                saveIconPreference = true
            } else {
                binding.sFoodPreferenceAbout.isEnabled = false
                binding.iFoodPreferenceChanger.setImageResource(R.drawable.marker)
                viewModel.setFoodPreference(sFoodPreferenceAbout.selectedItem.toString())
                viewModel.updateFoodPreference(FoodPreference(viewModel.getEmail()!!, viewModel.getFoodPreference()))
                saveIconPreference = false
            }
        }


        // Clearing shared pref and returning to login fragment
        binding.btnLogout.setOnClickListener{
            viewModel.clearData()
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
            pickedPhoto = data.data
            if(pickedPhoto != null) {
                if(Build.VERSION.SDK_INT >= 28){
                    val source = ImageDecoder.createSource(requireContext().contentResolver, pickedPhoto!!)
                    pickedBitMap = ImageDecoder.decodeBitmap(source)
                    binding.iProfilePic.setImageBitmap(pickedBitMap)
                } else {
                    pickedBitMap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, pickedPhoto)
                    binding.iProfilePic.setImageBitmap(pickedBitMap)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }






}