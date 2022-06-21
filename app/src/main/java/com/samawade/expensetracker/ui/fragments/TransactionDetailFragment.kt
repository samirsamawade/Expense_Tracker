package com.samawade.expensetracker.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.samawade.expensetracker.R
import com.samawade.expensetracker.data.network.Resource
import com.samawade.expensetracker.data.network.UserApi
import com.samawade.expensetracker.data.repository.UserRepository
import com.samawade.expensetracker.databinding.FragmentTransactionDetailBinding
import com.samawade.expensetracker.ui.base.BaseFragment
import com.samawade.expensetracker.ui.handleApiError
import com.samawade.expensetracker.ui.user.UserViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class TransactionDetailFragment :
    BaseFragment<UserViewModel, FragmentTransactionDetailBinding, UserRepository>() {

    val args:TransactionDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transaction = args.transaction

        val _id = transaction._id

        with(binding){
            myToolBar.inflateMenu(R.menu.menu)
            myToolBar.setTitle("Details")

            myToolBar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.deleteTransaction -> {
                        val dialog = AlertDialog.Builder(requireContext())
                        dialog.apply {
                            setTitle("Delete Transaction")
                            setMessage("Are you sure you want to delete this transaction?")
                            setNegativeButton("No"){negative, _ ->
                                negative.dismiss()
                            }
                            setPositiveButton("Yes"){positive, _ ->
                                Log.d("item", "Item clicked")
                                viewModel.deleteTransaction(_id)
                                viewModel.transaction.observe(viewLifecycleOwner, Observer {
                                    when(it){
                                        is Resource.Success -> {
                                            findNavController().navigate(
                                            R.id.action_transactionDetailFragment_to_dashboardFragment
                                        )
                                        }
                                        is Resource.Failure -> handleApiError(it)
                                    }
                                })
                                positive.dismiss()
                            }
                            dialog.create()
                            dialog.show()
                        }


                        true
                    }
                    else -> {
                        false
                    }

                }
            }

            detailDesc.text = transaction.description
            detailAmount.text = transaction.amount.toString()
            detailType.text = transaction.type
            detailDate.text = transaction.date
            detailCreatedAt.text = transaction.createdAt

            editTransactionDetail.setOnClickListener {
                val detailDesc = detailDesc.text.toString()
                val detailAmount =  detailAmount.text.toString()
                val detailType = detailType.text.toString()
                val detailDate = detailDate.text.substring(0,10).toString()

                val bundle = Bundle().apply {
                    putString("detail_desc", detailDesc)
                    putString("detail_amount", detailAmount)
                    putString("detail_type", detailType)
                    putString("detail_date", detailDate)
                    putString("detail_id", _id)
                }
                findNavController().navigate(
                    R.id.action_transactionDetailFragment_to_editTransactionFragment,
                    bundle
                )
            }
        }

    }

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTransactionDetailBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPrerences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)
        return UserRepository(api)
    }

}