package com.samawade.expensetracker.ui.base

import androidx.lifecycle.ViewModel
import com.samawade.expensetracker.data.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
): ViewModel() {
}