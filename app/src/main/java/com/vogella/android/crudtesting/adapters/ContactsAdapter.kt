package com.vogella.android.crudtesting.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.vogella.android.crudtesting.databinding.NamesCardViewBinding
import com.vogella.android.crudtesting.model.Contacts

class ContactsAdapter() : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var mContactsList: List<Contacts>? = null

    private val _isDelete: MutableLiveData<String> = MutableLiveData()
    val isDelete: LiveData<String>
        get() {
            return _isDelete
        }

    private val _isUpdate: MutableLiveData<Contacts> = MutableLiveData()
    val isUpdate: LiveData<Contacts>
        get() {
            return _isUpdate
        }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Contacts>?) {
        mContactsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            NamesCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mContactsList!![position]

        holder.firstName.text = itemsViewModel.firstName
        holder.lastName.text = itemsViewModel.lastName
        holder.contactNumber.text = itemsViewModel.contactNumber

        holder.updateButton.setOnClickListener {
            _isUpdate.postValue(Contacts(itemsViewModel.firstName,itemsViewModel.lastName,itemsViewModel.contactNumber,itemsViewModel.id))
        }
        holder.deleteButton.setOnClickListener {
            _isDelete.postValue(itemsViewModel.id)
        }
    }

    override fun getItemCount(): Int {
        return mContactsList?.size ?: 0
    }

    class ViewHolder(binding: NamesCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val firstName: TextView = binding.firstName
        val lastName: TextView = binding.lastName
        val contactNumber: TextView =binding.contactNumber
        val updateButton: Button = binding.updateButton
        val deleteButton: Button = binding.deleteButton
    }

}
