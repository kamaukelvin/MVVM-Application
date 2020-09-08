package com.uxcraft.mvvmsampleproject.ui.auth


import androidx.lifecycle.ViewModel
import com.uxcraft.mvvmsampleproject.data.db.entities.User
import com.uxcraft.mvvmsampleproject.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

//    calls are being made through th dispatcher IO to free the main IO HENCE making app faster

//  1.  Login that call our userLogin function from the repository

    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.userLogin(email, password) }

//  2.   get logged in user

    fun getLoggedInUser() = repository.getUser()

//    3.  save the authenticated user to our local database(i.e ROOM)

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)

    //    4. user signup
    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.userSignUp(name, email, password) }

//    5. RESET PASSWORD
    suspend fun resetPassword(

    email: String) = withContext(Dispatchers.IO){repository.resetPassword(email)}





}