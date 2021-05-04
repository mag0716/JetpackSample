package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.lifecycle.*

class HomeViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var _themeList = MutableLiveData<List<Theme>>()
    val themeList: LiveData<List<Theme>> = _themeList

    private var _gardenList = MutableLiveData<List<Garden>>()
    private var _selectedGardenList = savedStateHandle.getLiveData<List<Garden>>(
        KEY_SELECTED_GARDEN_LIST,
        emptyList()
    )
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
        setSelectedGardenList(selectedGardenList)
        _selectedGardenList.value = selectedGardenList
    }

    private fun setSelectedGardenList(selectedGardenList: List<Garden>) {
        savedStateHandle[KEY_SELECTED_GARDEN_LIST] = selectedGardenList
    }

    companion object {
        private const val KEY_SELECTED_GARDEN_LIST = "SelectedGardenList"
    }
}