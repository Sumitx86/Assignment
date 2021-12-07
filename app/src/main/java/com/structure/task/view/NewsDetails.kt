package com.structure.task.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.structure.task.R
import com.structure.task.model.Articles
import com.structure.task.util.loadImage
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetails : AppCompatActivity() {
    lateinit var data: Articles
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        data = intent?.getParcelableExtra("data")!!
        setData()
    }

    private fun setData() {
        dateTv.text = data.publishedAt
        newHeadlineTv.text = data.title
        reporterTv.text = data.author
        descriptionTv.text = data.description
        //infoTv.text = data.source.name
        headerCl.loadImage(data.urlToImage)
        backIv.setOnClickListener { finish() }
    }
}