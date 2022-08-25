package com.example.soundbrennertrial.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundbrennertrial.model.CommonModel
import com.example.soundbrennertrial.model.EnumCategory

class SettingsViewModel : ViewModel() {
    private var _alToplivedata = MutableLiveData<ArrayList<CommonModel>?>()
    val alTopLiveData: LiveData<ArrayList<CommonModel>?> = _alToplivedata
    private var _alBottomlivedata = MutableLiveData<ArrayList<CommonModel>?>()
    val alBottomLiveData: LiveData<ArrayList<CommonModel>?> = _alBottomlivedata


    fun initLists(){
        val topList: ArrayList<CommonModel> = ArrayList()
        val bottomList: ArrayList<CommonModel> = ArrayList()
        topList.add(CommonModel("Tuner",true, EnumCategory.Include,false))
        topList.add(CommonModel("Metronome",true, EnumCategory.Include,false))
        topList.add(CommonModel("Handheld Tuner",true, EnumCategory.Include,false))
        topList.add(CommonModel("dB meter",true, EnumCategory.Include,false))
        topList.add(CommonModel("Timer",true, EnumCategory.Include,false))
        topList.add(CommonModel("StopWatch",true, EnumCategory.Include,false))
        topList.add(CommonModel("Settings",true, EnumCategory.Include,true))

        _alToplivedata.postValue(topList);

        bottomList.add(CommonModel("Alarm",true,EnumCategory.DontInclude,false))
        bottomList.add(CommonModel("Contact Tuner",false,EnumCategory.DontInclude,false))
        _alBottomlivedata.postValue(bottomList);
    }
}