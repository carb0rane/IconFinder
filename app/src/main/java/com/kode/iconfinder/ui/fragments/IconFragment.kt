package com.kode.iconfinder.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kode.iconfinder.IconFinder.viewmodel.IconFinderViewModel
import com.kode.iconfinder.R
import com.kode.iconfinder.databinding.FragmentIconBinding

private const val TAG = "IconFragment"
class IconFragment : Fragment() {
    private lateinit var binding: FragmentIconBinding
    var iconViewModel: IconFinderViewModel? = null
    var iconset_id = 1761


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIconBinding.inflate(inflater)
        iconset_id = arguments?.getInt("iconset_id")?.toInt() ?: 1761
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iconViewModel = ViewModelProvider(requireActivity())[IconFinderViewModel::class.java]
        iconViewModel!!.icon.icons.clear()
        iconViewModel!!.iconset_id = iconset_id
        iconViewModel!!.liveIcon.value = null
        iconViewModel!!.getIconFromIconSet()

        iconViewModel!!.liveIcon.observe(viewLifecycleOwner){
            if (it != null) {
                it.icons.forEach{
                    iconViewModel!!.icon.icons.add(it)
                    binding.rcViewIcons.adapter?.notifyItemChanged(0)
                }
            }
        }

        binding.rcViewIcons.adapter = IconRecyclerView(iconViewModel!!)
        binding.rcViewIcons.layoutManager = GridLayoutManager(context,4)


    }

}