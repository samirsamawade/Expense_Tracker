package com.samawade.expensetracker.ui.auth.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samawade.expensetracker.data.repository.AuthRepository
import com.samawade.expensetracker.data.repository.BaseRepository
import com.samawade.expensetracker.ui.auth.AuthViewModel

class ViewModelFactory(
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            else -> throw IllegalArgumentException("viewModelClass Not Found")
        }
    }
}