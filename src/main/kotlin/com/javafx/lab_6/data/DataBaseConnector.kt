package com.javafx.lab_6.data

import java.lang.reflect.InvocationTargetException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DataBaseConnector(private val dataBaseName: String) {
    private val dataBaseUrl: String = "jdbc:h2:file:./$dataBaseName"
    private val dataBaseUser: String = "admin"
    private val dataBasePassword: String = ""
    private val driverClass: String = "org.h2.Driver"

    fun testDriver(): Boolean {
        return try {
            Class.forName(driverClass)
                .getDeclaredConstructor().newInstance()
            true
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            false
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            false
        } catch (e: InstantiationException) {
            e.printStackTrace()
            false
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            false
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            false
        }
    }

    @Throws(SQLException::class)
    fun getConnection(): Connection {
        return DriverManager.getConnection(dataBaseUrl, dataBaseUser, dataBasePassword)
    }
}
