package com.example.soundbrennertrial.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.example.soundbrennertrial.model.CommonModel
import com.example.soundbrennertrial.recyclerview.ListAdapter
import com.example.soundbrennertrial.model.Listener
import com.example.soundbrennertrial.R
import com.example.soundbrennertrial.databinding.FragmentSettingsBinding
import com.example.soundbrennertrial.model.EnumCategory

class SettingsFragment : Fragment() ,
    Listener {
    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    lateinit var rvTop: RecyclerView
    lateinit var rvBottom: RecyclerView
    val topList: MutableList<CommonModel> = ArrayList()
    val bottomList: MutableList<CommonModel> = ArrayList()
    lateinit var  topListAdapter:ListAdapter
    lateinit var  bottomListAdapter:ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val metronomeViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        rvTop=root.findViewById<RecyclerView>(R.id.rvTop)
        rvBottom=root.findViewById<RecyclerView>(R.id.rvBottom)
        initTopRecyclerView()
        initBottomRecyclerView()
             return root
    }

    private fun initTopRecyclerView() {
        rvTop.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        topList.add(CommonModel("Tuner",true,EnumCategory.Include,false))
        topList.add(CommonModel("Metronome",true,EnumCategory.Include,false))
        topList.add(CommonModel("Handheld Tuner",true,EnumCategory.Include,false))
        topList.add(CommonModel("dB meter",true,EnumCategory.Include,false))
        topList.add(CommonModel("Timer",true,EnumCategory.Include,false))
        topList.add(CommonModel("StopWatch",true,EnumCategory.Include,false))
        topList.add(CommonModel("Settings",true,EnumCategory.Include,true))
         topListAdapter =
            ListAdapter(topList, this)
        rvTop.adapter = topListAdapter
        rvTop.setOnDragListener(topListAdapter.dragInstance)
    }

    private fun initBottomRecyclerView() {
        rvBottom.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        bottomList.add(CommonModel("Alarm",true,EnumCategory.DontInclude,false))
        bottomList.add(CommonModel("Contact Tuner",false,EnumCategory.DontInclude,false))
        bottomListAdapter = ListAdapter(bottomList, this)
        rvBottom.adapter = bottomListAdapter
        rvBottom.setOnDragListener(bottomListAdapter.dragInstance)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun swapitems(commonModel: CommonModel?) {
        if (commonModel?.category==EnumCategory.Include){
            topList.remove(commonModel)
            commonModel.category=EnumCategory.DontInclude
            bottomList.add(commonModel)
        }
        else if(commonModel?.category==EnumCategory.DontInclude)
        {
            bottomList.remove(commonModel)
            commonModel.category=EnumCategory.Include
            topList.add(commonModel)
        }
        topListAdapter.notifyDataSetChanged()
        bottomListAdapter.notifyDataSetChanged()
    }

}