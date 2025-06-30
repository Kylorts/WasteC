package com.example.wastec.presentation.views.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.wastec.databinding.FragmentHistoryBinding
import com.example.wastec.presentation.viewmodel.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()
    private val historyAdapter = HistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        collectUiState()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewHistory.adapter = historyAdapter
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val isListEmpty = state.historyList.isEmpty()

                    binding.textEmptyHistory.isVisible = isListEmpty
                    binding.recyclerViewHistory.isVisible = !isListEmpty
                    historyAdapter.submitList(state.historyList)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}