package com.uxcraft.mvvmsampleproject.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.uxcraft.mvvmsampleproject.R
import com.uxcraft.mvvmsampleproject.databinding.ActivityLoginBinding
import com.uxcraft.mvvmsampleproject.ui.home.HomeActivity
import com.uxcraft.mvvmsampleproject.utils.ApiException
import com.uxcraft.mvvmsampleproject.utils.NoInternetException
import com.uxcraft.mvvmsampleproject.utils.snackbar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


//KodeinAware for depedency injection

class LoginActivity : AppCompatActivity(), KodeinAware {

/*
we will need MyApi, AppDatabase, NetworkInterceptor, repository and AuthViewModelFactory to
use in LoginActivity BUT they are all depedencies of AuthViewModelFactory so we do not need
to import all, importing AuthViewFactory gives us access to the rest of classes
GETTING FACTORY THAT SUPPLIES US WITH THE VIEWMODEL USING KODEIN instance
*/

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

//    setting the binding as the class instance

    private lateinit var binding: ActivityLoginBinding

    //    define our view model here
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
creating all class instances required to be used in this activity using direct injection
from outside the file with help of kodein depedency
binding our view model with our activity
*/

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


//        getLoggedIn User

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
//                if user returned by method is not null direct user to Homepage

                Intent(this, HomeActivity::class.java).also {
/*
we set flags to our intent to start this activity as a new activity so that incase user presses
back button he/she is not taken back to login page as that activity is already terminated
*/
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }

        })

//        sign in user by first binding the sign in button and calling the function
        binding.buttonSignIn.setOnClickListener {
            loginUser()
        }

/*
when user preses the "Dont have an , signup text" at bottom of screen he is taken to
signup screen
*/

        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.textViewForgetPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

/*
ADD VALIDATIONS HERE
**************************************
CALL OUR USER LOGIN FUNCTION FROM OUR VIEW MODEL
in an activity we use lifecycleScope but in a fragment we use viewLifecycleScope
*/

        lifecycleScope.launch {
            try {
                Log.d("hapa hakuna kitu1","still ghere")
                val authResponse = viewModel.userLogin(email, password)




//            check if response is successful and user is not null
                if (authResponse.data != null) {
                    Log.d("hapa hakuna kitu1",authResponse.data.toString())
                    // save logged user to local db
                    viewModel.saveLoggedInUser(authResponse.data)
                } else {
                    Log.d("hapa hakuna kitu2",authResponse.data.toString())
//                display the error
//                the !! ensures that the message is not null
                    binding.rootLayout.snackbar("Login Failed")

                }

            } catch (e: ApiException) {
                Log.e("hapa hakuna kitu3",e.toString())
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }


        }


    }

}
