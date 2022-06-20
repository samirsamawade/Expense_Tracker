package com.samawade.expensetracker.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.samawade.expensetracker.R
import com.samawade.expensetracker.adapter.Adapter
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.network.UserApi
import com.samawade.expensetracker.data.repository.UserRepository
import com.samawade.expensetracker.data.responses.Statement
import com.samawade.expensetracker.data.responses.Statements
import com.samawade.expensetracker.databinding.FragmentDashboardBinding
import com.samawade.expensetracker.ui.base.BaseFragment
import com.samawade.expensetracker.ui.enable
import com.samawade.expensetracker.ui.handleApiError
import com.samawade.expensetracker.ui.user.UserViewModel
import com.samawade.expensetracker.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class DashboardFragment : BaseFragment<UserViewModel, FragmentDashboardBinding, UserRepository>() {

    private val myAdapter by lazy { Adapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        myAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("transaction", it)
            }
            findNavController().navigate(
                R.id.action_dashboardFragment_to_transactionDetailFragment,
                bundle
            )
        }

        val id = runBlocking { userPrerences.authId.first() }
        viewModel.getStatement(id!!)
        viewModel.getAllStatements(id!!)

        viewModel.statement.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    updateUI(it.value)
                }

                is Resource.Failure -> handleApiError(it)
            }
        })

        viewModel.allStatements.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {

                    Log.d("Xaami", it.value.toString())
                    updateAdapter(it.value)
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    private fun updateAdapter(statements: Statements) {

//        statements?.let {
        if (statements.info.isEmpty()) {
            binding.dashboardGroup.visible(false)
            binding.emptyStateLayout.visible(true)
        }
        myAdapter.differ.submitList(statements.info.sortedBy { it._id }.reversed().toList())
//        }
    }

    private fun updateUI(statement: Statement) {
        with(binding) {
            textBalance.text = "$" + statement.balance
            textIncome.text = "$" + statement.userincome
            textExpense.text = "$" + statement.userExpense
        }
    }

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)


    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPrerences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)
        return UserRepository(api)
    }

    fun setRecyclerView() {
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }
}