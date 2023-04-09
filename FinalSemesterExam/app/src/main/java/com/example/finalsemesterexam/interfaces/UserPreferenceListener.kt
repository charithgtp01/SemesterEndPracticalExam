package com.example.finalsemesterexam.interfaces

interface UserPreferenceListener {
    fun onSuccess()

    fun onError(error: String)
}