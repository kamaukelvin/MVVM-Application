package com.uxcraft.mvvmsampleproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uxcraft.mvvmsampleproject.data.db.entities.Quote
import com.uxcraft.mvvmsampleproject.data.db.entities.User

//user annotation database and inside it define all your entities of the database
@Database(
    entities = [User::class, Quote::class],
    version = 1

)

abstract class AppDatabase: RoomDatabase() {

    abstract fun getUserDao():UserDao
    abstract fun getQuoteDao():QuotesDao

    companion object{

        @Volatile
        private var instance:AppDatabase?=null

//        lock prevents creating two instances of the database
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?: buildDatabase(context).also {
                instance =it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,AppDatabase::class.java,
            "MyDatabase.db"
        ).build()

    }
}