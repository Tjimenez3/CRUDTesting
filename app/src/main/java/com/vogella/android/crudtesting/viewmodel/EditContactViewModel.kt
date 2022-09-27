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

class EditContactViewModel(private val api: ApiRequest): ViewModel() {


    private val _isError: MutableLiveData<Throwable> = MutableLiveData()
    val isError: LiveData<Throwable>
        get() {
            return _isError
        }

    fun makeApiUpdateRequest(contact: Contacts) {
        viewModelScope.launch {
            runCatching {
                val json = Gson().toJson(contact)
                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
                api.updateContact(contact.id,requestBody)
            }.onSuccess {

            }.onFailure {
                processWithError(it)
            }
        }
    }
    private fun processWithError(t: Throwable) {
        _isError.postValue(t)
    }
}