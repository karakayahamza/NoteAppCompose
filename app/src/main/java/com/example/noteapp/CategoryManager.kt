package com.example.noteapp

import com.example.noteapp.model.Category
import kotlin.random.Random

class CategoryManager {
    companion object {
        private val categories = Category.values()

        fun getRandomCategory(): String {
            return categories[Random.nextInt(categories.size)].name.lowercase()
        }
    }
}