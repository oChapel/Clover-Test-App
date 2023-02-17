package com.cloverr.clovertestapp.ui.main

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cloverr.clovertestapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setPercentageButton.setOnClickListener(this)
        lifecycleScope.launch {
            viewModel.mainScreenStateFlow.collect { state ->
                binding.percentageInputLayout.error = state.inputLayoutError?.let { getString(it) }
                binding.setPercentageButton.isEnabled = state.isEditingEnabled
                binding.percentageEditText.apply {
                    isEnabled = state.isEditingEnabled
                    inputType =
                        if (state.isEditingEnabled) InputType.TYPE_NUMBER_FLAG_DECIMAL else InputType.TYPE_NULL
                }
            }
        }
    }

    override fun onClick(view: View) {
        when (view) {
            binding.setPercentageButton -> viewModel.setPercentage(binding.percentageEditText.text.toString())
        }
    }
}