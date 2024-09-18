package com.example.noteapp

enum class NoteSortOrder(val description: String) {
    DATE_DESC("Tarihe göre azalan"),
    MODIFICATION_DATE_DESC("Değişiklik tarihine göre azalan"),
    HEADLINE_ASC("Başlığa göre artan")
}