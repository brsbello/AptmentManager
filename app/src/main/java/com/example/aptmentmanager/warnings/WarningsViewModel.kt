package com.example.aptmentmanager.warnings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aptmentmanager.models.WarningModel
import com.example.aptmentmanager.warnings.data.WarningRepository

class WarningsViewModel : ViewModel() {

    private val liveData = MutableLiveData<List<WarningModel>>()

    //fun searhWarning() : LiveData<List<WarningModel>> {
    //
    //    return WarningRepository.searchAllWarnings()
    //}

}