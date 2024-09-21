package com.example.noteapp.data.local.model

enum class NoteSortOrder(val description: String) {
    DATE_DESC("Tarihe göre"),
    MODIFICATION_DATE_DESC("Değişiklik tarihine göre"),
    HEADLINE_ASC("Başlığa göre")
}