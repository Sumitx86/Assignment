package com.structure.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.structure.task.R
import com.structure.task.db.DAOAccess
import com.structure.task.model.Articles
import com.structure.task.util.loadImage
import kotlinx.android.synthetic.main.saved_news_item.view.*


abstract class SavedNewsListAdapter(var news: MutableList<Articles>, var dao: DAOAccess) :
    RecyclerView.Adapter<SavedNewsListAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val reporterCvImg = view.reporterCvImg
        private val tagTv = view.tagTv
        private val tvDate = view.tvDate
        private val newHeadlineTv = view.newHeadlineTv
        fun bind(articles: Articles) {
            tvDate.text = articles.publishedAt
            tagTv.text = articles.author
            newHeadlineTv.text = articles.title
            reporterCvImg.loadImage(articles.urlToImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = NewsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.saved_news_item, parent, false)
    )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articles = news[position]
        holder.bind(articles)
        holder.itemView.setOnClickListener {
            onOptionSelected(articles)
        }
    }

    override fun getItemCount() = news.size

    fun updateData(articles: List<Articles>) {
        news.clear()
        news.addAll(articles)
        notifyDataSetChanged()
    }

    abstract fun onOptionSelected(articles: Articles)
}