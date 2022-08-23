package com.example.soundbrennertrial.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.example.soundbrennertrial.CommonModel
import com.example.soundbrennertrial.ListAdapter
import com.example.soundbrennertrial.Listener
import com.example.soundbrennertrial.R
import com.example.soundbrennertrial.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() , Listener {
    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @BindView(R.id.rvTop)
    var rvTop: RecyclerView? = null
    @BindView(R.id.rvBottom)
    var rvBottom: RecyclerView? = null
    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val metronomeViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        rvTop=root.findViewById<RecyclerView>(R.id.rvTop)
        rvBottom=root.findViewById<RecyclerView>(R.id.rvBottom)
        // ButterKnife.bind(this)
        initTopRecyclerView()
        initBottomRecyclerView()
             return root
    }

    private fun initTopRecyclerView() {
        rvTop!!.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        val topList: MutableList<CommonModel> = ArrayList()
        topList.add(CommonModel("A",true))
        topList.add(CommonModel("B",true))
        val topListAdapter = ListAdapter(topList, this)
        rvTop!!.adapter = topListAdapter
        rvTop!!.setOnDragListener(topListAdapter.dragInstance)
    }

    private fun initBottomRecyclerView() {
        rvBottom!!.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        val bottomList: MutableList<CommonModel> = ArrayList()
        bottomList.add(CommonModel("C",true))
        bottomList.add(CommonModel("D",true))
        val bottomListAdapter = ListAdapter(bottomList, this)
        rvBottom!!.adapter = bottomListAdapter
        rvBottom!!.setOnDragListener(bottomListAdapter.dragInstance)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setEmptyListTop(visibility: Boolean) {
    }

    override fun setEmptyListBottom(visibility: Boolean) {
    }

}