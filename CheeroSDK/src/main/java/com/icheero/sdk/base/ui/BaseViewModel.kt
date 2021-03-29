package com.icheero.sdk.base.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    override fun onCleared() {
        super.onCleared()
    }
}