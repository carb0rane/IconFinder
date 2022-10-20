package com.kode.iconfinder.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kode.iconfinder.IconFinder.RasterSize
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.databinding.DownloadrecyclerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DownloadRecycler(val rastorList: List<RasterSize>, val viewModel: IconFinderViewModel) :
    RecyclerView.Adapter<DownloadRecycler.DownloadViewHolder>() {
    val _rastorList = rastorList
    val _viewModel = viewModel

    inner class DownloadViewHolder(val binding: DownloadrecyclerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
        return DownloadViewHolder(
            DownloadrecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
        holder.binding.textView.text =
            _rastorList.get(position).size_height.toString() + " * " + _rastorList.get(position).size_width.toString()

        holder.binding.btnDownloadIcon.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val job = _viewModel.download(_rastorList.get(position).formats.get(0).download_url)
                job.join()

            }

        }

    }

    override fun getItemCount(): Int {
        return _rastorList.size
    }
}