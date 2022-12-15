package com.streamwide.mycontacts.domain.use_case.saveContactsToDb

import com.streamwide.mycontacts.data.local.model.UserContact
import com.streamwide.mycontacts.domain.repository.ContactRepository
import javax.inject.Inject

class SaveContactToDbUseCase @Inject constructor(
    private val repository: ContactRepository
    ) {
    suspend operator fun invoke(contactList : List<UserContact>)  {
        repository.saveContactsToDb(contactList)
    }
}