package com.kode.iconfinder.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.R
import com.kode.iconfinder.databinding.CategorylistBinding
import com.kode.iconfinder.databinding.IconsetlistBinding

private const val TAG = "IconSetRecycler"

class IconSetRecycler(viewModel: IconFinderViewModel) :
    RecyclerView.Adapter<IconSetRecycler.IconSetViewHolder>() {
    val list = viewModel

    inner class IconSetViewHolder(val binding: IconsetlistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconSetViewHolder {
        return IconSetViewHolder(
            IconsetlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IconSetViewHolder, position: Int) {
        holder.binding.tvIconSetName.text = list.iconset.iconsets.get(position).name
        holder.binding.tvIconSetIconCount.text =
            list.iconset.iconsets.get(position).icons_count.toString() + " icons"
        if (!list.iconset.iconsets.get(position).is_premium) {
            holder.binding.imgViewPremiumStatus.setImageResource(R.drawable.ic_free)

            holder.binding.iconSetCardView.setOnClickListener(
                object : View.OnClickListener {

                    override fun onClick(v: View?) {
                        val activity = v!!.context as AppCompatActivity
                        activity.supportFragmentManager.beginTransaction().apply {
                            val bundle = Bundle()
                            val _position = holder.absoluteAdapterPosition
                            bundle.putInt(
                                "iconset_id",
                                list.iconset.iconsets.get(_position).iconset_id
                            )
                            val iconFragment = IconFragment()
                            iconFragment.arguments = bundle
                            replace(R.id.frmlMain, iconFragment)
                            addToBackStack(null)
                            commit()
                        }

                    }
                }
            )
        }else {
            holder.binding.iconSetCardView.setOnClickListener {
                Snackbar.make(
                    holder.itemView,
                    "Unable to load premium icon pack !",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

//        holder.binding.rcViewSongCardView.setOnClickListener {
//            list.selectedCategory.postValue(list.iconset.iconsets.get(position).identifier)
//        }
    }

    override fun getItemCount(): Int {
        return list.iconset.iconsets.size
    }
}