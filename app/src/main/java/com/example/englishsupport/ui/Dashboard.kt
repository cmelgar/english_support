package com.example.englishsupport.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.englishsupport.R
import com.example.englishsupport.WordClickListener
import com.example.englishsupport.WordListAdapter
import com.example.englishsupport.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private val viewModel: DashboardViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, DashboardViewModel.Factory(activity.application)).get(DashboardViewModel::class.java)
    }

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = WordListAdapter(WordClickListener {
//            findNavController().navigate()
            Toast.makeText(context, "Hey", Toast.LENGTH_SHORT).show()
        })

        binding.wordRecycler.adapter = adapter

        viewModel.words.observe(viewLifecycleOwner, Observer {
            it?.apply {
                adapter.submitList(it)
            }
        })

        setHasOptionsMenu(true)

        val wordQuery = binding.wordText.text

        binding.searchButton.setOnClickListener {
            viewModel.searchWord(wordQuery.toString())
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_all_words -> {
                viewModel.showOptionSelected(OptionSelected.ALL)
            }
            R.id.show_last30_words -> {
                viewModel.showOptionSelected(OptionSelected.LAST_30)
            }
            R.id.show_recent_words -> {
                viewModel.showOptionSelected(OptionSelected.RECENT)
            }
        }
        return true
    }
}

enum class OptionSelected { RECENT, LAST_30, ALL }