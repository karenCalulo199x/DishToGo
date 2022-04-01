package com.appscals.dish2go.viewmodel

import androidx.lifecycle.ViewModel
import com.appscals.dish2go.model.database.FoodDishRepo
import com.appscals.dish2go.model.entity.FoodDish

class FoodDishVM(private val repository: FoodDishRepo): ViewModel() {

//    fun insertFood(dish: FoodDish) = viewModelScope.launch {
//        repository.insertFoodDishData(dish)
//    }
}

