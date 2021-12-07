package com.structure.task.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.structure.task.R
import com.structure.task.adapter.NewsListAdapter
import com.structure.task.db.DAOAccess
import com.structure.task.db.DB
import com.structure.task.model.Articles
import com.structure.task.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_news_list.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    lateinit var newsAdapter: NewsListAdapter
    lateinit var dao : DAOAccess
    private var selectedDate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        dao = DB.getDatabaseClient(this).getDao()
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh("tesla", selectedDate)

        newsAdapter = object : NewsListAdapter(arrayListOf(), dao) {
            override fun onOptionSelected(articles: Articles) {
                startActivity(
                    Intent(this@MainActivity, NewsDetails::class.java)
                        .putExtra("data", articles)
                )
            }

            override fun onSaved(articles: Articles) {
                dao.inserData(articles)
            }
        }

        newsListRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        observeViewModel()

        bookmarkCl.setOnClickListener{
            startActivity(
                Intent(this@MainActivity, SavedNewsListing::class.java)
            )
        }

        searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (searchEt.text.toString().isNotEmpty()) {
                    viewModel.refresh(searchEt.text.toString(), selectedDate)
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.articles.observe(this, Observer { articles ->
            articles?.let {
                newsListRv.visibility = View.VISIBLE
                newsAdapter.updateData(it)
            }
        })

        viewModel.newsLoadError.observe(this, Observer { isError ->
            isError?.let { tvError.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loaderPB.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tvError.visibility = View.GONE
                    newsListRv.visibility = View.GONE
                }
            }
        })
    }
}