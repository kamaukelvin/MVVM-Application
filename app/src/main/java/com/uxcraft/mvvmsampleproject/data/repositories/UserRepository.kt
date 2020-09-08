package com.uxcraft.mvvmsampleproject.data.repositories

import android.util.Log
import com.uxcraft.mvvmsampleproject.data.db.AppDatabase
import com.uxcraft.mvvmsampleproject.data.db.entities.User
import com.uxcraft.mvvmsampleproject.data.network.MyApi
import com.uxcraft.mvvmsampleproject.data.network.SafeApiRequest
import com.uxcraft.mvvmsampleproject.data.network.responses.AuthResponse
import com.uxcraft.mvvmsampleproject.data.network.responses.ResetResponse

import retrofit2.Response
//THis class extends SafeApiRequest that is its uses safeApiRequest to make api request
//this is useful due to handling exceptions in api requests

class UserRepository(
//    here we are creating constructor to avoid calling MyApi() directly as it is a poor work method
//    declare the api file and database here to call all the functions in them using the declared variables e.g api.userLogin instead of MyApi().userLogin
private val api : MyApi,
private val db : AppDatabase
): SafeApiRequest(){

/*
user repository communicated with myApi to call the function userLogin and expects  a return of type AuthResponse
AuthResponse is the mapped api response to kotlin class
suspend functions are asynchronous function just like async await in javascript
*/

    suspend fun userLogin(email:String, password:String) : AuthResponse {
        return apiRequest{api.userLogin(email,password)}

    }

//    save authenticated  user to db(ROOM) function

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)


//    get user saved in database>>> This is an authenticated user
    fun getUser() = db.getUserDao().getUser()

//    signup function

    suspend fun userSignUp(name:String,email:String, password:String) :AuthResponse{
        return apiRequest { api.userSignUp(name, email, password) }
    }

//    Reset Password function
    suspend fun resetPassword(email:String): ResetResponse{
    Log.d("Reset","Reset method has been invoked")
    return apiRequest { api.resetPassword(email) }

}



}