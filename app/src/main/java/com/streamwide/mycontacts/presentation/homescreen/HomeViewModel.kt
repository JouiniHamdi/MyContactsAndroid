package com.streamwide.mycontacts.presentation.homescreen

import androidx.lifecycle.*
import com.streamwide.mycontacts.data.local.model.UserContact
import com.streamwide.mycontacts.domain.model.ContactModel
import com.streamwide.mycontacts.domain.model.toDatabaseModel
import com.streamwide.mycontacts.domain.use_case.deleteAllContactsFromDB.DeleteAllContactsFromDbUseCase
import com.streamwide.mycontacts.domain.use_case.getContactsFromDB.GetContactsFromDbUseCase
import com.streamwide.mycontacts.domain.use_case.saveContactsToDb.SaveContactToDbUseCase
import com.streamwide.mycontacts.domain.use_case.searchContact.SearchContactUSeCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val saveContactToDbUseCase: SaveContactToDbUseCase,
    private val getContactsFromDbUseCase: GetContactsFromDbUseCase,
    private val deleteAllContactsFromDbUseCase: DeleteAllContactsFromDbUseCase,
    private val SearchContactUSeCase : SearchContactUSeCase
) : ViewModel(){

    val contactList: LiveData<List<UserContact>> = getContactsFromDbUseCase.invoke().asLiveData()

    private var _searchList = MutableLiveData<List<UserContact>>()
    val searchList : LiveData<List<UserContact>> get() = _searchList

     fun saveContacts( contacts : List<ContactModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            saveContactToDbUseCase.invoke(contacts.map { it.toDatabaseModel() })
        }

    }

    fun getContactsBySearch( searchWord : String){
        viewModelScope.launch(Dispatchers.IO) {
            SearchContactUSeCase.invoke(searchWord).collect {
              _searchList.postValue(it)
            }
        }
    }
}