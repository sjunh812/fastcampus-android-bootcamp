package org.sjhstudio.fastcampus.part1.chapter7.database

import androidx.room.*

@Dao
interface WordDao {

    @Query("SELECT * FROM word ORDER BY id DESC")
    fun getAll(): List<Word>

    @Query("SELECT * FROM word ORDER BY id DESC LIMIT 1")
    fun getLatestWord(): Word

    @Insert
    fun addWord(word: Word)

    @Delete
    fun removeWord(word: Word)

    @Update
    fun updateWord(word: Word)
}