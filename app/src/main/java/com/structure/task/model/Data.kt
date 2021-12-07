package com.structure.task.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(val status: String, val totalResults: String, val articles: ArrayList<Articles>): Parcelable

@Entity(tableName = "NewsData")
@Parcelize
data class Articles(
    val author: String?, val title: String?, val description: String?, val url: String?,
    val urlToImage: String?, val publishedAt: String?, val content: String?
): Parcelable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}

@Parcelize
data class Source(val id: String?, val name: String): Parcelable