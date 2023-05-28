package com.javafx.lab_6.data

import java.io.Serializable
import java.time.LocalDate

data class Product(
    var id: Int = 0,
    var nameProduct: String = "",
    var category: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var isOnStorage: Boolean = false,
    var amount: Int = 0,
    var deliveryDate: LocalDate = LocalDate.now()
) : Serializable {

    constructor(
        nameProduct: String,
        category: String,
        description: String,
        price: Double,
        isOnStorage: Boolean,
        amount: Int
    ) : this() {
        this.nameProduct = nameProduct
        this.category = category
        this.description = description
        this.price = price
        this.isOnStorage = isOnStorage
        this.amount = amount
        this.deliveryDate = LocalDate.now()
    }

    constructor(
        nameProduct: String,
        category: String,
        description: String,
        price: Double,
        isOnStorage: Boolean,
        amount: Int,
        deliveryDate: LocalDate
    ) : this() {
        this.nameProduct = nameProduct
        this.category = category
        this.description = description
        this.price = price
        this.isOnStorage = isOnStorage
        this.amount = amount
        this.deliveryDate = deliveryDate
    }

    constructor(nameProduct: String, category: String, description: String) : this() {
        this.nameProduct = nameProduct
        this.category = category
        this.description = description
    }

    override fun toString(): String {
        return """
            |Id: $id
            |Name: $nameProduct
            |Category: $category
            |Description: $description
            |Price: $price
            |Is on storage: $isOnStorage
            |Amount: $amount
            |Delivery date: $deliveryDate
            |
        """.trimMargin()
    }
}
