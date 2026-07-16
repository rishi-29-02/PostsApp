package com.rm.postapp.data.repository

import android.content.Context
import android.content.Intent
import com.rm.postapp.domain.models.Post
import com.rm.postapp.domain.repository.PostShareManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PostShareManagerImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
): PostShareManager {
    override fun sharePost(post: Post) {
        val shareText = "Check out this post!\n\nTitle:: ${post.title}\n\n${post.body}"

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        val chooserIntent = Intent.createChooser(
            shareIntent,
            "Share Post via"
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(chooserIntent)
    }
}