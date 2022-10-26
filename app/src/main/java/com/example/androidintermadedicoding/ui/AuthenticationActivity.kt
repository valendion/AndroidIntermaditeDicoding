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
import com.example.androidintermadedicoding.databinding.ActivityAuthenticationBinding
import com.example.androidintermadedicoding.ui.list_story.ListStoryActivity
import com.example.androidintermadedicoding.utils.Status
import com.example.androidintermadedicoding.utils.preference.PreferenceFactory
import com.example.androidintermadedicoding.utils.preference.PreferenceViewModel
import org.koin.android.ext.android.inject


class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private val authenticationFactory: AuthenticationFactory by inject()
    private val storyViewModel: StoryViewModel by viewModels { authenticationFactory }

    private val prefFactory: PreferenceFactory by inject()
    private val pref: PreferenceViewModel by viewModels { prefFactory }
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {

            pref.getBearerKey().observe(this@AuthenticationActivity) {
                if (it.isNotEmpty()) {
                    startActivity(
                        Intent(
                            this@AuthenticationActivity,
                            ListStoryActivity::class.java
                        ).putExtra("token", token)
                    )
                    finishAffinity()
                }
            }



        textRegister.setOnClickListener {
            startActivity(Intent(this@AuthenticationActivity, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val emailText = inputEmail.text.toString()
            val passwordText = inputPassword.text.toString()

            if (emailText.isEmpty()) {
                inputEmail.error = "Tolong isi email anda"
                toastMessage()
            } else if (passwordText.isEmpty()) {
                inputPassword.error = "Tolong isi password anda"
                toastMessage()
            } else if (inputEmail.error != null || inputPassword.error != null) {
                toastMessage()
            } else {

                inputEmail.error = null
                inputPassword.error = null
                storyViewModel.postLogin(emailText, passwordText)
                    .observe(this@AuthenticationActivity) { status ->
                        when (status) {
                            is Status.Loading -> {
                                grupLogin.visibility = View.GONE
                                pbLoading.visibility = View.VISIBLE
                            }

                            is Status.Success -> {
                                grupLogin.visibility = View.VISIBLE
                                pbLoading.visibility = View.GONE

                                val data = status.data

                                Toast.makeText(
                                    this@AuthenticationActivity,
                                    data.message,
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(
                                    Intent(
                                        this@AuthenticationActivity,
                                        ListStoryActivity::class.java
                                    ).putExtra("token", data.loginResult?.token)
                                )

                                finishAffinity()
                            }

                            is Status.Error -> {
                                grupLogin.visibility = View.VISIBLE
                                pbLoading.visibility = View.GONE

                                val data = status.error

                                Log.e(RegisterActivity.nameClass, data)

                                Toast.makeText(
                                    this@AuthenticationActivity,
                                    "Some Think Wrong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

//
                    }
            }


        }
    }
}

private fun toastMessage() {
    Toast.makeText(this@AuthenticationActivity, "Perbaiki Data anda", Toast.LENGTH_SHORT)
        .show()
}

}


