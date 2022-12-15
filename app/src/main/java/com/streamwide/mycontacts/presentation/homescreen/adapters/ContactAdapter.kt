package com.streamwide.mycontacts.presentation.homescreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.streamwide.mycontacts.data.local.model.UserContact
import com.streamwide.mycontacts.databinding.ItemContactBinding

class ContactAdapter(
    var contactList: ArrayList<UserContact>
)
    : RecyclerView.Adapter<ContactAdapter.MyViewHolder>(){

    var itemClickListener: ((position: Int, userContact: UserContact) -> Unit)? = null

    fun setNewContactList(newContactList: ArrayList<UserContact>) {
        contactList = newContactList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTV = binding.nameTV
        val numberTV = binding.numberTV
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = contactList[position]
        holder.binding.nameTV.text = item.name
        holder.binding.numberTV.text = item.phone
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(position,contactList[position])
        }
    }

    override fun getItemCount(): Int = contactList.size
}