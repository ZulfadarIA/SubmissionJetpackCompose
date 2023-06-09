package com.dicoding.submissionjetpackcompose.ui.screen.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionjetpackcompose.data.ClothesRepository
import com.dicoding.submissionjetpackcompose.model.Clothes
import com.dicoding.submissionjetpackcompose.ui.common.UiStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ClothesRepository) : ViewModel() {
    private val _stateUi: MutableStateFlow<UiStates<List<Clothes>>> = MutableStateFlow(UiStates.Loading)
    val statUi: StateFlow<UiStates<List<Clothes>>>
        get() = _stateUi

    fun getAllClothes() {
        viewModelScope.launch {
            repository.getAllClothes()
                .catch {
                    _stateUi.value = UiStates.Error(it.message.toString())
                }

                .collect { listClothes ->
                    _stateUi.value = UiStates.Success(listClothes)
                }
        }
    }
}