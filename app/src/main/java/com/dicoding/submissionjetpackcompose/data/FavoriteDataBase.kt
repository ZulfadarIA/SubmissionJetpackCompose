package com.dicoding.submissionjetpackcompose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.submissionjetpackcompose.model.Clothes

@Database(
    entities = [Clothes::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteDataBase : RoomDatabase() {

    abstract fun favoriteProductDao() : FavoriteProductDao

    companion object {
        @Volatile
        var INSTANCE: FavoriteDataBase? = null

        @JvmStatic
        fun getDataBaseClothes(context: Context): FavoriteDataBase {
            return  INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDataBase::class.java, "favorite_clothes_database"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}