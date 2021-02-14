package com.example.airlift.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.airlift.databinding.ImageDetailsFragmentBinding
import com.example.airlift.di.Injection

class ImageDetailsFragment : Fragment() {

    private lateinit var binding: ImageDetailsFragmentBinding
    private val args: ImageDetailsFragmentArgs by navArgs()

    private val viewModel: ImageDetailsViewModel by lazy {
        ViewModelProvider(this, Injection.provideDetailsViewModelFactory(args.imageUrl))
            .get(ImageDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ImageDetailsFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


}