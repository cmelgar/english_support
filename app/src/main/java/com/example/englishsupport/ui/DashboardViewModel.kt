package com.example.englishsupport.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.englishsupport.database.getDatabase
import com.example.englishsupport.repository.EnglishSupportRepository
import java.lang.IllegalArgumentException

class DashboardViewModel (application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val englishSupportRepository = EnglishSupportRepository(database)

    private val _onOptionChanged = MutableLiveData<OptionSelected>()

    val words = Transformations.switchMap(_onOptionChanged) { option ->
        when (option) {
            OptionSelected.RECENT -> englishSupportRepository.wordsList
            OptionSelected.ALL -> englishSupportRepository.wordsList
            OptionSelected.LAST_30 -> englishSupportRepository.wordsList
            else -> englishSupportRepository.wordsList
        }

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                @Suppress("UNCHECKED CAST")
                return DashboardViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}