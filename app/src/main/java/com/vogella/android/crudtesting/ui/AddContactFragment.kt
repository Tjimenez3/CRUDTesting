package com.vogella.android.crudtesting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.vogella.android.crudtesting.viewmodel.AddContactViewModel
import com.vogella.android.crudtesting.App
import com.vogella.android.crudtesting.constants.UiConstant.ADD_CONTACT_ERROR
import com.vogella.android.crudtesting.databinding.FragmentAddContactBinding

class AddContactFragment : DialogFragment() {

    private var _binding: FragmentAddContactBinding? =null
    private val binding get() = _binding!!
    private var addNameViewModel = AddContactViewModel(App.getApi())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            binding.firstnameInput.selectAll()
            binding.lastnameInput.selectAll()
            binding.contactNumberInput.selectAll()
            val firstName = binding.firstnameInput.text.toString()
            val lastName = binding.lastnameInput.text.toString()
            val contactNumber = binding.contactNumberInput.text.toString()

            addNameViewModel.makeApiRequest(firstName,lastName,contactNumber)

        }
        addNameViewModel.responsePostNewContact.observe(viewLifecycleOwner) {
            val action = AddContactFragmentDirections.actionAddNameFragmentToContactListFragment()
            findNavController().navigate(action)
        }
        addNameViewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(context,ADD_CONTACT_ERROR,Toast.LENGTH_SHORT).show()
        }
    }

}