package com.dicoding.submissionjetpackcompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.submissionjetpackcompose.model.Clothes
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Clothes)

    @Delete
    suspend fun delete(favorite: Clothes)

    @Query("SELECT * FROM Clothes")
    fun getAllFavoriteClothes(): Flow<List<Clothes>>

    @Query("SELECT EXISTS(SELECT * FROM Clothes WHERE id = :id)")
    fun isFavorited(id: Long): Flow<Boolean>
}