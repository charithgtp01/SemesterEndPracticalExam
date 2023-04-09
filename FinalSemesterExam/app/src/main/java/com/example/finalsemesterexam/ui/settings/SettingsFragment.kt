package com.example.finalsemesterexam.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalsemesterexam.*
import com.example.finalsemesterexam.databinding.FragmentSettingsBinding
import com.google.gson.Gson

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.lifecycleOwner = this
        binding.vm = viewModel

        val userString = activity?.let { SharedPref(it).getPref(Constants.LOGGED_IN_USER) }
        val gson = Gson()

        val loggedInUser = gson.fromJson(userString, User::class.java)
        viewModel.name = loggedInUser.username
        viewModel.passoword = loggedInUser.password

        binding.btnSubmit.setOnClickListener {

            val name = viewModel.name
            val password = viewModel.passoword

            when {
                name.isBlank() -> {
                    Toast.makeText(context, "Username is mandatory", Toast.LENGTH_SHORT).show()
                }

                password.isBlank() -> {
                    Toast.makeText(context, "Password is mandatory", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {

                    val user = User(name, password)
                    val gson = Gson()
                    context?.let { it1 ->
                        SharedPref(it1).savePref(
                            Constants.LOGGED_IN_USER,
                            gson.toJson(user),
                            object : UserPreferenceListener {

                                override fun onSuccess() {
                                    findNavController().popBackStack()
                                }

                                override fun onError(error: String) {
                                    Log.d(Constants.TAG, "Error")
                                }
                            })
                    }
                }
            }
        }
        return root
    }
}