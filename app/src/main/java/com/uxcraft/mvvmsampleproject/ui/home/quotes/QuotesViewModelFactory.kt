package com.uxcraft.mvvmsampleproject.ui.home.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uxcraft.mvvmsampleproject.data.repositories.QuotesRepository
import com.uxcraft.mvvmsampleproject.data.repositories.UserRepository


/*
AuthViewModel cannot be instantiated in the Activity classes directly thus we use A
view Model Factory that takes in the AuthViewModel as a parameter
*/

@Suppress("UNCHECKED_CAST")
class QuotesViewModelFactory (
    private val repository: QuotesRepository
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModelFactory(repository) as T
    }

}