package com.streamwide.mycontacts.presentation.homescreen

import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.streamwide.mycontacts.R
import com.streamwide.mycontacts.data.local.model.UserContact
import com.streamwide.mycontacts.databinding.FragmentHomeScreenBinding
import com.streamwide.mycontacts.domain.model.ContactModel
import com.streamwide.mycontacts.presentation.homescreen.adapters.ContactAdapter
import com.streamwide.mycontacts.utils.Constants.REQUEST_CODE_CONTACT
import com.streamwide.mycontacts.utils.PermissionTracking
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class HomeScreenFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeScreenBinding

    lateinit var userContactListAdapter: ContactAdapter
    lateinit var recyclerViewContactList: RecyclerView
    lateinit var searchEditText: EditText
    lateinit var textNoData: TextView

    var contactList:ArrayList<ContactModel> = arrayListOf()
    var listOfContact:ArrayList<UserContact> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkContactPermissions()
        initContactRecycleView()
        initViews()
        loadContacts()
        observeContactsList()
        searchEditTextChangedListener()
    }

    private fun checkContactPermissions():Boolean{
        if (PermissionTracking.hasCOntactPermissions(requireContext())){
            return true
        }else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            EasyPermissions.requestPermissions(
                this,
                "You will need to accept the permission in order to run the application",
                REQUEST_CODE_CONTACT,
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_CONTACTS,
            )
            return true
        }else{
            return false
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }else{
            checkContactPermissions()
        }
    }

    private fun initContactRecycleView(){
        view?.let { view ->
            recyclerViewContactList = view.findViewById(R.id.rcvContact)
            userContactListAdapter = ContactAdapter( ArrayList<UserContact>())
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerViewContactList.layoutManager = layoutManager
            recyclerViewContactList.adapter = userContactListAdapter

           userContactListAdapter.itemClickListener = { position, contactInfo ->

                val arguments = Bundle().apply {
                    putString("contactName", contactInfo?.name)
                    putString("contactPhone", contactInfo?.phone)
                }

                findNavController().navigate(
                    R.id.action_homeFragment_to_detailPhotoFragment,
                    arguments
                )
            }


        }
    }

    private fun initViews() {
        view?.let { view ->
            searchEditText = view.findViewById(R.id.ed_search)
            textNoData = view.findViewById(R.id.text_no_data)
        }
    }

    private fun searchEditTextChangedListener() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (s.toString().isNullOrEmpty()){
                    observeContactsList()
                    recyclerViewContactList.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                }
                else{
                    viewModel.getContactsBySearch(s.toString())
                    observeSearchList()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun loadContacts() {
        contactList.clear()
        val cursor = context?.contentResolver
            ?.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,

                    ),null,null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
        while (cursor!!.moveToNext()){
            val contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contactModel =  ContactModel(contactName,contactNumber)
            contactList.add(contactModel)
            Log.d("TAG", "loadContacts: $contactName")
        }
        cursor.close()
        viewModel.saveContacts(contactList)

    }

    private fun observeContactsList() {
        viewModel.contactList.removeObservers(viewLifecycleOwner)
        viewModel.contactList.observe(viewLifecycleOwner) { list ->
            listOfContact = ArrayList(list)
            userContactListAdapter.setNewContactList(listOfContact)
        }
    }

    private fun observeSearchList() {
        viewModel.contactList.removeObservers(viewLifecycleOwner)
        viewModel.searchList.observe(viewLifecycleOwner) { searchlist ->

            if (searchlist.isNotEmpty()){
                listOfContact = ArrayList(searchlist)
                userContactListAdapter.setNewContactList(listOfContact)
            }
            else{
                recyclerViewContactList.visibility = View.GONE
              textNoData.visibility = View.VISIBLE
            }

        }
    }
}