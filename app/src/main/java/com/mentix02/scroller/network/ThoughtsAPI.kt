package com.mentix02.scroller.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import com.mentix02.scroller.models.Thought
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://169.233.147.48:8000/thoughts/"

interface ThoughtsAPI {

    @GET("list")
    fun getThoughts() : Call<List<Thought>>

    companion object {
        operator fun invoke() : ThoughtsAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ThoughtsAPI::class.java)
        }
    }

}
