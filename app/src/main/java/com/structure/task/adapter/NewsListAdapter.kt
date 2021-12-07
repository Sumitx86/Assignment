package com.structure.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.structure.task.R
import com.structure.task.db.DAOAccess
import com.structure.task.model.Articles
import com.structure.task.util.loadImage
import kotlinx.android.synthetic.main.news_feed_item.view.*

abstract class NewsListAdapter(var news: ArrayList<Articles>, var dao: DAOAccess) :
    RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val newsIv = view.newsIv
        private val newHeadlineTv = view.newHeadlineTv
        private val newDescriptionTv = view.newDescriptionTv
        private val dateTv = view.dateTv
        val readBtn = view.readBtn
        val saveBtn = view.saveBtn
        val imgSave = view.imgSave
        fun bind(articles: Articles) {
            newHeadlineTv.text = articles.title
            newDescriptionTv.text = articles.description
            dateTv.text = articles.publishedAt
            newsIv.loadImage(articles.urlToImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = NewsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.news_feed_item, parent, false)
    )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articles = news[position]
        holder.bind(articles)
        holder.readBtn.setOnClickListener { onOptionSelected(articles) }
        holder.saveBtn.setOnClickListener {
            holder.imgSave.setBackgroundResource(R.drawable.bg_bookmark)
            onSaved(articles)
        }
        val savedArticle = articles.description?.let { dao.getRecordByDes(it) }
        if (savedArticle != null && articles.description == savedArticle.description) {
            holder.imgSave.setBackgroundResource(R.drawable.bg_bookmark)
        } else {
            holder.imgSave.setBackgroundResource(R.drawable.gray_bg)
        }
    }

    override fun getItemCount() = news.size

    fun updateData(articles: List<Articles>) {
        news.clear()
        news.addAll(articles)
        notifyDataSetChanged()
    }

    abstract fun onOptionSelected(articles: Articles)
    abstract fun onSaved(articles: Articles)
}