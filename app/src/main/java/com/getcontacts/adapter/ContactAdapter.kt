package com.getcontacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getcontacts.databinding.ContactItemBinding
import com.getcontacts.model.PhoneNumber

class ContactAdapter(private val phoneNumber: ArrayList<PhoneNumber>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

  class ContactViewHolder(val contactItemBinding: ContactItemBinding) : RecyclerView.ViewHolder(contactItemBinding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
    val contactItemBinding =
      ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ContactViewHolder(contactItemBinding)
  }

  override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
    holder.contactItemBinding.tvName.text = phoneNumber[position].name
    holder.contactItemBinding.tvPhoneNum.text = phoneNumber[position].number
  }

  override fun getItemCount(): Int = phoneNumber.size

}