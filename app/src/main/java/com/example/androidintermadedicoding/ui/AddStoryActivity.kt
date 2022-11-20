package com.example.androidintermadedicoding.ui

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.androidintermadedicoding.data.view_model.AuthenticationFactory
import com.example.androidintermadedicoding.data.view_model.StoryViewModel
import com.example.androidintermadedicoding.databinding.ActivityAddStoryBinding
import com.example.androidintermadedicoding.utils.ImageModif
import com.example.androidintermadedicoding.utils.Status
import com.example.androidintermadedicoding.utils.preference.PreferenceFactory
import com.example.androidintermadedicoding.utils.preference.PreferenceViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File


class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private val authenticationFactory: AuthenticationFactory by inject()
    private val storyViewModel: StoryViewModel by viewModels { authenticationFactory }

    private val prefFactory: PreferenceFactory by inject()
    private val pref: PreferenceViewModel by viewModels{prefFactory}



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    applicationContext,
                    "Tidak mendapatkan permission",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    private var descriptionText = ""

    private var myFile: File? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.apply {

            setSupportActionBar(addStoryToolbar)
            supportActionBar?.title = "Add Story"
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            addStoryToolbar.setNavigationOnClickListener {
                finish()
            }


            cameraBtn.setOnClickListener {
                startTakePhoto()
            }

            galeriBtn.setOnClickListener {
                startGallery()
            }



            uploadBtn.setOnClickListener {
                descriptionText = inputDescription.text.toString()
                if (descriptionText.isEmpty() || myFile == null){
                    Toast.makeText(applicationContext, "Lengkapi Data Anda", Toast.LENGTH_SHORT).show()
                }else{
                    val reSizeImage= ImageModif.reduceFileImage(myFile!!)
                    val description = descriptionText.toRequestBody("text/plain".toMediaType())
                    val requestImageFile = reSizeImage.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", reSizeImage.name, requestImageFile)

                    pref.getBearerKey().observe(this@AddStoryActivity){
                        storyViewModel.postStory(it, description, imageMultipart).observe(this@AddStoryActivity){status ->
                            when (status) {
                                is Status.Loading -> {
                                    grupAdd.visibility = View.GONE
                                    pbLoading.visibility = View.VISIBLE
                                }

                                is Status.Success -> {
                                    grupAdd.visibility = View.VISIBLE
                                    pbLoading.visibility = View.GONE

                                    val data = status.data

                                    Toast.makeText(
                                        applicationContext,
                                        data.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    finish()

                                }

                                is Status.Error -> {
                                    grupAdd.visibility = View.VISIBLE
                                    pbLoading.visibility = View.GONE

                                    val data = status.error

                                    Log.e(DetailActivity.nameClass, data)

                                    Toast.makeText(applicationContext, "Some Think Wrong", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                }
            }
        }
    }



    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == RESULT_OK){
             myFile = File(currentPhotoPath)
            val result = BitmapFactory.decodeFile(myFile!!.path)
            binding.imageStory.load(result){
                transformations(
                    RoundedCornersTransformation(32F)
                )
                build()
            }
        }
    }

    private fun startTakePhoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        ImageModif.createCustomTempFile(application).apply {
            val photoUri: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,
                "com.example.androidintermadedicoding",
                this
            )
            currentPhotoPath = this.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentGalery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == RESULT_OK){
            val selectImg: Uri = it.data?.data as Uri
             myFile = ImageModif.uriToFile(selectImg,  this@AddStoryActivity)
            binding.imageStory.load(selectImg){
                transformations(
                    RoundedCornersTransformation(32F)
                )
                build()
            }
        }
    }

    private fun startGallery(){
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGalery.launch(chooser)
    }

    companion object {

        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}