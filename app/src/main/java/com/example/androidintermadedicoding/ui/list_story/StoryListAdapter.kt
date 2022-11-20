package com.example.androidintermadedicoding.ui.list_story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidintermadedicoding.R
import com.example.androidintermadedicoding.data.model.Story
import com.example.androidintermadedicoding.databinding.ItemStoryBinding
import com.example.androidintermadedicoding.ui.DetailActivity

class StoryListAdapter:PagingDataAdapter<Story, StoryListAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    class StoryViewHolder(private val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story){
            binding.apply {
                imageStory.load(story.photoUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image_24)
                }
                titleImage.text = story.name

                itemCard.setOnClickListener {


                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            binding.root.context as Activity,
                            Pair(imageStory, "image"),
                            Pair(titleImage, "description")
                        )

                    binding.root.context.startActivity(
                        Intent(binding.root.context, DetailActivity::class.java
                        ).putExtra("id",story.id), optionsCompat.toBundle())
                }
            }
        }
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    companion object{
         val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>(){
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }


}