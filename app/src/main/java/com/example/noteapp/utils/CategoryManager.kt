package com.example.noteapp.utils

import kotlin.random.Random

class CategoryManager {
    companion object {
        private val categories = Category.entries.toTypedArray()

        fun getRandomCategory(): String {
            return categories[Random.nextInt(categories.size)].name.lowercase()
        }
    }
}