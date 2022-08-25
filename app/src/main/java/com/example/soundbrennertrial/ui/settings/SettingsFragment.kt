package com.example.soundbrennertrial.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.example.soundbrennertrial.model.CommonModel
import com.example.soundbrennertrial.recyclerview.ListAdapter
import com.example.soundbrennertrial.model.Listener
import com.example.soundbrennertrial.R
import com.example.soundbrennertrial.databinding.FragmentSettingsBinding
import com.example.soundbrennertrial.model.EnumCategory
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(),
    Listener {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    lateinit var topListAdapter: ListAdapter
    lateinit var bottomListAdapter: ListAdapter
    lateinit var settingsViewModel: SettingsViewModel
    lateinit var topList: ArrayList<CommonModel>
    lateinit var bottomList: ArrayList<CommonModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        settingsViewModel.initLists()
        initobservers()
        return root
    }

    private fun initobservers() {
        settingsViewModel.alTopLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                topList = it
                binding.rvTop.layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.VERTICAL, false
                )
                topListAdapter = ListAdapter(topList, this)
                binding.rvTop.adapter = topListAdapter
                binding.rvTop.setOnDragListener(topListAdapter.dragInstance)
            }
        })
        settingsViewModel.alBottomLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                bottomList = it
                binding.rvBottom.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                bottomListAdapter = ListAdapter(bottomList, this)
                binding.rvBottom.adapter = bottomListAdapter
                binding.rvBottom.setOnDragListener(bottomListAdapter.dragInstance)
            }
        })
    }


    override fun swapitems(commonModel: CommonModel?) {
        if (commonModel?.category == EnumCategory.Include) {
            topList.remove(commonModel)
            commonModel.category = EnumCategory.DontInclude
            bottomList.add(commonModel)
        } else if (commonModel?.category == EnumCategory.DontInclude) {
            bottomList.remove(commonModel)
            commonModel.category = EnumCategory.Include
            topList.add(commonModel)
        }
        topListAdapter.updateList(topList)
        bottomListAdapter.updateList(bottomList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}