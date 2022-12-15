package com.streamwide.mycontacts.presentation.detailcontactscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.streamwide.mycontacts.R

class ContactDetail : Fragment() {

    var contactName: String = ""
    var contactPhone: String = ""

    lateinit var tvContactName: TextView
    lateinit var tvContactPhone: TextView
    lateinit var back_img : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAllArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        handleContactDetail()

    }

    private fun getAllArguments(){
        contactName = arguments.let { it?.getSerializable("contactName") as String }
        contactPhone = arguments.let { it?.getSerializable("contactPhone") as String }
    }

    private fun initViews(view: View) {
        tvContactName = view.findViewById(R.id.textname)
        tvContactPhone = view.findViewById(R.id.text_phone)
        back_img = view.findViewById(R.id.back_img)

        back_img.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun handleContactDetail(){
        tvContactName.text = contactName
        tvContactPhone.text = contactPhone
    }

}