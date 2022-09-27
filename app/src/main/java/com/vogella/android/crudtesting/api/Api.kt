package com.vogella.android.crudtesting.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Api(private val baseUrl: String) {


    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun provideApiCall(): ApiRequest {
        return provideRetrofit().create(ApiRequest::class.java)
    }
}