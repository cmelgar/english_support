package com.example.englishsupport

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Word>) {
    val adapter = recyclerView.adapter as WordListAdapter
    adapter.submitList(data) {
        recyclerView.scrollToPosition(0)
    }

}