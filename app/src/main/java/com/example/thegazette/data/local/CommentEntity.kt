package com.example.thegazette.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val articleId: Int,
    val userId: String,
    val userName: String,
    val userAvatarRes: Int,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)