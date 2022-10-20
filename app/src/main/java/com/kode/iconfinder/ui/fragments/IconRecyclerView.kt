package com.kode.iconfinder.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.R
import com.kode.iconfinder.databinding.CategorylistBinding
import com.kode.iconfinder.databinding.IconslistBinding

private const val TAG = "IconRecyclerView"

class IconRecyclerView(viewModel: IconFinderViewModel) :
    RecyclerView.Adapter<IconRecyclerView.IconRecyclerViewHolder>() {
    val list = viewModel

    inner class IconRecyclerViewHolder(val binding: IconslistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconRecyclerViewHolder {
        return IconRecyclerViewHolder(
            IconslistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IconRecyclerViewHolder, position: Int) {


        holder.binding.tvIconName.text = list.icon.icons.get(position).categories.get(0).name
        if (!list.icon.icons.get(position).is_premium) {
            holder.binding.imgViewPremiumStatus.setImageResource(R.drawable.ic_free)
            Glide.with(list.context)
                .load(list.icon.icons.get(position).raster_sizes.get(2).formats.get(0).preview_url)
                .into(holder.binding.imgViewIcon)
        }
        holder.binding.tvIconTags.text = list.icon.icons.get(position).tags.toString()
        holder.binding.iconListCardView.setOnClickListener (
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val activity = v!!.context as AppCompatActivity
                        val dialog = DownloadDialogFragment(list.icon.icons.get(position).raster_sizes)
                        dialog.show(activity.supportFragmentManager,"Download Dialog")
                    }
                }
        )

    }

    override fun getItemCount(): Int {
        return list.icon.icons.size
    }
}