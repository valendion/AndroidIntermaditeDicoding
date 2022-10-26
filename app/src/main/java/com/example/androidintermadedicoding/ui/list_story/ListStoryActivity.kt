package com.example.androidintermadedicoding.ui.list_story

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintermadedicoding.R
import com.example.androidintermadedicoding.data.model.Story
import com.example.androidintermadedicoding.data.view_model.AuthenticationFactory
import com.example.androidintermadedicoding.data.view_model.StoryViewModel
import com.example.androidintermadedicoding.databinding.ActivityListStoryBinding
import com.example.androidintermadedicoding.ui.AddStoryActivity
import com.example.androidintermadedicoding.ui.AuthenticationActivity
import com.example.androidintermadedicoding.utils.Status
import com.example.androidintermadedicoding.utils.preference.PreferenceFactory
import com.example.androidintermadedicoding.utils.preference.PreferenceViewModel
import org.koin.android.ext.android.inject

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private val factoryPref: PreferenceFactory by inject()
    private val storisAdapter: StorisAdapter by inject()
    private val prefViewModel: PreferenceViewModel by viewModels { factoryPref }

    private val authenticationFactory: AuthenticationFactory by inject()
    private val storyViewModel: StoryViewModel by viewModels { authenticationFactory }


    private val prefFactory: PreferenceFactory by inject()
    private val pref: PreferenceViewModel by viewModels { prefFactory }

    companion object {
        val nameClass: String = ListStoryActivity::class.java.simpleName
    }

    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        token = intent.getStringExtra("token").toString()
        pref.saveBearerKey(token)

        binding.apply {
            setSupportActionBar(listStoryToolbar)
            supportActionBar?.title = "StoryApp"

            listStoryRv.apply {

                layoutManager = LinearLayoutManager(this@ListStoryActivity)
                setHasFixedSize(true)
                adapter = storisAdapter

                getData()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        pref.getBearerKey().observe(this) {


            binding.apply {
                storyViewModel.getAllStories(it)
                    .observe(this@ListStoryActivity) { status ->
                        when (status) {

                            is Status.Loading -> {
                                grupStoris.visibility = View.GONE
                                pbLoading.visibility = View.VISIBLE
                            }

                            is Status.Success -> {
                                grupStoris.visibility = View.VISIBLE
                                pbLoading.visibility = View.GONE

                                val data = status.data

                                storisAdapter.setList(data.listStory as ArrayList<Story?>?)
                                toastMessage(data.message.toString())
                            }

                            is Status.Error -> {
                                grupStoris.visibility = View.VISIBLE
                                pbLoading.visibility = View.GONE
                                toastMessage("Some Thing Wrong")
                            }

                        }

                    }
            }
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> startActivity(Intent(this@ListStoryActivity, AddStoryActivity::class.java))

            R.id.logout -> {
                prefViewModel.deleteBearerKey()
                startActivity(Intent(this@ListStoryActivity, AuthenticationActivity::class.java))
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }
}