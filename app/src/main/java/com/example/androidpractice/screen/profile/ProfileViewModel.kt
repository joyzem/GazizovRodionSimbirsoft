package com.example.androidpractice.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class ProfileViewModel : ViewModel() {

    val friends: LiveData<List<String>> = liveData {
        this.emit(
            List(10) {
                "Валерий Дмитриевич"
            }
        )
    }
}