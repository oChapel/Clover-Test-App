package com.cloverr.clovertestapp.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloverr.clovertestapp.databinding.FragmentTransactionsHistoryBinding
import com.cloverr.clovertestapp.ui.transactions.adapter.TransactionsRecyclerAdapter
import kotlinx.coroutines.launch

class TransactionsHistoryFragment : Fragment() {

    private var binding: FragmentTransactionsHistoryBinding? = null
    private val viewModel: TransactionsHistoryViewModel by viewModels()

    private val adapter = TransactionsRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionsHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.transactionsRecyclerView?.apply {
            adapter = this@TransactionsHistoryFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            viewModel.getModifiedItems().collect {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}