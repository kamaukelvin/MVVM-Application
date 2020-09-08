package com.uxcraft.mvvmsampleproject.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uxcraft.mvvmsampleproject.data.repositories.UserRepository


/*
AuthViewModel cannot be instantiated in the Activity classes directly thus we use A
view Model Factory that takes in the AuthViewModel as a parameter
*/

class ProfileViewModelFactory (
    private val repository: UserRepository
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository) as T
    }

}