package com.example.englishsupport.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishsupport.R
import com.example.englishsupport.Word
import com.example.englishsupport.api.getImageUrl
import com.example.englishsupport.databinding.FragmentDetailBinding
import com.example.englishsupport.databinding.FragmentWordBinding
import com.example.englishsupport.ui.DashboardViewModel
import com.squareup.picasso.Picasso

class WordFragment : Fragment() {

    private val viewModel: DashboardViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, DashboardViewModel.Factory(activity.application)).get(
            DashboardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val word = WordFragmentArgs.fromBundle(requireArguments()).selectedWord

        binding.word = word


        viewModel.getImageFromWord(word.word)

        viewModel.wordImageUrl.observe(viewLifecycleOwner, Observer {
            it?.apply {
                Picasso.get()
                    .load(it).into(binding.wordImage)
            }

        })


        return binding.root
    }

}