package com.dicoding.submissionjetpackcompose.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissionjetpackcompose.data.ClothesRepository
import com.dicoding.submissionjetpackcompose.di.Injection

class ViewModelFactory(private  val clothesRepository: ClothesRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}