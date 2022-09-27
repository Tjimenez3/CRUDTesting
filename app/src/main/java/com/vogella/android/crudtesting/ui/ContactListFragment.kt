package com.vogella.android.crudtesting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vogella.android.crudtesting.App
import com.vogella.android.crudtesting.viewmodel.ContactListViewModel
import com.vogella.android.crudtesting.adapters.ContactsAdapter
import com.vogella.android.crudtesting.constants.UiConstant.GET_CONTACTS_ERROR
import com.vogella.android.crudtesting.databinding.FragmentContactListBinding

class ContactListFragment : DialogFragment() {
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!
    private val mContactsAdapter = ContactsAdapter()
    private var contactsViewModel = ContactListViewModel(App.getApi())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentContactListBinding.inflate(inflater, container, false)

        with(binding.recyclerview) {
            layoutManager = LinearLayoutManager(context)
            adapter = mContactsAdapter
        }
        contactsViewModel.makeApiRequest()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        contactsViewModel.responseGetContacts.observe(viewLifecycleOwner) {
            mContactsAdapter.setData(it?.toMutableList()?.toList())
        }
        contactsViewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(context,GET_CONTACTS_ERROR, Toast.LENGTH_SHORT).show()
        }
        mContactsAdapter.isUpdate.observe(viewLifecycleOwner) {
            val action = ContactListFragmentDirections.actionContactListFragmentToEditContactFragment(it)
            findNavController().navigate(action)
        }
        mContactsAdapter.isDelete.observe(viewLifecycleOwner) {
            contactsViewModel.makeApiDeleteRequest(it)
            contactsViewModel.makeApiRequest()
        }
        binding.fab.setOnClickListener {
            val action = ContactListFragmentDirections.actionNamesFragmentToAddNameFragment()
            findNavController().navigate(action)
        }
    }

}