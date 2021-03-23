package com.example.englishsupport.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.englishsupport.R
import com.example.englishsupport.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_overview, container, false)

        binding = FragmentOverviewBinding.inflate(inflater)
        binding.startButton.setOnClickListener {
            findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToMainFragment())
        }

        return binding.root
    }
}