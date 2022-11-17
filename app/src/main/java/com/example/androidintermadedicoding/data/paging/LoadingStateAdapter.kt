package com.example.androidintermadedicoding.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidintermadedicoding.databinding.ItemLoadingBinding

class LoadingStateAdapter(private val retry: () -> Unit): LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    class LoadingStateViewHolder(private val binding: ItemLoadingBinding, retry: () -> Unit): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState){
            if (loadState is LoadState.Error){
                binding.errorMessage.text = loadState.error.localizedMessage
            }

            binding.pbLoading.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.errorMessage.isVisible = loadState is LoadState.Error

        }
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return LoadingStateViewHolder(binding, retry)
    }


}