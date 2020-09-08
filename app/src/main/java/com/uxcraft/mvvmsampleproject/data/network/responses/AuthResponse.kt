package com.uxcraft.mvvmsampleproject.data.network.responses

import com.uxcraft.mvvmsampleproject.data.db.entities.User

/*
this file maps the response from api, we take all the key value pairs from the response and map them to a data class
User is an object and is mapped as an entity in db>entities>user
THIS IS USED TO PASS JSON RESPONSE FROM API TO A KOTLIN CLASS
*/

data class AuthResponse(

//    val isSuccessful:Boolean?,
//    val message: String?,
    val data: User?

)
