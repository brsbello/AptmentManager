package com.example.aptmentmanager.ui.warnings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aptmentmanager.data.models.WarningModel

class WarningsViewModel : ViewModel() {

    private val liveData = MutableLiveData<List<WarningModel>>()

    //fun searhWarning() : LiveData<List<WarningModel>> {
    //
    //    return WarningRepository.searchAllWarnings()
    //}

}