package com.example.androidintermadedicoding.data.view_model

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import coil.annotation.ExperimentalCoilApi
import com.example.androidintermadedicoding.data.StoryRepository
import com.example.androidintermadedicoding.data.model.*
import com.example.androidintermadedicoding.data.view_model.utils.DataDummy
import com.example.androidintermadedicoding.data.view_model.utils.MainDispatcherRule
import com.example.androidintermadedicoding.data.view_model.utils.StoryPagingSource
import com.example.androidintermadedicoding.data.view_model.utils.getOrAwaitValue
import com.example.androidintermadedicoding.ui.list_story.StoryListAdapter
import com.example.androidintermadedicoding.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoilApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var storyViewModel: StoryViewModel

    private val dummyLoginStory = DataDummy.generateDummyResponseLogin()
    private val dummyEmail = DataDummy.email
    private val dummyPassword = DataDummy.password

    private val dummyRegisterStory = DataDummy.generateDummyResponseRegister()
    private val dummyName = DataDummy.name

    private val dummyToken = DataDummy.token
    private val dummyDescription = DataDummy.description
    private val dummyFile = DataDummy.file

    private val dummyLocation = DataDummy.generateDummyResponseLocation()

    private val dummyStory = dummyLocation.listStory?.filterNotNull() ?: listOfNotNull()

    private val dummyDetail = DataDummy.generateDummyResponseDetail()
    private val dummyId = DataDummy.id

    @Before
    fun setUp() {
        storyViewModel = StoryViewModel(storyRepository)
    }

    @Test
    fun `when Post LoginStory Should not null and Return Success`() {
        val expectedLogin = MutableLiveData<Status<ResponseLogin>>()

        expectedLogin.value = Status.Success(dummyLoginStory)

        `when`(storyRepository.postLogin(dummyEmail, dummyPassword)).thenReturn(expectedLogin)

        val actualLogin = storyViewModel.postLogin(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).postLogin(dummyEmail, dummyPassword)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is Status.Success)
    }

    @Test
    fun `when Post LoginStory Network Error Should Return Error`() {
        val expectedLogin = MutableLiveData<Status<ResponseLogin>>()
        expectedLogin.value = Status.Error("Error")
        `when`(storyRepository.postLogin(dummyEmail, dummyPassword)).thenReturn(expectedLogin)
        val actualLogin = storyViewModel.postLogin(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).postLogin(dummyEmail, dummyPassword)
        assertTrue(actualLogin is Status.Error)

        if (actualLogin is Status.Error){
            assertNotNull(actualLogin.error)
        }
    }


    @Test
    fun `when Post RegisterStory Should not null and Return Success`() {
        val expectedRegister = MutableLiveData<Status<ResponseRegister>>()

        expectedRegister.value = Status.Success(dummyRegisterStory)

        `when`(storyRepository.postRegister(dummyName, dummyEmail, dummyPassword)).thenReturn(
            expectedRegister
        )

        val actualRegister =
            storyViewModel.postRegister(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).postRegister(dummyName, dummyEmail, dummyPassword)
        assertNotNull(actualRegister)
        assertTrue(actualRegister is Status.Success)
    }

    @Test
    fun `when Post RegisterStory Network Error Should Return Error`() {
        val expectedRegister = MutableLiveData<Status<ResponseRegister>>()
        expectedRegister.value = Status.Error("Error")
        `when`(storyRepository.postRegister(dummyName, dummyEmail, dummyPassword)).thenReturn(
            expectedRegister
        )
        val actualRegister =
            storyViewModel.postRegister(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).postRegister(dummyName, dummyEmail, dummyPassword)
        assertTrue(actualRegister is Status.Error)

        if (actualRegister is Status.Error){
            assertNotNull(actualRegister.error)
        }
    }


    @Test
    fun `when Post AddStory Should not null and Return Success`() {
        val expectedAdd = MutableLiveData<Status<ResponseRegister>>()

        expectedAdd.value = Status.Success(dummyRegisterStory)

        `when`(storyRepository.postStory(dummyToken, dummyDescription, dummyFile)).thenReturn(
            expectedAdd
        )

        val actualAdd =
            storyViewModel.postStory(dummyToken, dummyDescription, dummyFile).getOrAwaitValue()

        Mockito.verify(storyRepository).postStory(dummyToken, dummyDescription, dummyFile)
        assertNotNull(actualAdd)
        assertTrue(actualAdd is Status.Success)
    }

    @Test
    fun `when Post AddStory Network Error Should Return Error`() {
        val expectedAdd = MutableLiveData<Status<ResponseRegister>>()
        expectedAdd.value = Status.Error("Error")
        `when`(storyRepository.postStory(dummyToken, dummyDescription, dummyFile)).thenReturn(
            expectedAdd
        )
        val actualAdd =
            storyViewModel.postStory(dummyToken, dummyDescription, dummyFile).getOrAwaitValue()

        Mockito.verify(storyRepository).postStory(dummyToken, dummyDescription, dummyFile)
        assertTrue(actualAdd is Status.Error)

        if (actualAdd is Status.Error){
            assertNotNull(actualAdd.error)
        }
    }


    @Test
    fun `when Get Location Should not null and Return Success`() {
        val expectedLocation = MutableLiveData<Status<ResponseAllStory>>()

        expectedLocation.value = Status.Success(dummyLocation)

        `when`(storyRepository.getAllLocation(dummyToken)).thenReturn(expectedLocation)

        val actualLocation = storyViewModel.getAllLocation(dummyToken).getOrAwaitValue()

        Mockito.verify(storyRepository).getAllLocation(dummyToken)
        assertNotNull(actualLocation)
        assertTrue(actualLocation is Status.Success)
    }

    @Test
    fun `when Get Location Network Error Should Return Error`() {
        val expectedLocation = MutableLiveData<Status<ResponseAllStory>>()
        expectedLocation.value = Status.Error("Error")
        `when`(storyRepository.getAllLocation(dummyToken)).thenReturn(expectedLocation)

        val actualLocation = storyViewModel.getAllLocation(dummyToken).getOrAwaitValue()

        Mockito.verify(storyRepository).getAllLocation(dummyToken)
        assertTrue(actualLocation is Status.Error)

        if (actualLocation is Status.Error){
            assertNotNull(actualLocation.error)
        }
    }

    @Test
    fun `when Get Detail Should not null and Return Success`() {
        val expectedDetail = MutableLiveData<Status<ResponseDetail>>()

        expectedDetail.value = Status.Success(dummyDetail)

        `when`(storyRepository.getDetailStories(dummyToken, dummyId) ).thenReturn(expectedDetail)

        val actualDetail = storyViewModel.getDetailStories(dummyToken, dummyId).getOrAwaitValue()

        Mockito.verify(storyRepository).getDetailStories(dummyToken, dummyId)
        assertNotNull(actualDetail)
        assertTrue(actualDetail is Status.Success)
    }

    @Test
    fun `when Get Detail Network Error Should Return Error`() {
        val expectedDetail = MutableLiveData<Status<ResponseDetail>>()
        expectedDetail.value = Status.Error("Error")
        `when`(storyRepository.getDetailStories(dummyToken, dummyId)).thenReturn(expectedDetail)

        val actualDetail = storyViewModel.getDetailStories(dummyToken, dummyId).getOrAwaitValue()

        Mockito.verify(storyRepository).getDetailStories(dummyToken, dummyId)
        assertTrue(actualDetail is Status.Error)

        if (actualDetail is Status.Error){
            assertNotNull(actualDetail.error)
        }
    }

    @Test
    fun `when GetStory Should Not Null`() = runTest {
        val data: PagingData<Story> = StoryPagingSource.snapshot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<Story>>()
        expectedStory.value = data

        `when`(storyRepository.getAllStoryyPaging(dummyToken)).thenReturn(expectedStory)

        val actualStory: PagingData<Story> =
            storyViewModel.getAllStoryPaging(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DIFF_CALLBACK,
            updateCallback =noopListUpdateCalback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory,differ.snapshot())
        assertEquals(dummyStory.size,differ.snapshot().size)
        assertEquals(dummyStory[0].id,differ.snapshot()[0]?.id)
    }

    private val noopListUpdateCalback = object : ListUpdateCallback {

        override fun onInserted(position: Int, count: Int) {}

        override fun onRemoved(position: Int, count: Int) {}

        override fun onMoved(fromPosition: Int, toPosition: Int) {}

        override fun onChanged(position: Int, count: Int, payload: Any?) {}

    }

}