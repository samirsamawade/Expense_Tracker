package com.samawade.expensetracker.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.samawade.expensetracker.Model.Transaction
import com.samawade.expensetracker.R
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.network.UserApi
import com.samawade.expensetracker.data.repository.UserRepository
import com.samawade.expensetracker.databinding.FragmentEditTransactionBinding
import com.samawade.expensetracker.ui.base.BaseFragment
import com.samawade.expensetracker.ui.handleApiError
import com.samawade.expensetracker.ui.transactionType
import com.samawade.expensetracker.ui.transformIntoDatePicker
import com.samawade.expensetracker.ui.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_edit_transaction.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*

class EditTransactionFragment : BaseFragment<UserViewModel, FragmentEditTransactionBinding, UserRepository>() {
    val args: EditTransactionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = runBlocking { userPrerences.authId.first() }

        val transactionTypeAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.item_autocomplete_layout,
                transactionType
            )

        with(binding){
            etTransactionType.setAdapter(transactionTypeAdapter)

            etWhen.transformIntoDatePicker(
                requireContext(),
                "yyyy/MM/dd",
                Date()
            )

            val _id = args.detailId
            btnUpdateTransaction.setOnClickListener {
                val desc = etDesc.text.toString()
                val amount = etAmount.text.toString()
                val type = etTransactionType.text.toString()
                val date = etWhen.text.toString()

                val transaction = Transaction(amount.toString(),desc, type, id.toString(), date)

                when{
                    TextUtils.isEmpty(desc) -> {
                        etDesc.error = "Please enter description."
                    }
                    TextUtils.isEmpty(amount.toString()) -> {
                        etAmount.error = "Please enter description."
                    }
                    TextUtils.isEmpty(type) -> {
                        etTransactionType.error = "Please select transaction type."
                    }
                    TextUtils.isEmpty(date) -> {
                        etWhen.error = "Please select transaction date."
                    }
                    else -> {
                        viewModel.updateTransaction(_id, transaction)

                        viewModel.transaction.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                            when(it){
                                is Resource.Success -> {
                                    findNavController().navigate(
                                        R.id.action_editTransactionFragment_to_dashboardFragment
                                    )
                                }
                                is Resource.Failure -> handleApiError(it)
                            }
                        })
                    }
                }
            }

            etDesc.setText(args.detailDesc)
            etAmount.setText(args.detailAmount)
            etTransactionType.setText(args.detailType, false)
            etWhen.setText(args.detailDate)
        }


    }

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditTransactionBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPrerences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)
        return UserRepository(api)
    }

}