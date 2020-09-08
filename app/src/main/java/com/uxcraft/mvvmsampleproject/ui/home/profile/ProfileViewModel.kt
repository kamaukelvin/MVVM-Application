package com.uxcraft.mvvmsampleproject.ui.home.profile

import androidx.lifecycle.ViewModel
import com.uxcraft.mvvmsampleproject.data.repositories.UserRepository

class ProfileViewModel(
   repository: UserRepository
) : ViewModel() {

/*
get current user from user repository, this returns livedata and we can bind
livedata to our xml
*/
val user = repository.getUser()

}