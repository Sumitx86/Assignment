package com.structure.task.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.structure.task.model.Articles

@Dao
interface DAOAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserData(articles: Articles)

    @Query("SELECT * FROM NewsData")
    fun getRecord(): MutableList<Articles>

    @Query("SELECT * FROM NewsData where description =:des")
    fun getRecordByDes(des: String): Articles
}