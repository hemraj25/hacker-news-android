package com.hemraj.hackernews.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hemraj.hackernews.R
import com.hemraj.hackernews.domain.HackerNews


class HackerNewsAdaptor: RecyclerView.Adapter<HackerNewsAdaptor.NewsViewHolder>() {

    var hackerNewsList: List<HackerNews>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_rv_item_view, parent, false))

    override fun getItemCount(): Int {
        Log.d("HackerNewsAdaptor", "getItemCount = ${hackerNewsList?.size}")
        return hackerNewsList?.size?: 0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.d("HackerNewsAdaptor", "bind")
        holder.textView.text = hackerNewsList?.get(position)?.title
    }

    fun setData(newsList: List<HackerNews>) {
        hackerNewsList = newsList
        Log.d("HackerNewsAdaptor","list size ${hackerNewsList?.size}")
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView =  itemView.findViewById<TextView>(R.id.textView)
    }
}