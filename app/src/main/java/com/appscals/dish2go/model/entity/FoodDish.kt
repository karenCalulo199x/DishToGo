package com.appscals.dish2go.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_dish_table")
data class FoodDish(
    @ColumnInfo val image: String,
    @ColumnInfo(name = "image_source") val imageSource: String,
    @ColumnInfo val title: String,
    @ColumnInfo val type: String,
    @ColumnInfo val category: String,
    @ColumnInfo val ingredient: String,
    @ColumnInfo(name = "cooking_time") val cookingTime: String,
    @ColumnInfo(name = "instruction") val cookingDirection: String,
    @ColumnInfo(name = "favorite_dish") val isFavoriteDish: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)