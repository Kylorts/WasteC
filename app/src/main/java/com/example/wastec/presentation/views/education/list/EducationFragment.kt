package com.example.wastec.presentation.views.education.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wastec.databinding.FragmentEducationBinding
import com.example.wastec.presentation.viewmodel.education.list.EducationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EducationFragment : Fragment() {
    private var _binding: FragmentEducationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EducationViewModel by viewModels()
    private lateinit var educationAdapter: EducationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEducationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        collectUiState()
    }

    private fun setupRecyclerView() {
        educationAdapter = EducationAdapter { category ->
            val action = EducationFragmentDirections.actionEducationFragmentToEducationDetailFragment(category.id)
            findNavController().navigate(action)
        }
        binding.recyclerViewEducation.adapter = educationAdapter
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d("EducationFragment", "Current State: $state")
                    binding.progressBar.isVisible = state.isLoading

                    if (state.error != null) {
                        binding.textErrorMessage.isVisible = true
                        binding.textErrorMessage.text = state.error
                        binding.recyclerViewEducation.isVisible = false
                    } else {
                        binding.textErrorMessage.isVisible = false

                        val isListEmpty = state.categories.isEmpty()
                        binding.recyclerViewEducation.isVisible = !isListEmpty
                        educationAdapter.submitList(state.categories)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}