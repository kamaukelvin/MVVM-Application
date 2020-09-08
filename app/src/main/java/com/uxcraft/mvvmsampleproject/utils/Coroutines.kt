package com.uxcraft.mvvmsampleproject.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {

//    work is another function inside tghe main function
//    the work function can be called any other name and MUST be of type suspend
//    Dispatchers

    fun main( work: suspend (()-> Unit))=
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }
    fun io( work: suspend (()-> Unit))=
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }
}