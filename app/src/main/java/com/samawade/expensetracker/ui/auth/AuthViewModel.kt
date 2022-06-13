package com.samawade.expensetracker.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.repository.AuthRepository
import com.samawade.expensetracker.data.responses.LoginResponse
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
): ViewModel() {
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(login: Login ) = viewModelScope.launch {
        _loginResponse.value = repository.login(login)
    }

    fun saveAuthToken(token: String, id: String) = viewModelScope.launch{
        repository.saveAuthToken(token, id)
    }
}