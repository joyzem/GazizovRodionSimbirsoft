package com.example.androidpractice.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {

    private val _isLoginButtonEnabled = MutableLiveData(false)
    val isLoginButtonEnabled: LiveData<Boolean> = _isLoginButtonEnabled

    private val _passwordIsHidden = MutableLiveData(true)
    val passwordIsHidden: LiveData<Boolean> = _passwordIsHidden

    fun changePasswordVisibility() {
        _passwordIsHidden.postValue(!passwordIsHidden.value!!)
    }

    fun setLoginButtonEnabled(enabled: Boolean) {
        _isLoginButtonEnabled.postValue(enabled)
    }
}
