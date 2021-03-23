package com.icheero.sdk.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var dismissDialog: MutableLiveData<Unit> = MutableLiveData()
    var finishActivity: MutableLiveData<Unit> = MutableLiveData()
    fun dismissDialog() {
        dismissDialog.value = null
    }
}