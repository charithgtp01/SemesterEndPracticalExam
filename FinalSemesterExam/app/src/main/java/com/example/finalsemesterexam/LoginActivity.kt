package com.example.finalsemesterexam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.finalsemesterexam.Constants.Companion.TAG
import com.example.finalsemesterexam.databinding.ActivityLoginBinding
import com.google.gson.Gson


class LoginActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityLoginBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dataBinding.lifecycleOwner = this
        dataBinding.vm = viewModel

        SharedPref(this).clearPref()

        dataBinding.btnSubmit.setOnClickListener {

            val name = viewModel.name
            val passoword = viewModel.passoword

            when {
                name.isBlank() -> {
                    Toast.makeText(this, "Username is mandatory", Toast.LENGTH_SHORT).show()
                }

                passoword.isBlank() -> {
                    Toast.makeText(applicationContext, "Password is mandatory", Toast.LENGTH_SHORT)
                        .show()
                }

                passoword != Constants.PASSWORD || name != Constants.USERNAME -> {
                    Toast.makeText(applicationContext, "Invalid Credentials", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {

                    val user = User(name, passoword)
                    val gson = Gson()
                    SharedPref(this).savePref(
                        Constants.LOGGED_IN_USER,
                        gson.toJson(user),
                        object : UserPreferenceListener {

                            override fun onSuccess() {
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                            }

                            override fun onError(error: String) {
                                Log.d(TAG, "Error")
                            }
                        })
                }
            }
        }
    }
}