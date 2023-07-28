package com.example.basicweather.viewmodel

import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicweather.R
import com.example.basicweather.view.SettingsFragment
import io.reactivex.internal.disposables.DisposableHelper.replace

class SettingsViewModel: ViewModel() {

    val openSettingsFragment = MutableLiveData<Boolean>()

    private fun onSettingsButtonClick() {
        openSettingsFragment.value = true
    }
}