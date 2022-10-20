package com.kode.iconfinder.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.R
import com.kode.iconfinder.databinding.FragmentHomeBinding

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {
 lateinit var binding : FragmentHomeBinding
 var iconViewModel:IconFinderViewModel?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 25
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible
          //  Log.d(TAG, "onScrolled: $isNotLoadingAndNotLastPage $isAtLastItem $isNotAtBeginning $isTotalMoreThanVisible $isScrolling")
            if(shouldPaginate) {
                Log.d(TAG, "onScrolled: calling kdj")
                iconViewModel?.getHomeList()
                isScrolling = false
            } else {
             //   Log.d(TAG, "onScrolled: !-!")
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         iconViewModel = ViewModelProvider(requireActivity())[IconFinderViewModel::class.java]
        iconViewModel?.getHomeList()
        iconViewModel!!.categoryList.observe(viewLifecycleOwner){
           it.categories.forEach {
               iconViewModel!!.category.categories.add(it)
               binding.rcViewCategoryList.adapter?.notifyItemChanged(0)
           }
        }

        binding.rcViewCategoryList.adapter = CategoryListRecycler(iconViewModel!!)
       // binding.rcViewCategoryList.layoutManager = GridLayoutManager(context,2)



        binding.rcViewCategoryList.addOnScrollListener(this@HomeFragment.scrollListener)





    }




}