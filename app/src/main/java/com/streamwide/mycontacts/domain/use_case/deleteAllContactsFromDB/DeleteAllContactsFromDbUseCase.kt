package com.streamwide.mycontacts.domain.use_case.deleteAllContactsFromDB

import com.streamwide.mycontacts.domain.repository.ContactRepository
import javax.inject.Inject

class DeleteAllContactsFromDbUseCase @Inject constructor (private val repository: ContactRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllContactsFromDb()
    }
}