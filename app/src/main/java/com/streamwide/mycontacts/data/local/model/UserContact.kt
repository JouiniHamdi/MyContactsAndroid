package com.streamwide.mycontacts.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact")
data class UserContact(

    var name: String? = "",
    var phone: String? = ""
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}