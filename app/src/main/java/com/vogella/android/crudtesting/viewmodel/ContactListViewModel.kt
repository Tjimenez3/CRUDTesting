package com.vogella.android.crudtesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vogella.android.crudtesting.api.ApiRequest
import com.vogella.android.crudtesting.model.Contacts
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class ContactListViewModel(private val api: ApiRequest): ViewModel() {


    private val _responseGetContacts: MutableLiveData<Collection<Contacts>?> = MutableLiveData()
    val responseGetContacts: LiveData<Collection<Contacts>?>
        get() {
            return _responseGetContacts
        }

    private val _isError: MutableLiveData<Throwable> = MutableLiveData()
    val isError: LiveData<Throwable>
        get() {
            return _isError
        }

    fun makeApiRequest() {
        viewModelScope.launch {
            runCatching {
                api.getContacts()
            }.onSuccess {
                processWithContactGetResponse(it)
            }.onFailure {
                processWithError(it)
            }
        }
    }
    fun makeApiDeleteRequest(id: String) {
        viewModelScope.launch {
            runCatching {
                api.deleteContact(id)
            }.onSuccess {
            }.onFailure {
                processWithError(it)
            }
        }
    }

    private fun processWithContactGetResponse(contactResponse: Response<Map<String, Contacts>>) {
        val entries = contactResponse.body()?.values
        _responseGetContacts.postValue(entries)
    }

    private fun processWithError(t: Throwable) {
        _isError.postValue(t)
    }
}