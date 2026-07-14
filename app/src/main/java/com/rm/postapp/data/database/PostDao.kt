package com.rm.postapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    suspend fun insertPost(postEntity: List<PostEntity>)

    @Query("SELECT * FROM post")
    suspend fun getAllPost(): List<PostEntity>

    @Query("SELECT * FROM post WHERE id = :postId")
    suspend fun getPostID(postId:Int): PostEntity
}