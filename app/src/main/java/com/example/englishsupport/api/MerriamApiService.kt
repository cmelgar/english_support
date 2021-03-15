package com.example.englishsupport.api

import com.example.englishsupport.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MerriamApiService {
    @GET("thesaurus/json/")
    suspend fun getSynonymsAndAntonyms(
        @Query("word")word: String,
        @Query("api_key")api_key: String): String
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val MerriamService = retrofit.create(MerriamApiService::class.java)
}
