package com.example.androidintermadedicoding.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.androidintermadedicoding.R
import com.example.androidintermadedicoding.data.view_model.AuthenticationFactory
import com.example.androidintermadedicoding.data.view_model.StoryViewModel
import com.example.androidintermadedicoding.databinding.ActivityDetailBinding
import com.example.androidintermadedicoding.utils.Status
import com.example.androidintermadedicoding.utils.preference.PreferenceFactory
import com.example.androidintermadedicoding.utils.preference.PreferenceViewModel
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val authenticationFactory: AuthenticationFactory by inject()
    private val storyViewModel: StoryViewModel by viewModels { authenticationFactory }

    private val prefFactory: PreferenceFactory by inject()
    private val pref: PreferenceViewModel by viewModels{prefFactory}

    companion object{
        val nameClass = DetailActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id = intent.getStringExtra("id")

        binding.apply {
            setSupportActionBar(detailToolbar)
            supportActionBar?.title = "Detail Story"
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            detailToolbar.setNavigationOnClickListener {
                finish()
            }

            pref.getBearerKey().observe(this@DetailActivity){
                storyViewModel.getDetailStories(it.toString(), id.toString()).observe(this@DetailActivity){ status ->
                    when (status) {
                        is Status.Loading -> {
                            grupDetail.visibility = View.GONE
                            pbLoading.visibility = View.VISIBLE
                        }

                        is Status.Success -> {
                            grupDetail.visibility = View.VISIBLE
                            pbLoading.visibility = View.GONE

                            val data = status.data

                            imageStory.load(data.story?.photoUrl) {
                                placeholder(R.drawable.ic_image_24)
                            }
                            titleImage.text = data.story?.name
                            titleDescription.text = data.story?.description

                            Toast.makeText(
                                this@DetailActivity,
                                data.message,
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        is Status.Error -> {
                            grupDetail.visibility = View.VISIBLE
                            pbLoading.visibility = View.GONE

                            val data = status.error

                            Log.e(nameClass, data)

                            Toast.makeText(this@DetailActivity, "Some Think Wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


        }
    }
}