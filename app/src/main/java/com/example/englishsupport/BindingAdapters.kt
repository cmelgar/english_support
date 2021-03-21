package com.example.englishsupport

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.englishsupport.ui.MerriamWordsStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Word>?) {
    val adapter = recyclerView.adapter as WordListAdapter
    adapter.submitList(data) {
        recyclerView.scrollToPosition(0)
    }
}

@BindingAdapter("statusData")
fun bindStatusData(progressBar: ProgressBar, status: MerriamWordsStatus?) {
    when (status) {
        MerriamWordsStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        MerriamWordsStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
        MerriamWordsStatus.ERROR -> {
            progressBar.visibility = View.GONE
        }
    }
}

@BindingAdapter("android:fadeVisible")
fun setFadeVisible(view: View, visible: Boolean? = true) {
    if (view.tag == null) {
        view.tag = true
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    } else {
        view.animate().cancel()
        if (visible == true) {
            if (view.visibility == View.GONE)
                view.fadeIn()
        } else {
            if (view.visibility == View.VISIBLE)
                view.fadeOut()
        }
    }
}