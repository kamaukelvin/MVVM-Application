package com.uxcraft.mvvmsampleproject.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/*
we are saving the last TIME our local db was updated in shared preferences so that if the time is
more than say an hour since our last save we go fetch a clean copy of data from backend
*/

/*
Shared preference saves items in key-pair value format hence we declare here
(we can name them however)
*/
private const val KEY_SAVED_AT = "key_saved_at"

class PreferenceProvider(
    context: Context
) {
/*
saving context may lead to memory leaks hence we save application context that we get from
an instance of the context
*/
private val appContext = context.applicationContext

    private val preference: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)

//    FUNCTION TO SAVE THE TIME WHICH OUR LOCAL DB DATA WAS SAVED
    fun saveLastSavedAt(savedAt:String) {
    preference.edit().putString(KEY_SAVED_AT, savedAt).apply()
}
//    FUNCTION TO GET THE VALUE OF THE LAST TIME STAMP SAVED IN SHARED PREFERENCES

    fun getLastSavedAt():String?{
        return preference.getString(KEY_SAVED_AT, null)
    }
}

