package com.example.englishsupport

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Word(
    val id: String, val word: String, val definition: String, val synonyms: String,
    val antonyms: String, val save_date: String): Parcelable