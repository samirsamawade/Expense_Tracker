package com.samawade.expensetracker.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.repository.AuthRepository
import com.samawade.expensetracker.data.repository.UserRepo
import com.samawade.expensetracker.data.responses.LoginResponse
import com.samawade.expensetracker.data.responses.UserResponse
import com.samawade.expensetracker.data.responses.Users
import com.samawade.expensetracker.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class UserViewMo(
    private val repository: UserRepo
): BaseViewModel(repository) {

    private val _userResponse: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val userResponse: LiveData<Resource<UserResponse>>
        get() = _userResponse

    fun registerUser(user: Users) = viewModelScope.launch {
        _userResponse.value = Resource.Loading
        _userResponse.value = repository.registerUser(user)
    }

}