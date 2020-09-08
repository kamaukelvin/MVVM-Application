package com.uxcraft.mvvmsampleproject.data.network


import com.uxcraft.mvvmsampleproject.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

//CLASS TO HANDLE API EXCEPTIONS

abstract class SafeApiRequest {

    suspend fun <T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()

        if (response.isSuccessful){
//            no error so return the body from api
            return response.body()!!

        }else{

//            handling exception here


//  store the error in the variable error
            val error = response.errorBody()?.string()

//            trying to get the error message from the error body as in the api so as to display it
            val message = StringBuilder()
            error?.let{

/*
not aLL ERRORS are from the api response some are from network error, and maybe.......
error from api response with have an error body returned from api, but those from network error wont
we use try catch block to get the message to display to user if error has an errorBody using getString method from the json object in api
*/
                try {
//                    this says go to error body and get the string with the key name of message
                    message.append(JSONObject(it).getString("message"))
                } catch(e: JSONException){
//                    if error does not have message body code will execute the catch block which is NOTHING

                }
//                Adding a new line

                message.append("\n")
            }
//            for errors with  no error body we display the status code and throw an Api Exception message to our customized function in utils for handling Exceptions
            message.append("Error Code: ${response.code()}")

/*
throw the message to the APIException which will in turn throw the message to the IO Exception
in the ApiException parameter we converting the message to string
*/
            throw ApiException(message.toString())


        }
    }
}