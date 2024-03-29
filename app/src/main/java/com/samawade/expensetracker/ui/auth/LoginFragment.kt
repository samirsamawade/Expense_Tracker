package com.samawade.expensetracker.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.R
import com.samawade.expensetracker.databinding.FragmentLoginBinding
import com.samawade.expensetracker.data.network.AuthApi
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.repository.AuthRepository
import com.samawade.expensetracker.ui.*
import com.samawade.expensetracker.ui.base.BaseFragment
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)
        binding.buttonLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.token!!, it.value.id!!)
                        requireActivity().startNewActivity(DashboardActivity::class.java)
                    }

                }
                is Resource.Failure -> handleApiError(it){ login() }
            }
        })

        with(binding) {

            editTextPass.addTextChangedListener {
                val username = editTextUsername.text.toString().trim()
                buttonLogin.enable(username.isNotEmpty() && it.toString().trim().isNotEmpty())
            }

            tvRegister.setOnClickListener{
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment
                )
            }
            buttonLogin.setOnClickListener {
                login()
            }
        }
    }

    private fun login() {
        val username = binding.editTextUsername.text.toString().trim()
        val password = binding.editTextPass.text.toString().trim()

        val login = Login(username, password)
        viewModel.login(login)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPrerences)


}