package com.example.androidintermadedicoding.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidintermadedicoding.data.model.Story
import com.example.androidintermadedicoding.data.network.ApiServiceStory

class StoryPagingSource(private val apiServiceStory: ApiServiceStory,private val token: String): PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiServiceStory.getAllStoryPaging(token, position, params.loadSize)

            LoadResult.Page(
                data = responseData.listStory?.filterNotNull() ?: listOfNotNull(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else position + 1
            )
        }catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }
    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }
}