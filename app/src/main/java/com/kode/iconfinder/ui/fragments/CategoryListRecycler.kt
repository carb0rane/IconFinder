package com.kode.iconfinder.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.R
import com.kode.iconfinder.databinding.CategorylistBinding

private const val TAG = "CategoryListRecycler"
class CategoryListRecycler(viewModel: IconFinderViewModel) : RecyclerView.Adapter<CategoryListRecycler.CategoryListViewHolder>() {
 val list = viewModel
    inner class CategoryListViewHolder(val binding: CategorylistBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(CategorylistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
         holder.binding.tvSongNameRecycler.text = list.category.categories.get(position).name
        holder.binding.rcViewSongCardView.setOnClickListener(
            object : View.OnClickListener {
                
                override fun onClick(v: View?) {
                    Log.d(TAG, "onClick: ")
                    val activity = v!!.context as AppCompatActivity
                    activity.supportFragmentManager.beginTransaction().apply {
                        val bundle = Bundle()
                        val _position = holder.absoluteAdapterPosition
                        bundle.putString(
                            "identifier",
                            list.category.categories.get(_position).identifier
                        )
                        val iconSetFragment = IconSet()
                        iconSetFragment.arguments = bundle
                        replace(R.id.frmlMain, iconSetFragment)
                        addToBackStack(null)
                        commit()
                    }
                }
            }
        )


    }

    override fun getItemCount(): Int {
        return list.category.categories.size
    }
}