package com.example.englishsupport.data

import com.example.englishsupport.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MerriamApiService {
    @GET("thesaurus/json/")
    suspend fun getSynonymsAndAntonyms(
        @Query("word")word: String,
        @Query("api_key")api_key: String)

}

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .build()