package com.streamwide.mycontacts.data.repository

import com.streamwide.mycontacts.data.local.dao.UserContactDao
import com.streamwide.mycontacts.data.local.model.UserContact
import com.streamwide.mycontacts.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow

class UserContactRepositoryImpl( private val contactDao: UserContactDao) :ContactRepository {

    override suspend fun saveContactsToDb(contactList: List<UserContact>) = contactDao.insertAllContacts(contactList)

    override fun getContactsFromDb(): Flow<List<UserContact>> = contactDao.getAllContacts()

    override fun deleteAllContactsFromDb() = contactDao.deleteAllContacts()

    override fun getContactByNameOrPhone(nameOrPhone: String): Flow<List<UserContact>> = contactDao.getContactByNameORPhone(nameOrPhone)
}