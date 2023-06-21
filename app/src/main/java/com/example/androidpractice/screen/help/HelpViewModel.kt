package com.example.androidpractice.screen.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.androidpractice.R
import com.example.androidpractice.domain.model.Category

class HelpViewModel : ViewModel() {
    val categories = liveData<List<Category>> {
        emit(
            listOf(
                Category(R.string.children, R.drawable.ic_children_category),
                Category(R.string.adult, R.drawable.ic_adult_category),
                Category(R.string.old_people, R.drawable.ic_old_category),
                Category(R.string.animals, R.drawable.ic_animals_category),
                Category(R.string.events, R.drawable.ic_events_category)
            )
        )
    }
}
