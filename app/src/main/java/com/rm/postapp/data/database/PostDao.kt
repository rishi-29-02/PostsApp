package com.rm.postapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert
    suspend fun insertPost(postEntity: List<PostEntity>)

    @Query("SELECT * FROM post")
    fun getAllPost(): Flow<List<PostEntity>>

    @Query("SELECT * FROM post WHERE id = :postId")
    fun getPostID(postId:Int): PostEntity
}