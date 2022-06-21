package com.rsuly.expansetracker.view.about

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor() : ViewModel() {

    private val _url = MutableStateFlow("https://github.com/rsuly") //TODO: fix it
    val url: StateFlow<String> = _url

    fun launchLicence() {
        _url.value = "https://github.com/.../LICENCE"
    }

    fun launchRepository() {
        _url.value = "https://github.com/.../Expenso"
    }
}