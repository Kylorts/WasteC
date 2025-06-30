package com.example.wastec.presentation.views.education.detail

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
import com.bumptech.glide.Glide
import com.example.wastec.databinding.FragmentEducationDetailBinding
import com.example.wastec.presentation.viewmodel.education.detail.EducationDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EducationDetailFragment : Fragment() {

    private var _binding: FragmentEducationDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EducationDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEducationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectUiState()
    }


    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progressBarDetail.isVisible = state.isLoading
                    binding.contentScrollView.isVisible = !state.isLoading

                    state.category?.let { category ->
                        binding.textDetailTitle.text = category.name

                        binding.textDetailDescription.text = category.description
                        binding.textDetailExamples.text = category.examples.joinToString(separator = "\n") { "• $it" }
                        binding.textDetailRecyclingInfo.text = category.recyclingInfo

                        Glide.with(requireContext())
                            .load(category.iconUrl)
                            .into(binding.imageDetailIcon)
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