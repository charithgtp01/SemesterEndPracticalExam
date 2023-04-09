package com.example.finalsemesterexam.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.finalsemesterexam.Constants
import com.example.finalsemesterexam.R
import com.example.finalsemesterexam.SharedPref
import com.example.finalsemesterexam.entities.User
import com.example.finalsemesterexam.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        val userString = activity?.let { SharedPref(it).getPref(Constants.LOGGED_IN_USER) }
        val gson = Gson()

        val loggedInUser = gson.fromJson(userString, User::class.java)
        binding.textHome.text = "Welcome ${loggedInUser.username}"

        binding.btnSettings.setOnClickListener {
            goToSettings()
        }
        return root
    }

    private fun goToSettings() {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_home_to_nav_settings)
    }
}