package com.example.englishsupport.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.englishsupport.R
import com.example.englishsupport.Word
import com.example.englishsupport.database.getDatabase
import com.example.englishsupport.repository.EnglishSupportRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

enum class MerriamWordsStatus { LOADING, ERROR, DONE }

class DashboardViewModel (application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val englishSupportRepository = EnglishSupportRepository(database)
    private val MerriamApiKey = application.resources.getString(R.string.merriam_api_key)
    private val BingApiKey = application.resources.getString(R.string.bing_api_key)

    private val _onOptionChanged = MutableLiveData<OptionSelected>()

    private val _status = MutableLiveData<MerriamWordsStatus>()
    val status: LiveData<MerriamWordsStatus>
        get() = _status

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word>
        get() = _word

    private val _wordImageUrl = MutableLiveData<String>()
    val wordImageUrl: LiveData<String>
        get() = _wordImageUrl

    val words: LiveData<List<Word>> = Transformations.switchMap(_onOptionChanged) { option ->
        when (option) {
            OptionSelected.RECENT -> englishSupportRepository.wordsRecentList
            OptionSelected.ALL -> englishSupportRepository.wordsList
            OptionSelected.LAST_30 -> englishSupportRepository.words30List
        }
    }

    private val _showNoContent = MutableLiveData<Boolean>()
    val showNoContent: LiveData<Boolean>
        get() = _showNoContent

    init {
        _status.value = MerriamWordsStatus.LOADING
        viewModelScope.launch {
            if (words.value?.size == 0) {
                _showNoContent.value = true
            }
            showOptionSelected(OptionSelected.RECENT)
            _status.value = MerriamWordsStatus.DONE
        }
    }

    fun searchWord(word: String) {
        viewModelScope.launch {
            _status.value = MerriamWordsStatus.LOADING
            englishSupportRepository.getWord(word, MerriamApiKey)
            _status.value = MerriamWordsStatus.DONE
        }
    }

    fun getImageFromWord(word: String) {
        viewModelScope.launch {
//            _status.value = MerriamWordsStatus.LOADING
            _wordImageUrl.value = englishSupportRepository.getImageFromWord(word, BingApiKey)
//            _status.value = MerriamWordsStatus.DONE
        }
    }

    fun setLoadingStatusDone() {
        _status.value = MerriamWordsStatus.DONE
    }

    fun showOptionSelected(optionSelected: OptionSelected) {
        _onOptionChanged.value = optionSelected
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