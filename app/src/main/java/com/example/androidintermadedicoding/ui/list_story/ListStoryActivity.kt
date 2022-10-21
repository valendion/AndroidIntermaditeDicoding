package com.example.androidintermadedicoding.ui.list_story

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintermadedicoding.R
import com.example.androidintermadedicoding.data.view_model.AuthenticationFactory
import com.example.androidintermadedicoding.data.view_model.StoryViewModel
import com.example.androidintermadedicoding.databinding.ActivityListStoryBinding
import com.example.androidintermadedicoding.ui.AddActivity
import com.example.androidintermadedicoding.ui.AuthenticationActivity
import com.example.androidintermadedicoding.ui.SettingActivity
import com.example.androidintermadedicoding.utils.Status
import com.example.androidintermadedicoding.utils.preference.PreferenceFactory
import com.example.androidintermadedicoding.utils.preference.PreferenceViewModel
import org.koin.android.ext.android.inject

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private val factoryPref : PreferenceFactory by inject()
    private val storisAdapter: StorisAdapter by inject()
    private val prefViewModel: PreferenceViewModel by viewModels{factoryPref}

    private val authenticationFactory: AuthenticationFactory by inject()
    private val storyViewModel: StoryViewModel by viewModels{authenticationFactory}

    companion object{
        val nameClass: String = ListStoryActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(listStoryToolbar)
            supportActionBar?.title = "StoryApp"

            listStoryRv.apply {

                layoutManager = LinearLayoutManager(this@ListStoryActivity)
                setHasFixedSize(true)
                adapter = storisAdapter

            }

            storyViewModel.getAllStories().observe(this@ListStoryActivity){status ->
                when(status){

                    is Status.Loading -> {
                        grupStoris.visibility = View.GONE
                        pbLoading.visibility = View.VISIBLE
                    }

                    is Status.Success -> {
                        grupStoris.visibility = View.VISIBLE
                        pbLoading.visibility = View.GONE

                        val data = status.data
                        Log.e(nameClass, data.toString())
                        storisAdapter.setList(data.listStory)
                    }

                    is Status.Error -> {
                        grupStoris.visibility = View.VISIBLE
                        pbLoading.visibility = View.GONE
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
        when(item.itemId){
            R.id.add -> startActivity(Intent(this@ListStoryActivity, AddActivity::class.java))
            R.id.setting -> startActivity(Intent(this@ListStoryActivity, SettingActivity::class.java))
            R.id.logout -> {
                prefViewModel.deleteBearerKey()
                startActivity(Intent(this@ListStoryActivity, AuthenticationActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}