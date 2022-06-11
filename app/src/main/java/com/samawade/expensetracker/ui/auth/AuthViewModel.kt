package com.samawade.expensetracker.ui.auth

import androidx.lifecycle.ViewModel
import com.samawade.expensetracker.repository.AuthRepository

class AuthViewModel(
    private val repository: AuthRepository
): ViewModel() {
}