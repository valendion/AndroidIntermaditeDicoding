package com.example.androidintermadedicoding.ui.list_story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidintermadedicoding.R
import com.example.androidintermadedicoding.data.model.Story
import com.example.androidintermadedicoding.databinding.ItemStoryBinding
import com.example.androidintermadedicoding.ui.DetailActivity
import androidx.core.util.Pair

class StorisAdapter : RecyclerView.Adapter<StorisAdapter.StoryViewHolder>() {

    private var _stories = arrayListOf<Story>()

    fun setList(list: ArrayList<Story?>?){
        _stories.clear()
        notifyDataSetChanged()
        list?.forEach {
            if (it != null){
                add(it)
            }
        }
    }

    private fun add(story: Story) {
        _stories.add(story)
        notifyItemInserted(_stories.size)
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(_stories[position])
    }

    override fun getItemCount(): Int = _stories.size
}