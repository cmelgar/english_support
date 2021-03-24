package com.example.englishsupport.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.englishsupport.R
import com.example.englishsupport.Word
import com.example.englishsupport.api.getImageUrl
import com.example.englishsupport.databinding.FragmentDetailBinding
import com.example.englishsupport.databinding.FragmentMainBinding
import com.example.englishsupport.databinding.FragmentWordBinding
import com.example.englishsupport.ui.DashboardViewModel
import com.example.englishsupport.ui.MerriamWordsStatus
import com.squareup.picasso.Picasso

class WordFragment : Fragment() {

    private val viewModel: DashboardViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, DashboardViewModel.Factory(activity.application)).get(
            DashboardViewModel::class.java)
    }

    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val wordDetails = WordFragmentArgs.fromBundle(requireArguments()).selectedWord

        viewModel.getImageFromWord(wordDetails.word)

        binding.word = wordDetails

        viewModel.wordImageUrl.observe(viewLifecycleOwner, {
            it?.apply {
                Picasso.get()
                        .load(it).into(binding.wordImage)
            }
        })


//        viewModel.setLoadingStatusDone()
        binding.statusImage.visibility = GONE
        binding.backButton.setOnClickListener {
            findNavController().navigate(WordFragmentDirections.actionWordFragmentToMainFragment())
        }


        return binding.root
    }
}