package com.samawade.expensetracker.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.repository.UserRepository
import com.samawade.expensetracker.data.responses.Statement
import com.samawade.expensetracker.data.responses.Statements
import com.samawade.expensetracker.data.responses.TransactionResponse
import com.samawade.expensetracker.data.responses.Users
import com.samawade.expensetracker.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<Users>> = MutableLiveData()
    val user: LiveData<Resource<Users>>
        get() = _user

    private val _statement: MutableLiveData<Resource<Statement>> = MutableLiveData()
    val statement: LiveData<Resource<Statement>>
        get() = _statement

    private val _allStatements: MutableLiveData<Resource<Statements>> = MutableLiveData()
    val allStatements: LiveData<Resource<Statements>>
        get() = _allStatements

    private val _transaction: MutableLiveData<Resource<TransactionResponse>> = MutableLiveData()
    val transaction: LiveData<Resource<TransactionResponse>>
        get() = _transaction


    fun getUser(userId: String) = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser(userId)
    }

    fun getStatement(userId: String) = viewModelScope.launch {
        _statement.value = Resource.Loading
        _statement.value = repository.getStatement(userId)
    }

    fun getAllStatements(userId: String) = viewModelScope.launch {
        _allStatements.value = Resource.Loading
        _allStatements.value = repository.getAllStatements(userId)
    }



    fun deleteTransaction(transactionId: String) = viewModelScope.launch {
        _transaction.value = Resource.Loading
        _transaction.value = repository.deleteTransaction(transactionId)
    }
}