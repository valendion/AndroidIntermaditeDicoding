package com.example.androidintermadedicoding.data.view_model.utils

import com.example.androidintermadedicoding.data.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

object DataDummy {
    const val email = "lio@gmail.com"
    const val password = "123456"
    const val name = "lio"
    const val token = "token"
    val description = "Dummy Description".toRequestBody()
    val file = MultipartBody.Part.create("dummy".toRequestBody())
    const val id = "Dummy  id"


    fun generateDummyResponseLogin(): ResponseLogin {
        val loginResult = LoginResult(
            userId = "user-SAKJB0rUEHBT3I7k",
            name = "lio",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
        )

        return ResponseLogin(
            error = false,
            message = "success",
            loginResult = loginResult
        )
    }

    fun generateDummyResponseRegister(): ResponseRegister {
        return ResponseRegister(
            error = false,
            message = "User created"
        )
    }

    fun generateDummyResponseLocation(): ResponseAllStory {
        val json = """
            {
                "error": false,
                "message": "Stories fetched successfully",
                "listStory": [
                    {
                        "id": "story-CeC-442IdYCyeOpA",
                        "name": "noldy",
                        "description": "It is a cat",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668783718682__CNgj4TJ.jpg",
                        "createdAt": "2022-11-18T15:01:58.683Z",
                        "lat": -8.58661,
                        "lon": 120.2649217
                    },
                    {
                        "id": "story-ZdLLhvlecDqD8prB",
                        "name": "69",
                        "description": "e",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668783677695_LYEwZPg5.jpg",
                        "createdAt": "2022-11-18T15:01:17.696Z",
                        "lat": -6.145959,
                        "lon": 106.836395
                    },
                    {
                        "id": "story-uSMLu0zXnmGEsIQd",
                        "name": "69",
                        "description": "h",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668782972215_MUitk8_x.jpg",
                        "createdAt": "2022-11-18T14:49:32.216Z",
                        "lat": -6.145959,
                        "lon": 106.836395
                    },
                    {
                        "id": "story-fOz-9H265_YMMLSM",
                        "name": "69",
                        "description": "g",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668782300427_agucJeQk.jpg",
                        "createdAt": "2022-11-18T14:38:20.428Z",
                        "lat": -6.145959,
                        "lon": 106.836395
                    },
                    {
                        "id": "story-t9owHOFBSB1DDV-9",
                        "name": "69",
                        "description": "g",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668782111451_eD1KKpMl.jpg",
                        "createdAt": "2022-11-18T14:35:11.452Z",
                        "lat": -6.145959,
                        "lon": 106.836395
                    },
                    {
                        "id": "story-YBek4kCAtxeyh4c7",
                        "name": "review",
                        "description": "test",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668780379607_PV0pC1P8.jpg",
                        "createdAt": "2022-11-18T14:06:19.608Z",
                        "lat": -6.273375,
                        "lon": 106.983085
                    },
                    {
                        "id": "story-O3HdBaiDiSGvC27c",
                        "name": "test",
                        "description": "test",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668779805943_-I4Xnycm.jpg",
                        "createdAt": "2022-11-18T13:56:45.946Z",
                        "lat": -6.273738,
                        "lon": 106.847182
                    },
                    {
                        "id": "story-XbFi8-AUFj2HI9Tc",
                        "name": "cipmunk",
                        "description": "ysys",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668777079632_Em7V6tgC.jpg",
                        "createdAt": "2022-11-18T13:11:19.635Z",
                        "lat": -6.9287008,
                        "lon": 109.1409954
                    },
                    {
                        "id": "story-ST3Uxgb7fMsXC0VJ",
                        "name": "cipmunk",
                        "description": "cek",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668776503325_MlQPIlgy.jpg",
                        "createdAt": "2022-11-18T13:01:43.327Z",
                        "lat": -6.9287008,
                        "lon": 109.1409954
                    },
                    {
                        "id": "story-l-B2sJIJnislzM54",
                        "name": "cipmunk",
                        "description": "cek",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1668776121157_sw3rmX6K.jpg",
                        "createdAt": "2022-11-18T12:55:21.213Z",
                        "lat": -6.9287008,
                        "lon": 109.1409954
                    }
                ]
            }
        """.trimIndent()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val adapter = moshi.adapter(ResponseAllStory::class.java)
        return adapter.fromJson(json)!!
    }

    fun generateDummyResponseDetail(): ResponseDetail {
        val json = """
            {
                "error": false,
                "message": "Stories fetched successfully",
                "listStory": [
                    {
                        "id": "story-bVFHHyRUOAxAqWza",
                        "name": "deri",
                        "description": "hello world",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1669343551751_RldtnNyO.jpg",
                        "createdAt": "2022-11-25T02:32:31.753Z",
                        "lat": null,
                        "lon": null
                    },
                    {
                        "id": "story-crV1r4NdKGNQNZE7",
                        "name": "rev",
                        "description": "reviewer",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1669343144352_1f_eN1p5.jpg",
                        "createdAt": "2022-11-25T02:25:44.354Z",
                        "lat": null,
                        "lon": null
                    },
                    {
                        "id": "story-KVcVG6wirn0Hw6FX",
                        "name": "aka",
                        "description": "lorem ipsum",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1669343045432_8dgJ3xNf.jpg",
                        "createdAt": "2022-11-25T02:24:05.435Z",
                        "lat": null,
                        "lon": null
                    },
                    {
                        "id": "story-3-W2nSWwAxGGkGRA",
                        "name": "sabha",
                        "description": "hhhhhhhhhhhhhh",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1669341291934_eP9F5sw3.jpg",
                        "createdAt": "2022-11-25T01:54:51.935Z",
                        "lat": null,
                        "lon": null
                    },
                    {
                        "id": "story-IqHnkSWZ9kI7MGsq",
                        "name": "sabha",
                        "description": "rtetertertert",
                        "photoUrl": "https://story-api.dicoding.dev/images/stories/photos-1669341282911_KxEbiI6g.jpg",
                        "createdAt": "2022-11-25T01:54:42.913Z",
                        "lat": null,
                        "lon": null
                    }
                ]
            }
        """.trimIndent()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val adapter = moshi.adapter(ResponseDetail::class.java)
        return adapter.fromJson(json)!!
    }


}