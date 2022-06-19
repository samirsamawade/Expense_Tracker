package com.samawade.expensetracker.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.samawade.expensetracker.R
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.network.UserApi
import com.samawade.expensetracker.data.repository.UserRepository
import com.samawade.expensetracker.data.responses.Users
import com.samawade.expensetracker.databinding.FragmentProfileBinding
import com.samawade.expensetracker.ui.base.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<UserViewModel, FragmentProfileBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = runBlocking { userPrerences.authId.first() }

        viewModel.getUser(id!!)

        viewModel.user.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    updateUI(it.value)
                }
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
    }

    private fun updateUI(user: Users) {
        with(binding){
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