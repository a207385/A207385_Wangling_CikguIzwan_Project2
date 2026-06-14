package com.example.a207385_wangling_cikguizwan_project2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [GroceryItem::class], version = 1, exportSchema = false)
abstract class GroceryDatabase : RoomDatabase() {

    abstract fun groceryDao(): GroceryDao

    companion object {
        @Volatile
        private var Instance: GroceryDatabase? = null

        fun getDatabase(context: Context): GroceryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    GroceryDatabase::class.java,
                    "grocery_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}