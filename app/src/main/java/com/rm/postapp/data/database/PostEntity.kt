package com.rm.postapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey
    val id: Int,
    val body: String,
    val title: String,
    val userId: Int
)
