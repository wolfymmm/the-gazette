package com.example.thegazette.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CommentEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDao
}