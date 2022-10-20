package com.kode.iconfinder.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.R
import com.kode.iconfinder.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var iconViewModel:IconFinderViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iconViewModel = ViewModelProvider(requireActivity())[IconFinderViewModel::class.java]
        CoroutineScope(Dispatchers.IO).launch {

           iconViewModel.getTextQuery(binding.etSearch.searchFlow())

        }
        binding.rcViewSearch.adapter = SearchRecycler(iconViewModel)
        binding.rcViewSearch.layoutManager = GridLayoutManager(context,4)
        iconViewModel.liveSearchResult.observe(viewLifecycleOwner)
        {
            iconViewModel.searchResult.icons.clear()
            iconViewModel.searchResult.icons = it.icons
            iconViewModel.searchResult.total_count = it.total_count
            binding.rcViewSearch.adapter?.notifyDataSetChanged()
        }
    }

    fun EditText.searchFlow(): Flow<CharSequence?> {
        return callbackFlow<CharSequence?> {
            val listener = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Log.d(TAG, "onTextChanged: $s")
                    trySend(s)
                }

                override fun afterTextChanged(s: Editable?) {

                }

            }

            addTextChangedListener(listener)
            awaitClose {
                removeTextChangedListener(listener)
            }

        }//.onStart { emit(text) }

    }

}