package com.javafx.lab_6.data

import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDate
import java.time.ZoneId

class DataBaseRepository(private val dataBaseConnector: DataBaseConnector) : Repository {

    init {
        try {
            dataBaseConnector.getConnection().use { conn ->
                val tableCreateStr = """
                    CREATE TABLE IF NOT EXISTS Products (
                        id INT NOT NULL AUTO_INCREMENT,
                        NameProduct VARCHAR(50),
                        Category VARCHAR(50),
                        Description VARCHAR(50),
                        Price Double,
                        IsOnStorage SMALLINT,
                        Amount INT,
                        DeliveryDate Date,
                        PRIMARY KEY (id)
                    );
                """.trimIndent()
                val createTable = conn.createStatement()
                createTable.execute(tableCreateStr)
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    override fun getAll(): List<Product> {
        val products = ArrayList<Product>()
        try {
            dataBaseConnector.getConnection().use { connection ->
                val statement = connection.createStatement()
                val rs = statement.executeQuery("select * from Products")
                while (rs.next()) {
                    products.add(Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getBoolean(6),
                        rs.getInt(7),
                        rs.getDate(8).toLocalDate()
                    ))
                }
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return products
    }

    override fun getById(id: Int): Product? {
        var product: Product? = null
        try {
            dataBaseConnector.getConnection().use { connection ->
                val statement = connection.prepareStatement("select * from Products where id = ?")
                statement.setInt(1, id)
                val rs = statement.executeQuery()
                if (rs.next()) {
                    product = Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getBoolean(6),
                        rs.getInt(7),
                        rs.getDate(8).toLocalDate()
                    )
                }
            }
        } catch (exception: SQLException) {
            exception.printStackTrace()
        }
        return product
    }

    override fun getAllByCategory(category: String): List<Product> {
        val products = ArrayList<Product>()
        try {
            dataBaseConnector.getConnection().use { connection ->
                val statement = connection.prepareStatement("select * from Products where Category Like(?)")
                statement.setString(1, "%$category%")
                val rs = statement.executeQuery()
                while (rs.next()) {
                    products.add(Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getBoolean(6),
                        rs.getInt(7),
                        rs.getDate(8).toLocalDate()
                    ))
                }
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return products
    }

    override fun getProductsIsNotTheStorage(): List<Product> {
        val products = ArrayList<Product>()
        try {
            dataBaseConnector.getConnection().use { connection ->
                val statement = connection.prepareStatement("select * from Products where IsOnStorage = ?")
                statement.setBoolean(1, false)
                val rs = statement.executeQuery()
                while (rs.next()) {
                    products.add(Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getBoolean(6),
                        rs.getInt(7),
                        rs.getDate(8).toLocalDate()
                    ))
                }
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return products
    }

    override fun addProduct(product: Product): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.getConnection().use { conn ->
                val preparedStatement = conn.prepareStatement("INSERT INTO Products (NameProduct, Category, Description, Price, IsOnStorage, Amount, DeliveryDate) VALUES (?,?,?,?,?,?,?)")
                preparedStatement.setString(1, product.nameProduct)
                preparedStatement.setString(2, product.category)
                preparedStatement.setString(3, product.description)
                preparedStatement.setDouble(4, product.price)
                preparedStatement.setBoolean(5, product.isOnStorage)
                preparedStatement.setInt(6, product.amount)
                preparedStatement.setDate(
                    7,
                    java.sql.Date.valueOf(product.deliveryDate) // Convert to java.sql.Date
                )
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }

    override fun updateProduct(id: Int, product: Product): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.getConnection().use { conn ->
                val preparedStatement = conn.prepareStatement(
                    """
                    UPDATE Products 
                    SET NameProduct = ?, Category = ?, Description = ?, Price = ?, IsOnStorage = ?, Amount = ?, DeliveryDate = ?
                    WHERE id = ?
                    """.trimIndent()
                )
                preparedStatement.setString(1, product.nameProduct)
                preparedStatement.setString(2, product.category)
                preparedStatement.setString(3, product.description)
                preparedStatement.setDouble(4, product.price)
                preparedStatement.setBoolean(5, product.isOnStorage)
                preparedStatement.setInt(6, product.amount)
                preparedStatement.setDate(
                    7,
                    java.sql.Date.valueOf(product.deliveryDate) // Convert to java.sql.Date
                )
                preparedStatement.setInt(8, id)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }

    override fun deleteProduct(id: Int): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.getConnection().use { conn ->
                val preparedStatement = conn.prepareStatement("DELETE FROM Products WHERE id = ?")
                preparedStatement.setInt(1, id)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }
}
