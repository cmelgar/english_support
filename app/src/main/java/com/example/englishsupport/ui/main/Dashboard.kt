package com.example.englishsupport.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
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
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val SPEECH_REQUEST_CODE = 100

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
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToWordFragment(it))
        })

        binding.wordRecycler.adapter = adapter

        viewModel.words.observe(viewLifecycleOwner, Observer {
            it?.apply {
                adapter.submitList(it)
            }
        })

        setHasOptionsMenu(true)

        binding.searchButton.setOnClickListener {
            val wordQuery = binding.wordText.text
            viewModel.searchWord(wordQuery.toString())
        }

        binding.speakerButton.setOnClickListener {
            displaySpeechRecognizer()
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

    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                .let { results ->
                results?.get(0)
            }
            binding.wordText.setText(spokenText)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

enum class OptionSelected { RECENT, LAST_30, ALL }