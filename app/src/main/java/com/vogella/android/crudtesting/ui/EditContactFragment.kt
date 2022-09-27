package com.vogella.android.crudtesting.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vogella.android.crudtesting.App
import com.vogella.android.crudtesting.constants.UiConstant
import com.vogella.android.crudtesting.databinding.FragmentEditContactBinding
import com.vogella.android.crudtesting.model.Contacts
import com.vogella.android.crudtesting.viewmodel.EditContactViewModel

class EditContactFragment : Fragment() {

    private var _binding: FragmentEditContactBinding? =null
    private val binding get() = _binding!!
    private var editContactViewModel = EditContactViewModel(App.getApi())
    private val args: EditContactFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.firstnameInput.text = SpannableStringBuilder(args.contact.firstName)
        binding.lastnameInput.text = SpannableStringBuilder(args.contact.lastName)
        binding.contactNumberInput.text = SpannableStringBuilder(args.contact.contactNumber)

        binding.submitButton.setOnClickListener {
            binding.firstnameInput.selectAll()
            binding.lastnameInput.selectAll()
            binding.contactNumberInput.selectAll()
            val firstNameEdit = binding.firstnameInput.text.toString()
            val lastNameEdit = binding.lastnameInput.text.toString()
            val contactNumber = binding.contactNumberInput.text.toString()
            val updatedContact = Contacts(firstNameEdit,lastNameEdit,contactNumber,args.contact.id)

            editContactViewModel.makeApiUpdateRequest(updatedContact)

            navigateToContactList()
        }
        binding.cancelButton.setOnClickListener {
            navigateToContactList()
        }
        editContactViewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(context, UiConstant.UPDATE_CONTACT_ERROR, Toast.LENGTH_SHORT).show()
        }

    }
    private fun navigateToContactList() {
        val action = EditContactFragmentDirections.actionEditContactFragmentToContactListFragment()
        findNavController().navigate(action)
    }
}