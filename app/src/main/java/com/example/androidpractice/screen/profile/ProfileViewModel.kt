package com.example.androidpractice.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : ViewModel() {

    val friends: LiveData<List<String>> = liveData {
        emit(
            List(10) {
                "Валерий Дмитриевич"
            }
        )
    }
}
