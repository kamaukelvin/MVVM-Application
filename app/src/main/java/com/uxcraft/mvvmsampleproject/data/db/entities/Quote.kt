package com.uxcraft.mvvmsampleproject.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//data class dont have a body i.e the curled brackets
@Entity
data class Quote (

/*
mapping the fields from the api response key-pair values
the id will be the primarykey
*/
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val quote: String,
    val author: String,
    val thumbnail: String,
    val created_at: String?,
    val updated_at: String?

)