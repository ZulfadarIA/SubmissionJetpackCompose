package com.dicoding.submissionjetpackcompose.data

import com.dicoding.submissionjetpackcompose.model.Clothes
import com.dicoding.submissionjetpackcompose.model.DummyClothesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class ClothesRepository(private val database: FavoriteDataBase) {
    private val clothesList = mutableListOf<Clothes>()

    init {
        if (clothesList.isEmpty()) {
            DummyClothesDataSource.dummyClothes.forEach { clothes ->
                clothesList.add(
                    Clothes(
                        id = clothes.id,
                        image = clothes.image,
                        titleProd = clothes.titleProd,
                        descriptionProd = clothes.descriptionProd,
                        detailProd = clothes.detailProd
                    )
                )
            }
        }
    }

    fun getAllClothes() : Flow<List<Clothes>> {
        return flowOf(clothesList)
    }

    suspend fun insert(favoriteClothes: Clothes) {
        withContext(Dispatchers.IO) {
            database.favoriteProductDao().delete(favoriteClothes)
        }
    }

    fun getClothesById(idClothes: Long) : Clothes {
        return clothesList.first { clothes ->
            clothes.id == idClothes
        }
    }

    suspend fun delete(favoriteClothes: Clothes) {
        withContext(Dispatchers.IO) {
            database.favoriteProductDao().delete(favoriteClothes)
        }
    }

    fun isFavoritedClothes(idClothes: Long): Flow<Boolean> {
        return database.favoriteProductDao().isFavorited(idClothes)
    }

    fun getAllFavoritedClothes(): Flow<List<Clothes>> {
        return database.favoriteProductDao().getAllFavoriteClothes()
    }
}