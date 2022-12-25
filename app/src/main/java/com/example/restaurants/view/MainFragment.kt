package com.example.restaurants.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurants.R
import com.example.restaurants.viewModel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        replaceFragment(HomeFragment())
        val bottomNavigationView =
            view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment ->replaceFragment(HomeFragment())
                R.id.favoritesFragment ->replaceFragment(FavoritesFragment())
                R.id.aboutFragment ->replaceFragment(AboutFragment())
                else ->{

                }
            }
            true
        }
    }
    private fun replaceFragment(fragment:Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.commit()
    }

}