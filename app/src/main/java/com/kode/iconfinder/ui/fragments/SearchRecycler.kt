package com.kode.iconfinder.ui.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.R
import com.kode.iconfinder.databinding.CategorylistBinding
import com.kode.iconfinder.databinding.IconslistBinding

private const val TAG = "SearchRecycler"

class SearchRecycler(iconFinderViewModel: IconFinderViewModel) :
    RecyclerView.Adapter<SearchRecycler.SearchViewHolder>() {

    val _iconFinderViewModel = iconFinderViewModel

    inner class SearchViewHolder(val binding: IconslistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            IconslistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val pos = position
        try {
            holder.binding.tvIconName.text =
                _iconFinderViewModel.searchResult.icons.get(position).categories.get(0).name
            if (!_iconFinderViewModel.searchResult.icons.get(position).is_premium)
                holder.binding.imgViewPremiumStatus.setImageResource(R.drawable.ic_free)
            Glide.with(_iconFinderViewModel.context)
                .load(
                    _iconFinderViewModel.searchResult.icons.get(position).raster_sizes.get(2).formats.get(
                        0
                    ).preview_url
                )
                .into(holder.binding.imgViewIcon)
            holder.binding.tvIconTags.text =
                _iconFinderViewModel.searchResult.icons.get(position).tags.toString()
            holder.binding.iconListCardView.setOnClickListener(
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val activity = v!!.context as AppCompatActivity
                        val dialog =
                            DownloadDialogFragment(_iconFinderViewModel.searchResult.icons.get(pos).raster_sizes)
                        dialog.show(activity.supportFragmentManager, "Download Dialog")
                    }
                }
            )
        } catch (e: Exception) {
            Log.d(TAG, "onBindViewHolder: raised $e")
        }
    }

    override fun getItemCount(): Int {
        try {
            return _iconFinderViewModel.searchResult.total_count
        } catch (e: Exception) {
            Log.d(TAG, "getItemCount: $e")
        }
        return 0
    }
}