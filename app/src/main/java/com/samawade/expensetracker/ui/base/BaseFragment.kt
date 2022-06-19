package com.samawade.expensetracker.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.samawade.expensetracker.data.UserPreferences
import com.samawade.expensetracker.data.network.RemoteDataSource
import com.samawade.expensetracker.data.repository.BaseRepository
import com.samawade.expensetracker.ui.auth.AuthActivity
import com.samawade.expensetracker.ui.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM: BaseViewModel, B: ViewBinding, R: BaseRepository>: Fragment() {

    protected lateinit var userPrerences: UserPreferences
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPrerences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory= ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        lifecycleScope.launch { userPrerences.authToken.first() }

        return binding.root
    }

    fun logout() = lifecycleScope.launch{
        userPrerences.clear()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getFragmentRepository(): R
}