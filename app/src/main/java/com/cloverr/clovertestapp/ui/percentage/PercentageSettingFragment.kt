package com.cloverr.clovertestapp.ui.percentage

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cloverr.clovertestapp.App
import com.cloverr.clovertestapp.R
import com.cloverr.clovertestapp.databinding.FragmentSettingPercentageBinding
import com.cloverr.clovertestapp.ui.MainViewModel
import com.cloverr.clovertestapp.ui.transactions.TransactionsHistoryFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class PercentageSettingFragment : Fragment(), View.OnClickListener {

    private var binding: FragmentSettingPercentageBinding? = null

    @Inject
    lateinit var factory: MainViewModel.Factory
    private val viewModel: MainViewModel by activityViewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingPercentageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.appComponent.inject(this)

        binding?.setPercentageButton?.setOnClickListener(this)
        binding?.seeTransactionsButton?.setOnClickListener(this)
        lifecycleScope.launch {
            viewModel.mainScreenStateFlow.collect { state ->
                binding?.percentageInputLayout?.error =
                    state.inputLayoutError?.let { getString(it) }
                binding?.setPercentageButton?.isEnabled = state.isEditingEnabled
                binding?.percentageEditText?.apply {
                    isEnabled = state.isEditingEnabled
                    inputType =
                        if (state.isEditingEnabled) InputType.TYPE_NUMBER_FLAG_DECIMAL else InputType.TYPE_NULL
                }
            }
        }
    }

    override fun onClick(view: View) {
        when (view) {
            binding?.setPercentageButton -> viewModel.setPercentage(binding?.percentageEditText?.text.toString())
            binding?.seeTransactionsButton -> parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container_view, TransactionsHistoryFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}