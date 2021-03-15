package com.example.englishsupport.api

import com.example.englishsupport.Word
import com.example.englishsupport.database.DatabaseWords
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkWordContainer(val word: Word)

fun NetworkWordContainer.asDatabaseModel(): DatabaseWords {
    return DatabaseWords(
        word.id,
        word.word,
        word.definition,
        word.synonyms,
        word.antonyms,
        word.save_date
    )
}