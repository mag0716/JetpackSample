package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private var _themeList = MutableLiveData<List<Theme>>()
    val themeList: LiveData<List<Theme>> = _themeList

    private var _gardenList = MutableLiveData<List<Garden>>()
    val gardenList: LiveData<List<Garden>> = _gardenList

    // TODO: 選択状態の保持

    init {
        // FIXME: 非同期で取得できるようにする
        _themeList.value = Theme.values().toList()
        _gardenList.value = Garden.values().toList()
    }
}