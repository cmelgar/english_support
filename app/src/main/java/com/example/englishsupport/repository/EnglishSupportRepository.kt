package com.example.englishsupport.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.englishsupport.Word
import com.example.englishsupport.api.Network
import com.example.englishsupport.api.NetworkWordContainer
import com.example.englishsupport.api.asDatabaseModel
import com.example.englishsupport.api.parseWordJsonResult
import com.example.englishsupport.database.WordsDatabase
import com.example.englishsupport.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class EnglishSupportRepository (private val database: WordsDatabase) {

    val wordsList: LiveData<List<Word>> = Transformations.map(database.wordsDao.getWords()) {
        it.asDomainModel()
    }

    suspend fun getWord (word: String, app_key: String) {
        withContext(Dispatchers.IO) {
            try {
                val result = Network.MerriamService.getSynonymsAndAntonyms(
                    word, app_key
                )

                val jsonWord = JSONObject(result)

                val words = parseWordJsonResult(jsonWord)

                database.wordsDao.insertWord(NetworkWordContainer(words).asDatabaseModel())

            } catch (e: Exception) {

            }
        }
    }
}