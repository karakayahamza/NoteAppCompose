package com.example.noteapp.data.model

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("quote") var quote: String? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("category") var category: String? = null
)