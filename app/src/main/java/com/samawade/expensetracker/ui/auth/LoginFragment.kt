package com.samawade.expensetracker.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.R
import com.samawade.expensetracker.databinding.FragmentLoginBinding
import com.samawade.expensetracker.network.AuthApi
import com.samawade.expensetracker.network.Resource
import com.samawade.expensetracker.repository.AuthRepository
import com.samawade.expensetracker.ui.auth.base.BaseFragment

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.apply {

            buttonLogin.setOnClickListener {
                val username = editTextUsername.text.toString()
                val password = editTextPass.text.toString()

                val login = Login(username, password)

                //@todo add input validations
                viewModel.login(login)
            }
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))


}