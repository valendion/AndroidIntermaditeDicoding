package com.example.androidintermadedicoding.ui.list_story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidintermadedicoding.R
import com.example.androidintermadedicoding.data.model.Story
import com.example.androidintermadedicoding.databinding.ItemStoryBinding

class StorisAdapter : RecyclerView.Adapter<StorisAdapter.StoryViewHolder>() {

    private var _stories = arrayListOf<Story>()

    fun setList(list: ArrayList<Story?>?){
        if (list != null) {
            list.forEach {
                if (it != null){
                    add(it)
                }
            }
        }
    }

    private fun add(story: Story) {
        _stories.add(story)
        notifyItemInserted(_stories.size)
    }

    inner class StoryViewHolder(val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story){
            binding.apply {
                imageStory.load(story.photoUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image_24)
                }
                titleImage.text = story.name
                descriptionImage.text = story.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(_stories[position])
    }

    override fun getItemCount(): Int = _stories.size
}