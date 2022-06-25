package com.samawade.expensetracker.ui.user

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.samawade.expensetracker.R
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.network.UserApi
import com.samawade.expensetracker.data.repository.UserRepository
import com.samawade.expensetracker.data.responses.User
import com.samawade.expensetracker.data.responses.Users
import com.samawade.expensetracker.databinding.FragmentProfileBinding
import com.samawade.expensetracker.ui.base.BaseFragment
import com.samawade.expensetracker.ui.handleApiError
import kotlinx.android.synthetic.main.dialog_update_user.view.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<UserViewModel, FragmentProfileBinding, UserRepository>() {
    var name: String? = ""
    var username: String? = ""
    var phone: String? = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = runBlocking { userPrerences.authId.first() }

        viewModel.getUser(id!!)

        viewModel.user.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    updateUI(it.value)
                }
                is Resource.Failure -> handleApiError(it)
            }
        })

        binding.logoutAccount.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())

            dialog.apply {
                setTitle("Sign out")
                setMessage("Are you sure you want to sign out?")
                setNegativeButton("No"){negative, _ ->
                    negative.dismiss()
                }
                setPositiveButton("Yes"){positive, _ ->
                    logout()
                    positive.dismiss()
                }
                dialog.create()
                dialog.show()
            }
        }

        binding.deleteAccount.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.apply {
                setTitle("Delete Account")
                setMessage("Are you sure you want to delete this Account?")
                setNegativeButton("No"){negative, _ ->
                    negative.dismiss()
                }
                setPositiveButton("Yes"){positive, _ ->
                    Log.d("item", "Item clicked")
                    viewModel.deleteAccount(id!!)
                    viewModel.user.observe(viewLifecycleOwner, Observer {
                        when(it){
                            is Resource.Success -> {
                                logout()
                            }
                            is Resource.Failure -> handleApiError(it)
                        }
                    })
                    positive.dismiss()
                }
                dialog.create()
                dialog.show()
            }
        }

        binding.updateUser.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            // Get the layout inflater
            val inflater = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_update_user, null)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout

            builder.setView(inflater)
                // Add action buttons
                .setPositiveButton("Update",
                    DialogInterface.OnClickListener { dialog, _ ->
                        // sign in the user ...
                        name = inflater.dialog_edit_name.text.toString()
                        phone = inflater.dialog_edit_phone.text.toString()
                        username = inflater.dialog_edit_username.text.toString()

                        val user = User(name.toString(), phone.toString(), username.toString())

                        viewModel.updateUser(id.toString(), user)
                        viewModel.userResponse.observe(this, androidx.lifecycle.Observer {

                            when(it){
                                is Resource.Success -> {
                                    viewModel.getUser(id!!)
                                }
                                is Resource.Failure -> handleApiError(it)
                            }

                        })
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, _ ->
                    })
            inflater.dialog_edit_name.setText(name)
            inflater.dialog_edit_phone.setText(phone)
            inflater.dialog_edit_username.setText(username)

            builder.create()
            builder.show()
        }
    }

    private fun updateUI(user: Users) {
        with(binding){

            name = user.name!!
            username = user.username!!
            phone = user.phone!!

            tvName.text = user.name
            tvUsername.text = user.username
        }
    }

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPrerences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)
        return UserRepository(api)
    }

}