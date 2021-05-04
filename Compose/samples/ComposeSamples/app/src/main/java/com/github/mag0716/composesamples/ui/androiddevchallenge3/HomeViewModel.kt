package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private var _themeList = MutableLiveData<List<Theme>>()
    val themeList: LiveData<List<Theme>> = _themeList

    private var _gardenList = MutableLiveData<List<Garden>>()
    private var _selectedGardenList = MutableLiveData<List<Garden>>()
    val gardenListWithSelectedStatus = MediatorLiveData<List<Pair<Garden, Boolean>>>()

    init {
        gardenListWithSelectedStatus.addSource(_gardenList) { gardenList ->
            val selectedGardenList = _selectedGardenList.value
            gardenListWithSelectedStatus.value = gardenList.map { garden ->
                Pair(garden, selectedGardenList?.contains(garden) ?: false)
            }
        }
        gardenListWithSelectedStatus.addSource(_selectedGardenList) { selectedGardenList ->
            val gardenList = _gardenList.value
            gardenListWithSelectedStatus.value = gardenList?.map { garden ->
                Pair(garden, selectedGardenList?.contains(garden) ?: false)
            }
        }
        // FIXME: 非同期で取得できるようにする
        _themeList.value = Theme.values().toList()
        _gardenList.value = Garden.values().toList()
    }

    fun addOrRemoveSelectedGarden(garden: Garden) {
        val selectedGardenList = mutableListOf<Garden>()
        _selectedGardenList.value?.let { selectedGardenList.addAll(it) }
        if (selectedGardenList.contains(garden)) {
            selectedGardenList.remove(garden)
        } else {
            selectedGardenList.add(garden)
        }
        _selectedGardenList.value = selectedGardenList
    }
}