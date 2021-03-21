package com.example.englishsupport.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordsDao {
    @Query("SELECT * FROM DatabaseWords ORDER BY save_date DESC")
    fun getWords(): LiveData<List<DatabaseWords>>

    @Query("SELECT * FROM DatabaseWords ORDER BY save_date DESC LIMIT 30")
    fun getLastThirtyWords(): LiveData<List<DatabaseWords>>

    @Query("SELECT * FROM DatabaseWords ORDER BY save_date DESC LIMIT 10")
    fun getRecentWords(): LiveData<List<DatabaseWords>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: DatabaseWords)
}

@Database(entities = [DatabaseWords::class], version = 1)
abstract class WordsDatabase: RoomDatabase() {
    abstract val wordsDao: WordsDao
}

private lateinit var INSTANCE: WordsDatabase

fun getDatabase(context: Context): WordsDatabase {
    synchronized(WordsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
           INSTANCE = Room.databaseBuilder(context.applicationContext,
           WordsDatabase::class.java,
           "words")
               .fallbackToDestructiveMigration()
               .build()
        }
    }

    return  INSTANCE
}