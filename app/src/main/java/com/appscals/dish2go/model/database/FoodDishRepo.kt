package com.appscals.dish2go.model.database

import androidx.annotation.WorkerThread
import com.appscals.dish2go.model.entity.FoodDish

class FoodDishRepo(private val foodDishDao: FoodDishDao) {
    @WorkerThread
    suspend fun insertFoodDishData(foodDish: FoodDish) {
        foodDishDao.insertFoodDish(foodDish)
    }
}