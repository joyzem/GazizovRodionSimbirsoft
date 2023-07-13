package com.example.androidpractice.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidpractice.ui.BaseViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor() : BaseViewModel() {

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
