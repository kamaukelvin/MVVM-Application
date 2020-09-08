package com.uxcraft.mvvmsampleproject

import android.app.Application
import com.uxcraft.mvvmsampleproject.data.db.AppDatabase
import com.uxcraft.mvvmsampleproject.data.network.MyApi
import com.uxcraft.mvvmsampleproject.data.network.NetworkConnectionInterceptor
import com.uxcraft.mvvmsampleproject.data.preferences.PreferenceProvider
import com.uxcraft.mvvmsampleproject.data.repositories.QuotesRepository
import com.uxcraft.mvvmsampleproject.data.repositories.UserRepository
import com.uxcraft.mvvmsampleproject.ui.auth.AuthViewModelFactory
import com.uxcraft.mvvmsampleproject.ui.home.profile.ProfileViewModelFactory
import com.uxcraft.mvvmsampleproject.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/*
this is an application class that is instantiated(run) before any other class in our app
its work is to supply instances of object to other objects instead of calling them directly in
our activity
*/
class MVVMApplication: Application(),KodeinAware {

    override val kodein= Kodein.lazy{
        import (androidXModule(this@MVVMApplication))

//        binding all the objects that we need
//        singleton means only a single instance
//NB If a certain class(say class1) requires class2 as a parameter we need to first bind the
// parameter inorder for instance() to work, otherwise it would not understand what instance is if
//        not already binded


//        network interceptor
        bind() from singleton { NetworkConnectionInterceptor(instance()) }

//        myApi
        bind() from singleton{MyApi(instance())}

        //        myDatabase
        bind() from singleton{AppDatabase(instance())}

        //        User Repository
        bind() from singleton{UserRepository(instance(), instance())}

        //        Shared Preference
        bind() from singleton{PreferenceProvider(instance())}

//        for our AuthviewModelFactory we dont require a single instance hence we use provider
        bind() from provider{AuthViewModelFactory(instance())}
        bind() from provider{ ProfileViewModelFactory(instance()) }

        //      Quotes Repository
        bind() from singleton{ QuotesRepository(instance(), instance(), instance()) }

        //      QuotesViewModelFactory
        bind() from provider{QuotesViewModelFactory(instance())}
    }

}