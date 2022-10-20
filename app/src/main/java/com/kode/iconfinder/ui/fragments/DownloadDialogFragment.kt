package com.kode.iconfinder.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kode.iconfinder.IconFinder.RasterSize
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.R
import com.kode.iconfinder.databinding.FragmentDownloadDialogBinding

private const val TAG = "DownloadDialogFragment"

class DownloadDialogFragment(val rastors:List<RasterSize>) : DialogFragment() {
 private lateinit var binding: FragmentDownloadDialogBinding
    var iconViewModel: IconFinderViewModel?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iconViewModel = ViewModelProvider(requireActivity())[IconFinderViewModel::class.java]
        binding.rcViewDownloadDialog.adapter = DownloadRecycler(rastors,iconViewModel!!)
        binding.rcViewDownloadDialog.layoutManager = LinearLayoutManager(context)
        Glide.with(requireContext())
            .load(rastors.get(2).formats.get(0).preview_url)
            .into(binding.imgViewDownloadIcon)

    }


}