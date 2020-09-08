package com.uxcraft.mvvmsampleproject.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uxcraft.mvvmsampleproject.data.db.AppDatabase
import com.uxcraft.mvvmsampleproject.data.db.entities.Quote
import com.uxcraft.mvvmsampleproject.data.network.MyApi
import com.uxcraft.mvvmsampleproject.data.network.SafeApiRequest
import com.uxcraft.mvvmsampleproject.data.preferences.PreferenceProvider
import com.uxcraft.mvvmsampleproject.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


//the minimum interval in hours so that data can be fetched from api and updated in our local db

private val MINIMUM_INTERVAL = 0.5

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
):SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

/*
here we observe the quotes inside an init block
whenever there is a change in quotes liveData we save the quotes to local db
we use observeForever as we are not inside a fragment class or activity so we ont need to
worry about lifecycle changes
*/
init {
        quotes.observeForever{
//            whenever there is a change in quotes we will push all the changes to the local db
            saveQuotes(it)
        }
    }

//    1. fetch the quotes from the backend
    private suspend fun fetchQuotes(){

//    get the time the quotes were last saved at from the shared preferences
    val lastSavedAt = prefs.getLastSavedAt()
/*
check if we have a copy of the quotes in our local db if it exists no need to fetch the
data again
*/

    if(lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))){

        try{
            val response = apiRequest {
                api.getQuotes()}

//              we put all our fetched quotes to the LiveData quotes declared in Line 15
            quotes.postValue(response.quotes)


        }catch(e:Exception){
            e.printStackTrace()

        }

    }

}
    /*
    2.   function to check if we have an exact copy of the data in our local db or if the
    difference in time between now and when our quotes were saved to the local db is half an hour
    so as to update it
    */

    private fun isFetchNeeded(savedAt: LocalDateTime):Boolean{
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now())> MINIMUM_INTERVAL
    }





//  3.  save quotes to local db

    private fun saveQuotes(quotes: List<Quote>){

        Coroutines.io {

/*
save timestamp using shared preferneces (the time quotes has been saved in local
db>>>this will be used to check if we require to make a fresh request from backend  or not
*/
            prefs.saveLastSavedAt(LocalDateTime.now().toString())

//            save quotes to local db
            db.getQuoteDao().saveAllQuotes(quotes)
        }

    }

//    4. fetch quotes from local database
    suspend fun getQuotes(): LiveData<List<Quote>>{
    return withContext(Dispatchers.IO){

/*
it will call fetchQuotes which will in turn check if a call to backend is needed, if so,
the data will be save to the local db and we can get the quotes from the local db
using the method getQuotes from the QuotesDao
*/
        fetchQuotes()
        db.getQuoteDao().getQuotes()
    }


}
}