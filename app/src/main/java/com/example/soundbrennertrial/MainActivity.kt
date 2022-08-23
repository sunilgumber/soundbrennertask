package com.example.soundbrennertrial

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : AppCompatActivity(),Listener {

    @BindView(R.id.rvTop)
    var rvTop: RecyclerView? = null
    @BindView(R.id.rvBottom)
    var rvBottom: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvTop=findViewById<RecyclerView>(R.id.rvTop)
        rvBottom=findViewById<RecyclerView>(R.id.rvBottom)
       // ButterKnife.bind(this)
        initTopRecyclerView()
        initBottomRecyclerView()
    }


    private fun initTopRecyclerView() {
        rvTop!!.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
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
            this, LinearLayoutManager.VERTICAL, false
        )
        val bottomList: MutableList<CommonModel> = ArrayList()
        bottomList.add(CommonModel("C",true))
        bottomList.add(CommonModel("D",true))
        val bottomListAdapter = ListAdapter(bottomList, this)
        rvBottom!!.adapter = bottomListAdapter
        rvBottom!!.setOnDragListener(bottomListAdapter.dragInstance)
    }

    override fun setEmptyListTop(visibility: Boolean) {
    }

    override fun setEmptyListBottom(visibility: Boolean) {
    }


}