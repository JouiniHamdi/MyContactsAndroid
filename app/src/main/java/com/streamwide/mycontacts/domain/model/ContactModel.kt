package com.streamwide.mycontacts.domain.model

import com.streamwide.mycontacts.data.local.model.UserContact

data class ContactModel (
    val name : String,
   val  phone : String
        )

fun ContactModel.toDatabaseModel(): UserContact {
    return UserContact(
        name = name,
        phone = phone,
    )
}