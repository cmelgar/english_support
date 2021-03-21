package com.example.englishsupport.repository

import android.util.Log
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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class EnglishSupportRepository (private val database: WordsDatabase) {

    val wordsList: LiveData<List<Word>> = Transformations.map(database.wordsDao.getWords()) {
        it.asDomainModel()
    }


    val words30List: LiveData<List<Word>> = Transformations.map(database.wordsDao.getLastThirtyWords()) {
        it.asDomainModel()
    }

    val wordsRecentList: LiveData<List<Word>> = Transformations.map(database.wordsDao.getRecentWords()) {
        it.asDomainModel()
    }

    suspend fun getWord (word: String, app_key: String) {
        withContext(Dispatchers.IO) {
            try {

                val result = Network.MerriamService.getSynonymsAndAntonyms(
                    word, app_key
                )
                Log.e("getWord1", result)

                val jsonWord = JSONArray(result)
                Log.e("getWord2", jsonWord[0].toString())

                val jsonObject = JSONObject(jsonWord[0].toString())

                val words = parseWordJsonResult(jsonObject)
                Log.e("getWord3", "words")

                database.wordsDao.insertWord(NetworkWordContainer(words).asDatabaseModel())

            } catch (e: JSONException) {
                Log.e("getWord Somethin", e.message.toString())
            }
        }
    }
}