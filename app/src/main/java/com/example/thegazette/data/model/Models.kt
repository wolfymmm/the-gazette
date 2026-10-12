package com.example.thegazette.data.model

import androidx.annotation.DrawableRes

// DTO з мережі
data class ArticleDto(
    val id: Int,
    val title: String,
    val description: String,
    val author: String,
    val publishedAt: String,
    val category: String,
    val imageUrl: String,
    val contentParagraphs: List<String>
)

// Профіль користувача
data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    @DrawableRes val avatarRes: Int
)