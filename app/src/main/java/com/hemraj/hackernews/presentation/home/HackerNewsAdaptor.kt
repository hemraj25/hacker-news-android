package com.hemraj.hackernews.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hemraj.hackernews.R
import com.hemraj.hackernews.domain.HackerNews
import kotlinx.android.synthetic.main.news_rv_item_view.view.*


class HackerNewsAdaptor(private val onItemClickListener: (HackerNews?) -> Unit) :
    RecyclerView.Adapter<HackerNewsAdaptor.NewsViewHolder>() {

    private var hackerNewsList: List<HackerNews>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.news_rv_item_view, parent, false)
        )

    override fun getItemCount(): Int {
        Log.d("HackerNewsAdaptor", "getItemCount = ${hackerNewsList?.size}")
        return hackerNewsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.d("HackerNewsAdaptor", "bind")
        holder.bindData(hackerNewsList?.get(position))
        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(hackerNewsList?.get(position))
        }
    }

    fun setData(newsList: List<HackerNews>) {
        hackerNewsList = newsList
        Log.d("HackerNewsAdaptor", "list size ${hackerNewsList?.size}")
        notifyDataSetChanged()
    }

    fun clearData() {
        hackerNewsList = null
        notifyDataSetChanged()
    }


    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(hackerNews: HackerNews?) {
            itemView.storyTitleTv.text = hackerNews?.title
            itemView.storyAuthorTv.text = hackerNews?.author
            itemView.storyTextTv.text = if (hackerNews?.storyText!!.isNotEmpty()) {
                hackerNews.storyText
            } else {
                itemView.context.getString(R.string.no_story_available)
            }
        }
    }
}