package com.hemraj.hackernews.data

import androidx.annotation.Keep
import com.hemraj.hackernews.domain.HackerNews

@Keep
class HackerNewsApi(
    val exhaustiveNbHits: Boolean,
    val hits: List<Hit>,
    val hitsPerPage: Int,
    val nbHits: Int,
    val nbPages: Int,
    val page: Int,
    val params: String,
    val processingTimeMS: Int,
    val query: String
) {
    fun toDomain(): List<HackerNews> {
        val hackerNewsList = mutableListOf<HackerNews>()
        hits.filter {
            it.title.isNullOrEmpty().not() ||  it.story_title.isNullOrEmpty().not()
        }.map {
            hackerNewsList.add(
                HackerNews(
                    getTitle(it),
                    it.comment_text ?: "",
                    it.author ?: "",
                    it.url ?: it.story_url?: ""
                )
            )
        }
        return hackerNewsList
    }

    private fun getTitle(hit: Hit): String {
        return if (hit.title.isNullOrEmpty().not())
            hit.title!!
        else if (hit.story_title.isNullOrEmpty().not()) {
            hit.story_title!!
        } else {
            "Title"
        }
    }
}

@Keep
data class Hit(
    val _tags: List<String>?,
    val author: String?,
    val comment_text: String?,
    val created_at: String?,
    val created_at_i: Int,
    val num_comments: Int,
    val objectID: String?,
    val parent_id: Int,
    val points: Int,
    val relevancy_score: Int,
    val story_id: Int,
    val story_text: String?,
    val story_title: String?,
    val story_url: String?,
    val title: String?,
    val url: String?
)

