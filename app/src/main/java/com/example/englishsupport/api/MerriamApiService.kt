package com.example.englishsupport.api

import com.example.englishsupport.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MerriamApiService {
    @GET("thesaurus/json/{word}")
    suspend fun getSynonymsAndAntonyms(
        @Path("word")word: String,
        @Query("key")api_key: String): String
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    val MerriamService: MerriamApiService = retrofit.create(MerriamApiService::class.java)
}
