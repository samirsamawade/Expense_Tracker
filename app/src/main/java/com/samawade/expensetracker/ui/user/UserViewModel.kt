package com.samawade.expensetracker.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.repository.UserRepository
import com.samawade.expensetracker.data.responses.Users
import com.samawade.expensetracker.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
): BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<Users>> = MutableLiveData()
    val user: LiveData<Resource<Users>>
        get() = _user

    fun getUser(userId: String) = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser(userId)
    }
}