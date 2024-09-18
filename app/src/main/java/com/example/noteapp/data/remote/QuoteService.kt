package com.example.noteapp.data.remote

import com.example.noteapp.data.model.Quote
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QuoteService {
    @GET("quotes")
    suspend fun getQuotes(
        @Query("category") category: String = "happiness",
        @Header("X-Api-Key") apiKey: String
    ): List<Quote>
}

// BASE = https://api.api-ninjas.com/v1/
//API=KEY EF6jRXcKZbTQqQAdPk5lkg==W63eMgYAQh2fYiTp
//quotes?category=happiness&=