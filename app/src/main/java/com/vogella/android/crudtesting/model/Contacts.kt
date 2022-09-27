package com.vogella.android.crudtesting.model

import java.io.Serializable

data class Contacts(
    val firstName: String,
    val lastName: String,
    val contactNumber: String,
    val id: String
): Serializable