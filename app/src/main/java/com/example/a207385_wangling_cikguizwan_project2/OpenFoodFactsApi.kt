package com.example.a207385_wangling_cikguizwan_project2

import retrofit2.http.GET
import retrofit2.http.Path
interface OpenFoodFactsApi {
    @GET("api/v0/product/{barcode}.json")
    suspend fun getProductByBarcode(@Path("barcode") barcode: String): ProductResponse
}
data class ProductResponse(
    val status: Int,
    val product: ProductInfo?
)
data class ProductInfo(
    val product_name: String?,
    val generic_name: String?,
    val categories: String?,
    val ecoscore_grade: String?
)