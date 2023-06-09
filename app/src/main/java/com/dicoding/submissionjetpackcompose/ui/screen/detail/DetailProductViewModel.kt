package com.dicoding.submissionjetpackcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionjetpackcompose.data.ClothesRepository
import com.dicoding.submissionjetpackcompose.model.Clothes
import com.dicoding.submissionjetpackcompose.ui.common.UiStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailProductViewModel(private val detailRepository: ClothesRepository) : ViewModel() {
    private val _stateUi: MutableStateFlow<UiStates<Clothes>> = MutableStateFlow(UiStates.Loading)
    private val _statusFavoriteClothes = MutableStateFlow(false)
    val statusFavoriteClothes: StateFlow<Boolean>
        get() = _statusFavoriteClothes

    val stateUi: StateFlow<UiStates<Clothes>>
        get() = _stateUi

    fun getClothesById(idClothes:Long) {
        viewModelScope.launch {
            _stateUi.value = UiStates.Loading
            _stateUi.value = UiStates.Success(detailRepository.getClothesById(idClothes))
        }
    }

    fun updateFavoriteClothesStatus(id: Long) = viewModelScope.launch {
        _statusFavoriteClothes.value = detailRepository.isFavoritedClothes(id).first()
    }

    fun favoriteChanged(favoriteClothes: Clothes) {
        viewModelScope.launch {
            if (_statusFavoriteClothes.value) {
                detailRepository.delete(favoriteClothes)
            } else {
                detailRepository.insert(favoriteClothes)
            }
        }
    }
}