package com.example.a207385_wangling_cikguizwan_project2

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow

class GroceryRepository(
    private val dao: GroceryDao,
    private val db: FirebaseFirestore
) {


    val allItems: Flow<List<GroceryItem>> = dao.getAllItems()

    suspend fun insertItem(item: GroceryItem) {
        dao.insertItem(item)
    }

    suspend fun deleteItem(item: GroceryItem) {
        dao.deleteItem(item)
    }

    suspend fun clearAll() {
        dao.deleteAllItems()
    }

    fun donateItem(item: GroceryItem, onResult: (String) -> Unit) {
        val data = hashMapOf(
            "name" to item.name,
            "category" to item.category,
            "impactValue" to item.impactValue,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("community_food_bank")
            .add(data)
            .addOnSuccessListener {
                onResult("success")
            }
            .addOnFailureListener {
                onResult("error: ${it.message}")
            }
    }


    fun listenToCommunityItems(onUpdate: (List<CommunityFoodItem>) -> Unit) {
        db.collection("community_food_bank")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    android.util.Log.e("FIRESTORE", "Listen failed: ${error.message}")
                    return@addSnapshotListener
                }
                val items = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        CommunityFoodItem(
                            id = doc.id,
                            name = doc.getString("name") ?: "Unknown",
                            category = doc.getString("category") ?: "Unknown",
                            impactValue = doc.getDouble("impactValue") ?: 0.0,
                            timestamp = doc.getLong("timestamp") ?: 0L
                        )
                    } catch (e: Exception) { null }
                } ?: emptyList()

                onUpdate(items)
            }
    }
}