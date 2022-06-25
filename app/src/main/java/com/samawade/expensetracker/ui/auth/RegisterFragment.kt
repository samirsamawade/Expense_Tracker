package com.samawade.expensetracker.ui.auth

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.samawade.expensetracker.R
import com.samawade.expensetracker.data.network.AuthApi
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.network.UserApi
import com.samawade.expensetracker.data.repository.AuthRepository
import com.samawade.expensetracker.data.repository.UserRepo
import com.samawade.expensetracker.data.repository.UserRepository
import com.samawade.expensetracker.data.responses.Users
import com.samawade.expensetracker.databinding.FragmentRegisterBinding
import com.samawade.expensetracker.ui.base.BaseFragment
import com.samawade.expensetracker.ui.handleApiError
import com.samawade.expensetracker.ui.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class RegisterFragment : BaseFragment<UserViewMo, FragmentRegisterBinding, UserRepo>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            tvLogin.setOnClickListener {
                findNavController().navigate(
                    R.id.action_registerFragment_to_loginFragment
                )
            }
            registerButton.setOnClickListener {
                val name = rName.text.toString()
                val phone = rPhone.text.toString()
                val username = rUsername.text.toString()
                val password = rPassword.text.toString()

                when{
                    TextUtils.isEmpty(name) -> {
                        rName.error = "Please enter your name."
                    }
                    TextUtils.isEmpty(phone) || phone.length < 9 -> {
                        rPhone.error = "Please enter a valid mobile number."
                    }
                    TextUtils.isEmpty(username) -> {
                        rUsername.error = "Please enter your username."
                    }
                    TextUtils.isEmpty(password) || password.length < 6 -> {
                        rPassword.error = "Your password must have at least 6 letters."
                    }
                    else -> {
                        val register = Users(name,password, phone,username)
                        viewModel.registerUser(register)

                        viewModel.userResponse.observe(viewLifecycleOwner, Observer {
                            when(it){
                                is Resource.Success -> {
                                    findNavController().navigate(
                                        R.id.action_registerFragment_to_loginFragment
                                    )
                                }
                                is Resource.Failure -> handleApiError(it)
                            }
                        })
                    }
                }
            }
        }
    }

    override fun getViewModel() = UserViewMo::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = UserRepo(remoteDataSource.buildApi(AuthApi::class.java))

}