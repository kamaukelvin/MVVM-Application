package com.uxcraft.mvvmsampleproject.data.network

import android.util.Log
import com.uxcraft.mvvmsampleproject.data.network.responses.AuthResponse
import com.uxcraft.mvvmsampleproject.data.network.responses.QuotesResponse
import com.uxcraft.mvvmsampleproject.data.network.responses.ResetResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

import timber.log.Timber

interface MyApi {

//    LOGIN

    @FormUrlEncoded
    @POST("sign_in")
    suspend fun userLogin(
        @Field("email")email: String,
        @Field("password")password: String
    )  : Response<AuthResponse>


//    signup

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignUp(
        @Field("name")name: String,
        @Field("email")email: String,
        @Field("password")password: String

    ):Response<AuthResponse>


    //    GET QUOTES
    @GET("quotes")
    suspend fun getQuotes():Response<QuotesResponse>

//RESET PASSWORD

@FormUrlEncoded
@POST("password")
suspend fun resetPassword(
    @Field("email")email: String
)  : Response<ResetResponse>



    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{

            val logger = HttpLoggingInterceptor.Logger { message ->
                Timber.tag("OkHttp").d(message)


            }
            val loggingInterceptor = HttpLoggingInterceptor(logger)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient.Builder()
                .addInterceptor(getTokenAndHeaderInterceptor())
                .connectTimeout(100, TimeUnit.SECONDS)
                .callTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
            builder.addInterceptor(loggingInterceptor)

            val httpClient = builder.build()

//            check for internet connectivity before proceeding using a network interceptor


            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

//            .client adds a network interceptor to our retrofit

            return Retrofit.Builder()
                .client(httpClient)
                .baseUrl("https://staging-core-v2-backend.herokuapp.com/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)


        }

        private fun getTokenAndHeaderInterceptor(): Interceptor {
            return Interceptor { chain: Interceptor.Chain ->
                val token = ""
                Timber.d("Auth token: %s", token)
                val originalRequest = chain.request()
                val builder = originalRequest.newBuilder()
                /*.header("Content-Type", "application/json")
                .header("WWW-Authenticate", "Token")*/
                if (token.isNotEmpty()) {
                    builder.header("Authorization", "Token $token")
                }
                val request = builder.build()
                chain.proceed(request)
            }
        }

    }


}

//