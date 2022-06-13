package com.samawade.expensetracker.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.databinding.FragmentLoginBinding
import com.samawade.expensetracker.data.network.AuthApi
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.repository.AuthRepository
import com.samawade.expensetracker.ui.DashboardActivity
import com.samawade.expensetracker.ui.base.BaseFragment
import com.samawade.expensetracker.ui.enable
import com.samawade.expensetracker.ui.startNewActivity
import com.samawade.expensetracker.ui.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)
        binding.buttonLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(false)
            when(it){
                is Resource.Success -> {

                        viewModel.saveAuthToken(it.value.token, it.value.id)
                        requireActivity().startNewActivity(DashboardActivity::class.java)

                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.apply {

            editTextPass.addTextChangedListener {
                val username = editTextUsername.text.toString()
                buttonLogin.enable(username.isNotEmpty() && it.toString().isNotEmpty())
            }

            buttonLogin.setOnClickListener {
                val username = editTextUsername.text.toString()
                val password = editTextPass.text.toString()

                val login = Login(username, password)
                progressBar.visible(true)
                viewModel.login(login)
            }
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPrerences)


}