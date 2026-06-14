package com.example.a207385_wangling_cikguizwan_project2

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GroceryViewModel(private val repository: GroceryRepository) : ViewModel() {

    val allIngredients = listOf(
        GroceryItem(name = "Onion", imageRes = R.drawable.onion, category = "Health Choices", impactValue = 0.3),
        GroceryItem(name = "Cucumber", imageRes = R.drawable.cucumber, category = "Health Choices", impactValue = 0.2),
        GroceryItem(name = "Spinach", imageRes = R.drawable.spinach, category = "Health Choices", impactValue = 0.25),
        GroceryItem(name = "Garlic", imageRes = R.drawable.garlic, category = "Health Choices", impactValue = 0.15),
        GroceryItem(name = "Ginger", imageRes = R.drawable.ginger, category = "Health Choices", impactValue = 0.2),
        GroceryItem(name = "Tomato", imageRes = R.drawable.tomato, category = "Vegetables", impactValue = 0.4),
        GroceryItem(name = "Carrot", imageRes = R.drawable.carrot, category = "Vegetables", impactValue = 0.35),
        GroceryItem(name = "Broccoli", imageRes = R.drawable.broccoli, category = "Vegetables", impactValue = 0.45),
        GroceryItem(name = "Potato", imageRes = R.drawable.potato, category = "Vegetables", impactValue = 0.5),
        GroceryItem(name = "Corn", imageRes = R.drawable.corn, category = "Vegetables", impactValue = 0.55),
        GroceryItem(name = "Apple", imageRes = R.drawable.apple, category = "Fresh Fruits", impactValue = 0.3),
        GroceryItem(name = "Banana", imageRes = R.drawable.banana, category = "Fresh Fruits", impactValue = 0.25),
        GroceryItem(name = "Orange", imageRes = R.drawable.orange, category = "Fresh Fruits", impactValue = 0.28),
        GroceryItem(name = "Grape", imageRes = R.drawable.grape, category = "Fresh Fruits", impactValue = 0.32),
        GroceryItem(name = "Mango", imageRes = R.drawable.mango, category = "Fresh Fruits", impactValue = 0.4)
    )

    var selectedItem by mutableStateOf<GroceryItem?>(null)

    val summaryItems: StateFlow<List<GroceryItem>> = repository.allItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _communityItems = MutableStateFlow<List<CommunityFoodItem>>(emptyList())
    val communityItems: StateFlow<List<CommunityFoodItem>> = _communityItems

    private val _uploadStatus = MutableStateFlow<String?>(null)
    val uploadStatus: StateFlow<String?> = _uploadStatus

    private val _apiStatus = MutableStateFlow<String?>(null)
    val apiStatus: StateFlow<String?> = _apiStatus

    fun startListeningCommunity() {
        repository.listenToCommunityItems { items ->
            _communityItems.value = items
        }
    }

    fun selectItem(item: GroceryItem) { selectedItem = item }

    fun addIngredient(item: GroceryItem) {
        viewModelScope.launch { repository.insertItem(item) }
    }

    fun removeIngredient(item: GroceryItem) {
        viewModelScope.launch { repository.deleteItem(item) }
    }

    fun resetProjectData() {
        viewModelScope.launch { repository.clearAll() }
        selectedItem = null
    }

    fun donateToCloud(item: GroceryItem) {
        repository.donateItem(item) { result ->
            if (result == "success") {
                _uploadStatus.value = "✅ Donated ${item.name} to community!"
                android.util.Log.d("FIREBASE_TEST", "Upload Success")
            } else {
                _uploadStatus.value = "❌ Upload failed: $result"
                android.util.Log.e("FIREBASE_TEST", "Upload Failed: $result")
            }
        }
    }

    fun clearUploadStatus() { _uploadStatus.value = null }
    fun fetchAndAddScannedItem(barcode: String) {
        viewModelScope.launch {
            _apiStatus.value = "🔍 Searching barcode $barcode..."
            try {
                val response = RetrofitInstance.api.getProductByBarcode(barcode)
                if (response.status == 1 && response.product != null) {
                    val productName = response.product.product_name ?: "Unknown Product"
                    val category = response.product.categories?.split(",")?.firstOrNull()?.trim() ?: "Scanned"
                    val imageRes = getImageResForCategory(category, productName)

                    val newItem = GroceryItem(
                        name = productName,
                        imageRes = imageRes,
                        category = category,
                        impactValue = 0.5
                    )
                    addIngredient(newItem)
                    _apiStatus.value = "✅ Added: $productName"
                } else {
                    _apiStatus.value = "❌ Product not found in database"
                }
            } catch (e: Exception) {
                _apiStatus.value = "❌ Network error: ${e.message}"
                e.printStackTrace()
            }
        }
    }

    fun clearApiStatus() { _apiStatus.value = null }

   fun getImageResForCategory(category: String, name: String): Int {
        val combined = (category + name).lowercase()
        return when {
            combined.contains("apple") -> R.drawable.apple
            combined.contains("banana") -> R.drawable.banana
            combined.contains("orange") -> R.drawable.orange
            combined.contains("mango") -> R.drawable.mango
            combined.contains("grape") -> R.drawable.grape
            combined.contains("tomato") -> R.drawable.tomato
            combined.contains("carrot") -> R.drawable.carrot
            combined.contains("broccoli") -> R.drawable.broccoli
            combined.contains("potato") -> R.drawable.potato
            combined.contains("corn") -> R.drawable.corn
            combined.contains("onion") -> R.drawable.onion
            combined.contains("cucumber") -> R.drawable.cucumber
            combined.contains("spinach") -> R.drawable.spinach
            combined.contains("garlic") -> R.drawable.garlic
            combined.contains("ginger") -> R.drawable.ginger
            combined.contains("fruit") -> R.drawable.apple
            combined.contains("vegetable") || combined.contains("veggie") -> R.drawable.broccoli
            else -> R.drawable.box
        }
    }
}

data class CommunityFoodItem(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val impactValue: Double = 0.0,
    val timestamp: Long = 0L
)

class GroceryViewModelFactory(private val repository: GroceryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GroceryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}