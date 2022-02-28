package com.appscals.dish2go.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appscals.dish2go.model.entity.FoodDish

@Database(entities = [FoodDish::class], version = 1)
abstract class FoodDishDB : RoomDatabase() {
    abstract fun foodDishDao(): FoodDishDao

    companion object {
        @Volatile
        private var INSTANCE: FoodDishDB? = null

        fun getDatabase(ctx: Context): FoodDishDB {
            //if instance is not null then return it
            //else create database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    FoodDishDB::class.java,
                    "food_dish_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // Return instance
                instance
            }
        }
    }

}