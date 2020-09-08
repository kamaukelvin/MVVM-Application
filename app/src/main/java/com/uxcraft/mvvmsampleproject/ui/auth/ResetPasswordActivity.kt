package com.uxcraft.mvvmsampleproject.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.uxcraft.mvvmsampleproject.R
import com.uxcraft.mvvmsampleproject.databinding.ActivityLoginBinding
import com.uxcraft.mvvmsampleproject.databinding.ActivityResetPasswordBinding
import com.uxcraft.mvvmsampleproject.utils.ApiException
import com.uxcraft.mvvmsampleproject.utils.NoInternetException
import com.uxcraft.mvvmsampleproject.utils.snackbar
import kotlinx.coroutines.launch
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ResetPasswordActivity : AppCompatActivity(),KodeinAware {

    override val kodein by kodein()
    private val factory:AuthViewModelFactory by instance()

    private lateinit var binding: ActivityResetPasswordBinding

    private lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  DataBindingUtil.setContentView(this,R.layout.activity_reset_password)

         viewModel =ViewModelProvider(this,factory).get(AuthViewModel::class.java)

        binding.buttonResetPassword.setOnClickListener {
            resetPassword()
        }



    }

    private fun resetPassword() {
        val email = binding.editTextEmail.text.toString().trim()


        lifecycleScope.launch {
            try {



                val resetResponse = viewModel.resetPassword(email)
                if(resetResponse.success != false){
                    Log.d("reset password",resetResponse.message.toString() )
                }

                    Log.d("reset password","display the error here" )
                    Log.d("reset password",resetResponse.errors.toString() )




            } catch (e: ApiException) {
                Log.e("hapa hakuna kitu3",e.toString())
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }


        }


    }


}