package com.uxcraft.mvvmsampleproject.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.uxcraft.mvvmsampleproject.data.repositories.QuotesRepository
import com.uxcraft.mvvmsampleproject.utils.lazyDeferred

//we need a QuotesViewModelFactory as the view Model is taking a repository as an argument
class QuotesViewModel(
    repository:QuotesRepository
) : ViewModel() {

/*
get quotes from Quotes Repository
we dont want to get quotes everytime the QuotesViewModel is needed, so we use the
lazy keyword to get quotes only when needed
*/
val quotes by lazyDeferred{
    repository.getQuotes()

}
}