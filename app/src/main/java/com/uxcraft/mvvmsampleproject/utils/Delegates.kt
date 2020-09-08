package com.uxcraft.mvvmsampleproject.utils

import kotlinx.coroutines.*

//this file is responsible for supplying data in a lazy mode i.e only when needed
//like when fetching quotes,

fun<T> lazyDeferred(block: suspend CoroutineScope.()->T):Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}