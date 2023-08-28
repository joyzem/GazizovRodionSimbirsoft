package com.example.androidpractice.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.androidpractice.core.ui.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : BaseViewModel() {

    val friends: LiveData<List<String>> = liveData {
        emit(
            List(10) {
                "Валерий Дмитриевич"
            }
        )
    }
}
