package com.example.englishsupport.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.englishsupport.Word

@Entity
data class DatabaseWords constructor(
    @PrimaryKey
    val id: String,
    val word: String,
    val definition: String,
    val synonyms: String,
    val antonyms: String,
    val save_date: String
)

fun List<DatabaseWords>.asDomainModel(): List<Word> {
    return map {
        Word(
            it.id,
            it.word,
            it.definition,
            it.synonyms,
            it.antonyms,
            it.save_date
        )
    }
}