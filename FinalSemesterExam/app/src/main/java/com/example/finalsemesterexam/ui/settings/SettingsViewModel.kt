package com.example.finalsemesterexam.ui.settings

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    @get:Bindable
    var name: String = ""

    @get:Bindable
    var passoword: String = ""


}