package com.hemraj.hackernews.data

import com.hemraj.hackernews.domain.HackerNews

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
        hits.forEach {
            hackerNewsList.add(
                HackerNews(
                    it.title ?: "Title",
                    it.story_text ?: "",
                    it.author ?: "",
                    it.url ?: ""
                )
            )
        }
        return hackerNewsList
    }
}


data class Hit(
    val _highlightResult: HighlightResult,
    val _tags: List<String>,
    val author: String?,
    val comment_text: Any,
    val created_at: String,
    val created_at_i: Int,
    val num_comments: Int,
    val objectID: String,
    val parent_id: Any,
    val points: Int,
    val relevancy_score: Int,
    val story_id: Any,
    val story_text: String?,
    val story_title: String?,
    val story_url: Any,
    val title: String?,
    val url: String?
)

data class HighlightResult(
    val author: Author,
    val title: Title,
    val url: Url
)

data class Title(
    val matchLevel: String,
    val matchedWords: List<Any>,
    val value: String
)

data class Url(
    val matchLevel: String,
    val matchedWords: List<Any>,
    val value: String
)

data class Author(
    val matchLevel: String,
    val matchedWords: List<Any>,
    val value: String
)
