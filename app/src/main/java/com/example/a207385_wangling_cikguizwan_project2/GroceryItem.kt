package com.example.a207385_wangling_cikguizwan_project2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_table")
data class GroceryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val imageRes: Int,
    val impactValue: Double
)