package com.streamwide.mycontacts.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.streamwide.mycontacts.data.local.model.UserContact
import kotlinx.coroutines.flow.Flow

@Dao
interface UserContactDao {

    @Query("SELECT * FROM Contact")
    fun getAllContacts(): Flow<List<UserContact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContacts(contactList: List<UserContact>)

    @Query("DELETE FROM Contact")
    fun deleteAllContacts()

    @Query("SELECT * FROM Contact WHERE  name  LIKE  '%'|| :nameOrPhone || '%' OR phone LIKE '%'|| :nameOrPhone || '%' order By name ASC")
    fun getContactByNameORPhone(
        nameOrPhone: String
    ): Flow<List<UserContact>>

}