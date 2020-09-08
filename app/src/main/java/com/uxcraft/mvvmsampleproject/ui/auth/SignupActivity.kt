package com.uxcraft.mvvmsampleproject.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.uxcraft.mvvmsampleproject.R
import com.uxcraft.mvvmsampleproject.data.db.entities.User
import com.uxcraft.mvvmsampleproject.databinding.ActivityLoginBinding
import com.uxcraft.mvvmsampleproject.databinding.ActivitySignupBinding
import com.uxcraft.mvvmsampleproject.ui.home.HomeActivity
import com.uxcraft.mvvmsampleproject.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(),  KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


   binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

   viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)




    viewModel.getLoggedInUser().observe(this, Observer { user->
        if (user != null) {
//                if user returned by method is not null direct user to Homepage

            Intent(this, HomeActivity::class.java).also {

                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

    })

        binding.buttonSignUp.setOnClickListener {
            userSignup()
        }

        // when user preses the "Already have an , login text" at bottom of screen he is taken to
// login screen

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
}

    private fun userSignup(){

        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val confirmPassword = binding.editTextPasswordConfirm.text.toString().trim()

        lifecycleScope.launch {
            try{
                val authResponse = viewModel.userSignup(name, email, password)

//            check if response is successful and user is not null
                if (authResponse.data!=null){

                    // save logged user to local db
                    viewModel.saveLoggedInUser(authResponse.data)
                }else{
//                display the error
//                the !! ensures that the message is not null
                    binding.root.snackbar("signup failed")

                }

            }catch (e: ApiException){
                e.printStackTrace()
            }catch (e: NoInternetException){
                e.printStackTrace()
            }


        }

    }


}
