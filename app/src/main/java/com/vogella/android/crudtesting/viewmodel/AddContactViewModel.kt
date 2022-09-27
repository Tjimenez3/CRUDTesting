package com.vogella.android.crudtesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vogella.android.crudtesting.api.ApiRequest
import com.vogella.android.crudtesting.constants.UiConstant.RANDOM_CHARSET
import com.vogella.android.crudtesting.model.Contacts
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*

class AddContactViewModel(private val api: ApiRequest): ViewModel() {

    private val _responsePostNewContact: MutableLiveData<Boolean> = MutableLiveData()
    val responsePostNewContact: LiveData<Boolean>
        get() {
            return _responsePostNewContact
        }

    private val _isError: MutableLiveData<Throwable> = MutableLiveData()
    val isError: LiveData<Throwable>
        get() {
            return _isError
        }

    fun makeApiRequest(firstName: String, lastName: String, contactNumber: String) {
        viewModelScope.launch {
            runCatching {
                val randomLong = getRandomString(15)

                val contact = Contacts(firstName,lastName,contactNumber,randomLong)
                val json = Gson().toJson(contact)
                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

                api.createNewContact(randomLong,requestBody)
            }.onSuccess {
                processWithContactsResponse()
            }.onFailure {
                processWithError(it)
            }
        }
    }

    private fun processWithContactsResponse() {
        _responsePostNewContact.postValue(true)
    }
    private fun processWithError(t: Throwable) {
        _isError.postValue(t)
    }
    private fun getRandomString(length: Int): String {
        val charset = RANDOM_CHARSET
        return (1..(length))
            .map{ charset.random()}
            .joinToString()+Calendar.MILLISECOND
    }
}