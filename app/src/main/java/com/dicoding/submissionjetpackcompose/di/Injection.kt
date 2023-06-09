package com.dicoding.submissionjetpackcompose.di

import android.content.Context
import com.dicoding.submissionjetpackcompose.data.ClothesRepository
import com.dicoding.submissionjetpackcompose.data.FavoriteDataBase

object Injection {
    fun provideRepository(context: Context): ClothesRepository {
        return  ClothesRepository(
            database = FavoriteDataBase.getDataBaseClothes(context)
        )
    }
}