package com.example.finalsemesterexam.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.finalsemesterexam.*
import com.example.finalsemesterexam.databinding.FragmentLoginBinding
import com.example.finalsemesterexam.entities.User
import com.example.finalsemesterexam.interfaces.UserPreferenceListener
import com.example.finalsemesterexam.ui.login.ViewModel
import com.google.gson.Gson

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: ViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.lifecycleOwner = this
        binding.vm = viewModel

        context?.let { SharedPref(it).clearPref() }

        binding.btnSubmit.setOnClickListener {

            val name = viewModel.name
            val passoword = viewModel.passoword

            when {
                name.isBlank() -> {
                    Toast.makeText(context, "Username is mandatory", Toast.LENGTH_SHORT).show()
                }

                passoword.isBlank() -> {
                    Toast.makeText(context, "Password is mandatory", Toast.LENGTH_SHORT)
                        .show()
                }

                passoword != Constants.PASSWORD || name != Constants.USERNAME -> {
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {

                    val user = User(name, passoword)
                    val gson = Gson()
                    context?.let { it1 ->
                        SharedPref(it1).savePref(
                            Constants.LOGGED_IN_USER,
                            gson.toJson(user),
                            object : UserPreferenceListener {

                                override fun onSuccess() {
                                    goToHome()
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

    private fun goToHome() {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_home_to_nav_home)
    }
}