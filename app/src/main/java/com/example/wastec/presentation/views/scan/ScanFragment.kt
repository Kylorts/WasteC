package com.example.wastec.presentation.views.scan

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.wastec.R
import com.example.wastec.databinding.FragmentScanBinding
import com.example.wastec.presentation.viewmodel.scan.ScanViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScanViewModel by viewModels()

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onImageSelected(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        collectUiState()
    }

    private fun setupClickListeners() {
        binding.buttonOpenGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.buttonClassify.setOnClickListener {
            viewModel.uiState.value.selectedImageUri?.let { uri ->
                val bitmap = uriToBitmap(uri)
                viewModel.classifyImage(bitmap)
            }
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progressBar.isVisible = state.isLoading

                    if (state.selectedImageUri != null) {
                        binding.imagePreview.setImageURI(state.selectedImageUri)
                        binding.buttonClassify.isEnabled = !state.isLoading
                    } else {
                        binding.imagePreview.setImageResource(R.drawable.outline_image_24)
                        binding.buttonClassify.isEnabled = false
                    }

                    val resultText = when {
                        state.result != null -> "${state.result.label} (${(state.result.confidence * 100).toInt()}%)"
                        state.error != null -> "Error: Gagal menganalisis"
                        else -> ""
                    }
                    binding.textScanResult.text = resultText
                }
            }
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        }.copy(Bitmap.Config.ARGB_8888, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}