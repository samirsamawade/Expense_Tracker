package com.samawade.expensetracker.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samawade.expensetracker.data.repository.AuthRepository
import com.samawade.expensetracker.data.repository.BaseRepository
import com.samawade.expensetracker.data.repository.UserRepo
import com.samawade.expensetracker.data.repository.UserRepository
import com.samawade.expensetracker.ui.auth.AuthViewModel
import com.samawade.expensetracker.ui.auth.UserViewMo
import com.samawade.expensetracker.ui.user.UserViewModel

class ViewModelFactory(
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(UserViewMo::class.java) -> UserViewMo(repository as UserRepo) as T
            else -> throw IllegalArgumentException("viewModelClass Not Found")
        }
    }
}