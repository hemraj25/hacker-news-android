package com.hemraj.hackernews.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hemraj.hackernews.R
import com.hemraj.hackernews.databinding.NewsRvItemViewBinding
import com.hemraj.hackernews.domain.HackerNews
import com.hemraj.hackernews.util.log


class HackerNewsAdapter(private val onItemClickListener: (HackerNews?) -> Unit) :
    RecyclerView.Adapter<HackerNewsAdapter.NewsViewHolder>() {

    private var hackerNewsList: List<HackerNews>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsRvItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        log("HackerNewsAdapter", "getItemCount = ${hackerNewsList?.size}")
        return hackerNewsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        log("HackerNewsAdapter", "bind")
        holder.bindData(hackerNewsList?.get(position))
        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(hackerNewsList?.get(position))
        }
    }

    fun setData(newsList: List<HackerNews>) {
        hackerNewsList = newsList
        log("HackerNewsAdapter", "list size ${hackerNewsList?.size}")
        notifyDataSetChanged()
    }

    fun clearData() {
        hackerNewsList = null
        notifyDataSetChanged()
    }


    class NewsViewHolder(private val binding: NewsRvItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(hackerNews: HackerNews?) {
            binding.apply {
                storyTitleTv.text = hackerNews?.title
                storyAuthorTv.text = hackerNews?.author
                storyTextTv.text = if (hackerNews?.storyText.isNullOrEmpty().not()) {
                    hackerNews!!.storyText
                } else {
                    itemView.context.getString(R.string.no_story_available)
                }
            }
        }
    }
}