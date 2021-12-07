package com.structure.task.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.structure.task.model.Articles

@Database(entities = arrayOf(Articles::class), version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: DB? = null
        fun getDatabaseClient(context: Context): DB {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, DB::class.java, "NEWS_DATABASE")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                return INSTANCE!!
            }
        }
    }

    abstract fun getDao(): DAOAccess
}