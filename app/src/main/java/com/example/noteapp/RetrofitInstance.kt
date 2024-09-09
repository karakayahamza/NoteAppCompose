package com.example.noteapp

import com.example.noteapp.roomdb.QuoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.api-ninjas.com/v1/"

    val quoteService: QuoteService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteService::class.java)
    }
}