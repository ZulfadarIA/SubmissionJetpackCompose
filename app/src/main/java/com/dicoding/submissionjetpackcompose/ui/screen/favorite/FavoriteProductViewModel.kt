package com.dicoding.submissionjetpackcompose.ui.screen.favorite

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

class FavoriteProductViewModel(private val favRepository: ClothesRepository) : ViewModel() {
    private val _stateUi: MutableStateFlow<UiStates<List<Clothes>>> = MutableStateFlow(UiStates.Loading)
    val stateUi: StateFlow<UiStates<List<Clothes>>>
        get() = _stateUi

    fun getAllFavoritedClothes() {
        viewModelScope.launch {
            favRepository.getAllClothes()
                .catch {
                    _stateUi.value = UiStates.Error(it.message.toString())
                }
                .collect { favoritedClothesList ->
                    _stateUi.value = UiStates.Success(favoritedClothesList)
                }
        }
    }
}