package com.example.englishsupport.api

import com.example.englishsupport.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

// "x-rapidapi-key:3eff61cfd1msh804391be24c1c11p1114c6jsnd2ccffbf7179",
interface BingImageApiService {
    @Headers("Ocp-Apim-Subscription-Key: 95b381b8b0c048488e20e3bca6713f52")
    @GET("v7.0/images/search")
    suspend fun getImageFromWord(
        @Header("Ocp-Apim-Subscription-Key")api_key: String,
        @Query("q")word: String): String
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object ImageNetwork {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_IMAGE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val BingApiService: BingImageApiService = retrofit.create(BingImageApiService::class.java)
}