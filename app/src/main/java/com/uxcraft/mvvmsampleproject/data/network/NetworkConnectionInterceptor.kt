package com.uxcraft.mvvmsampleproject.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.uxcraft.mvvmsampleproject.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

//    saving application context from the context in the constructor
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

//        if internet is not available throw an exception (from our exceptions file)
        if(!isInternetAvailable())
            throw   NoInternetException("Please Check your Internet connection")

//        case internet is available proceed to make api call
        return chain.proceed(chain.request())

    }

//    function to check internet availability before making a request
//    it requires context
    private fun isInternetAvailable(): Boolean{

//    a variable to store if network is available initially initialized as false
    var result = false
//    we are using context here as we need to access getSystemService method available in context
    val connectivityManager =
    applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
connectivityManager?.let {
    it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
    result = when{
//        if it has Transport network is available
        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        else -> false
    }
    }
}
    return result
}
}