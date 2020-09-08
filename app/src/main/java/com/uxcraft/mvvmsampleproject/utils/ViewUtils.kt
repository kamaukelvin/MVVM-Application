package com.uxcraft.mvvmsampleproject.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


//show the toast message

fun Context.toast(message:String){

    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

//show the progress bar
fun ProgressBar.show(){
    visibility = View.VISIBLE
}

//hide the progress bar
fun ProgressBar.hide(){
    visibility = View.GONE
}

//snackbar
fun View.snackbar(message:String){
    Snackbar.make(this, message,Snackbar.LENGTH_LONG).also{ snackbar ->
        snackbar.setAction("Ok"){
            snackbar.dismiss()

        }
    }.show()

}
