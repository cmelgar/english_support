package com.example.englishsupport.api

import com.example.englishsupport.Constants
import com.example.englishsupport.Word
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

fun parseWordJsonResult(jsonResult: JSONObject): Word {
    val wordResult = jsonResult.getJSONArray("meta")
    System.out.println(wordResult)
    var wordEntity: Word? = null

    for (i in 0 until wordResult.length()) {
        val wordList = wordResult.getJSONObject(i)

        val id = wordList.getString("id")
        val word = wordList.getString("id")
        val definition = wordList.getString("shortdef")
        val synonyms = wordList.getString("syns")
        val antonyms = wordList.getString("ants")

        wordEntity = Word(id, word, definition, synonyms, antonyms, getToday())
    }

    return wordEntity!!
}

fun getToday(): String {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime)
}