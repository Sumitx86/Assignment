package com.structure.task.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.structure.task.R
import com.structure.task.adapter.SavedNewsListAdapter
import com.structure.task.db.DAOAccess
import com.structure.task.db.DB
import com.structure.task.model.Articles
import kotlinx.android.synthetic.main.activity_saved_news.*

class SavedNewsListing : AppCompatActivity() {
    lateinit var newsAdapter: SavedNewsListAdapter
    lateinit var dao: DAOAccess
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_news)
        dao = DB.getDatabaseClient(this).getDao()
        setData()


    }

    private fun setData() {
        newsAdapter = object : SavedNewsListAdapter(dao.getRecord(), dao) {
            override fun onOptionSelected(articles: Articles) {
                startActivity(
                    Intent(this@SavedNewsListing, NewsDetails::class.java)
                        .putExtra("data", articles)
                )
            }
        }

        savedNewsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        backIv.setOnClickListener { finish() }
    }
}