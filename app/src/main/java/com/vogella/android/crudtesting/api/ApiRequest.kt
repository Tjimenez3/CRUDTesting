package com.vogella.android.crudtesting.api


import com.vogella.android.crudtesting.model.Contacts
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiRequest {

    @GET("/fireblog/posts.json")
    suspend fun getContacts(): Response<Map<String, Contacts>>

    @PATCH("/fireblog/posts/{id}.json")
    suspend fun updateContact(@Path("id") id: String, @Body requestBody: RequestBody): Response<ResponseBody>

    @PUT("/fireblog/posts/{id}.json")
    suspend fun createNewContact(@Path("id") id: String, @Body requestBody: RequestBody): Response<ResponseBody>

    @DELETE("/fireblog/posts/{id}.json")
    suspend fun deleteContact(@Path("id") id: String): Response<ResponseBody>

}
