package com.uxcraft.mvvmsampleproject.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uxcraft.mvvmsampleproject.data.repositories.UserRepository


/*
AuthViewModel cannot be instantiated in the Activity classes directly thus we use A
view Model Factory that takes in the AuthViewModel as a parameter
*/

class AuthViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }

}