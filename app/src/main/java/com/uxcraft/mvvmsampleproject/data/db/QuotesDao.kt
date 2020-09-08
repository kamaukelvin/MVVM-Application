package com.uxcraft.mvvmsampleproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uxcraft.mvvmsampleproject.data.db.entities.Quote

//annotate with @Dao
@Dao
interface QuotesDao {

//    SAVE ALL OUR QUOTES INSIDE THE LOCAL DATABASE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllQuotes(quotes:List<Quote>)

//    FETCH DATA FROM LOCAL DATABASE
    @Query("SELECT * FROM Quote")
    fun getQuotes() : LiveData<List<Quote>>

}