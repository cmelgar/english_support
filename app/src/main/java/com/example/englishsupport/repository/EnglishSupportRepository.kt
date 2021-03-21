package com.example.englishsupport.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.englishsupport.Word
import com.example.englishsupport.api.*
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

    suspend fun getImageFromWord (word: String, api_key: String): String? {
        var image:String? = null

        withContext(Dispatchers.IO) {

            try {
                val result = ImageNetwork.BingApiService.getImageFromWord(word, api_key)

                val imageJson = JSONObject(result)

                image = getImageUrl(imageJson)

            } catch (e: JSONException) {

            }


        }
        return image
    }

    suspend fun getWord (word: String, app_key: String) {
        withContext(Dispatchers.IO) {
            try {

                val result = Network.MerriamService.getSynonymsAndAntonyms(
                    word, app_key
                )

                // Convert to Array due to it returns []
                val jsonWord = JSONArray(result)

                // Convert to Object to work with the string
                val jsonObject = JSONObject(jsonWord[0].toString())

                val words = parseWordJsonResult(jsonObject)

                database.wordsDao.insertWord(NetworkWordContainer(words).asDatabaseModel())

            } catch (e: JSONException) {
                Log.e("getWord Somethin", e.message.toString())
            }
        }
    }
}