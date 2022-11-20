package com.example.androidintermadedicoding.ui.list_story

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintermadedicoding.R
import com.example.androidintermadedicoding.data.paging.LoadingStateAdapter
import com.example.androidintermadedicoding.data.view_model.AuthenticationFactory
import com.example.androidintermadedicoding.data.view_model.StoryViewModel
import com.example.androidintermadedicoding.databinding.ActivityListStoryBinding
import com.example.androidintermadedicoding.ui.AddStoryActivity
import com.example.androidintermadedicoding.ui.AuthenticationActivity
import com.example.androidintermadedicoding.ui.map.MapActivity
import com.example.androidintermadedicoding.utils.preference.PreferenceFactory
import com.example.androidintermadedicoding.utils.preference.PreferenceViewModel
import org.koin.android.ext.android.inject

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding

    private val factoryPref: PreferenceFactory by inject()

    //    private val storisAdapter: StorisAdapter by inject()
    private val storyPaginigAdapter: StoryListAdapter by inject()

    private val prefViewModel: PreferenceViewModel by viewModels { factoryPref }

    private val authenticationFactory: AuthenticationFactory by inject()
    private val storyViewModel: StoryViewModel by viewModels { authenticationFactory }


    private val prefFactory: PreferenceFactory by inject()
    private val pref: PreferenceViewModel by viewModels { prefFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(listStoryToolbar)
            supportActionBar?.title = "StoryApp"


                getData()



        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        binding.listStoryRv.apply {

            layoutManager = LinearLayoutManager(this@ListStoryActivity)
            adapter = storyPaginigAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyPaginigAdapter.retry()
                }
            )
        }
        pref.getBearerKey().observe(this) {
            binding.apply {
                storyViewModel.getAllStoryPaging(it).observe(this@ListStoryActivity) {
                        storyPaginigAdapter.addLoadStateListener {loadState ->
                            if (loadState.prepend.endOfPaginationReached){
                                binding.grupStoris.visibility = View.VISIBLE
                                binding.pbLoading.visibility = View.GONE
                            }else{
                                binding.grupStoris.visibility = View.GONE
                                binding.pbLoading.visibility = View.VISIBLE
                            }
                        }
                       storyPaginigAdapter.submitData(lifecycle, it)
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

            R.id.map -> {
                startActivity(Intent(this@ListStoryActivity, MapActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }


}