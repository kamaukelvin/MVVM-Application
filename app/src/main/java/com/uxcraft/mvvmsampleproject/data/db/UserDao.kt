package com.uxcraft.mvvmsampleproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uxcraft.mvvmsampleproject.data.db.entities.CURRENT_USER_ID
import com.uxcraft.mvvmsampleproject.data.db.entities.User


//Dao = data access object to access data from our db i.e ROOM

@Dao
interface UserDao {

/*
function to insert or update user
CASE user is inserted successfully we will get the row id thus the type LONG
use INSERT annotation as this function will insert data in our database
on conflict describes what to do in case we get an entity with the same id
*/


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User) :Long

//    get the current stored user
    @Query("SELECT * FROM user WHERE uuid = $CURRENT_USER_ID")
    fun getUser(): LiveData<User>
}