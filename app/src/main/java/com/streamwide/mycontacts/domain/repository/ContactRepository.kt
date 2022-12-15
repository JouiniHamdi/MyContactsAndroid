package com.streamwide.mycontacts.domain.repository

import com.streamwide.mycontacts.data.local.model.UserContact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun saveContactsToDb(contactList : List<UserContact>)
    fun getContactsFromDb() : Flow<List<UserContact>>
    fun deleteAllContactsFromDb()
    fun getContactByNameOrPhone (nameOrPhone : String) : Flow<List<UserContact>>
}