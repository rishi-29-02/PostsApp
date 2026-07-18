package com.rm.postapp.data.repository

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostShareManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PostShareManagerImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : PostShareManager {
    override fun sharePost(post: Post) {

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, createShareContent(post))
        }

        val chooserIntent = Intent.createChooser(
            shareIntent,
            "Share Post via"
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(chooserIntent)
        } catch (e: ActivityNotFoundException) {
            Log.e("Share", "No app found to share", e)
        }
    }

    fun createShareContent(post: Post): String {
        return buildString {
            appendLine("📄 Check out this post!")
            appendLine()
            appendLine("Title:")
            appendLine(post.title)
            appendLine()
            append(post.body)

        }
    }
}