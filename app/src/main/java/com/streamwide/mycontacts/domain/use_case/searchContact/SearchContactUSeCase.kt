package com.streamwide.mycontacts.domain.use_case.searchContact

import com.streamwide.mycontacts.data.local.model.UserContact
import com.streamwide.mycontacts.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchContactUSeCase @Inject constructor(
    private val repository: ContactRepository
){
     operator fun invoke(searchBY : String): Flow<List<UserContact>> {
        return repository.getContactByNameOrPhone(searchBY)
    }
}