package com.example.englishsupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.englishsupport.databinding.FragmentWordBinding

class WordListAdapter(val clickListener: WordClickListener):
    ListAdapter<Word, WordListAdapter.WordListViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }

    class WordListViewHolder(private var binding: FragmentWordBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: WordClickListener, word: Word) {
            binding.word = word
            binding.wordCallback = listener

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): WordListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentWordBinding.inflate(layoutInflater, parent, false)
                return WordListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {
        return WordListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

}

class WordClickListener (val clickListener: (word: Word) -> Unit) {
    fun onClick(word: Word) = clickListener(word)
}