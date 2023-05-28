package com.javafx.lab_6.data

interface Repository {
    fun getAll(): List<Product?>
    fun getById(id: Int): Product?
    fun getAllByCategory(category: String): List<Product>
    fun getProductsIsNotTheStorage(): List<Product>
    fun addProduct(product: Product): Boolean
    fun updateProduct(id: Int, product: Product): Boolean
    fun deleteProduct(id: Int): Boolean
}
