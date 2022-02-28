package com.appscals.dish2go.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.appscals.dish2go.model.entity.FoodDish

@Dao
interface FoodDishDao {
    @Insert
    suspend fun insertFoodDish(foodDish: FoodDish)
}