package com.example.androidintermadedicoding.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidintermadedicoding.data.view_model.AuthenticationFactory
import com.example.androidintermadedicoding.data.view_model.StoryViewModel
import com.example.androidintermadedicoding.databinding.ActivityRegisterBinding
import org.koin.android.ext.android.inject
import com.example.androidintermadedicoding.utils.Status

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val authenticationFactory: AuthenticationFactory by inject()
    private val storyViewModel: StoryViewModel by viewModels{authenticationFactory}

    companion object{
        val nameClass = RegisterActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            btnRegis.setOnClickListener {
                val nameText = inputName.text.toString()
                val emailText = inputEmail.text.toString()
                val passwordText = inputPassword.text.toString()

                if (nameText.isEmpty()){
                    inputName.error = "Tolong Isi nama anda"
                    toastMessage()
                }else if (emailText.isEmpty()){
                    inputEmail.error = "Tolong isi email anda"
                    toastMessage()
                }else if (passwordText.isEmpty()){
                    inputPassword.error = "Tolong isi password anda"
                    toastMessage()
                }else if (inputEmail.error != null || inputPassword.error != null){
                    toastMessage()
                }else{
                    inputName.error = null
                    inputEmail.error = null
                    inputPassword.error = null
                    storyViewModel.postRegister(nameText, emailText, passwordText).observe(this@RegisterActivity){status ->
                        when(status){
                            is Status.Loading ->{
                                grupRegister.visibility = View.GONE
                                pbLoading.visibility = View.VISIBLE
                            }

                            is Status.Success ->{
                                grupRegister.visibility = View.VISIBLE
                                pbLoading.visibility = View.GONE

                                val data = status.data

                                Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this@RegisterActivity, AuthenticationActivity::class.java))
                            }

                            is Status.Error ->{
                                grupRegister.visibility = View.VISIBLE
                                pbLoading.visibility = View.GONE

                                val data = status.error

                                Log.e(nameClass, data)

                                Toast.makeText(applicationContext, "Some Think Wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
//
                }

            }
        }
    }

    private fun toastMessage(){
        Toast.makeText(applicationContext, "Perbaiki Data anda", Toast.LENGTH_SHORT)
            .show()
    }
}