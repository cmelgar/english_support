package com.example.englishsupport.api

import com.example.englishsupport.Constants
import com.example.englishsupport.Word
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

fun parseWordJsonResult(jsonResult: JSONObject): Word {
    val wordResult = jsonResult.getJSONObject("meta")
    val wordDef = jsonResult.getJSONArray("shortdef")
    val syns = JSONArray(wordResult.getString("syns"))
    val ants = JSONArray(wordResult.getString("ants"))

    val id = wordResult.getString("id")
    val word = wordResult.getString("id")
    val definition = wordDef[0].toString()
    val synonyms = if (syns.length() > 0) syns[0].toString() else ""
    val antonyms = if (ants.length() > 0) ants[0].toString() else ""

    return Word(id, word, definition, synonyms, antonyms, getToday())
}

fun getImageUrl(jsonResult: JSONObject): String {
    val imageResult = jsonResult.getJSONObject("value")

    return imageResult["webSearchUrl"].toString()
}

fun getToday(): String {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime)
}