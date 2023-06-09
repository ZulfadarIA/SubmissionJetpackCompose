package com.dicoding.submissionjetpackcompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clothes")
data class Clothes(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val image: Int,
    val titleProd: String,
    val descriptionProd: String,
    val detailProd: String
)
