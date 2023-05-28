package com.javafx.lab_6

import com.javafx.lab_6.data.Product
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.control.ListView
import javafx.stage.Stage
import com.javafx.lab_6.data.DataBaseConnector
import com.javafx.lab_6.data.DataBaseRepository
import com.javafx.lab_6.data.Repository
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class MainController : Initializable {
    @FXML
    lateinit var listProducts: ListView<*>

    @FXML
    lateinit var categoryCombo: ComboBox<*>

    private lateinit var repository: Repository

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        repository = DataBaseRepository(DataBaseConnector("InternetShopDB"))
        updateListsView()
    }

    fun updateListsView() {
        val products: List<Product?> = repository.getAll()
        val productsList: ObservableList<Product> = FXCollections.observableList(products)
        listProducts.items = productsList

        val categories: MutableList<String?> = products
            .mapTo(ArrayList()) { it?.category }
            .distinct()
            .toMutableList()
        categories.add("All")
        val categoryList: ObservableList<String> = FXCollections.observableList(categories)
        categoryCombo.items = categoryList
        categoryCombo.selectionModel.select(categories.size - 1)
    }


    @FXML
    fun addNewProduct(actionEvent: ActionEvent) {
        val newWindow = Stage()
        val loader = FXMLLoader(InternetShop::class.java.getResource(
            "add-product-form.fxml"
        ))
        val root: Parent = try {
            loader.load()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        newWindow.title = "Зареєструвати продукт"
        newWindow.scene = Scene(root, 520.0, 340.0)
        val secondController: AddProductController = loader.getController()
        secondController.set_repository(repository)
        secondController.set_mainController(this)
        newWindow.show()
    }

    fun filterByCategory(actionEvent: ActionEvent) {
        val category = categoryCombo.selectionModel.selectedItem as? String
        if (category != null) {
            val products: List<Product> = if ("All" == category) {
                repository.getAll().filterNotNull() ?: emptyList()
            } else {
                repository.getAllByCategory(category) ?: emptyList()
            }
            val productsList: ObservableList<Product> = FXCollections.observableList(products)
            listProducts.items = productsList
        }
    }



    @FXML
    fun deleteProduct(actionEvent: ActionEvent) {
        val toDelete = listProducts.selectionModel.selectedItem as Product
        println(toDelete)
        repository.deleteProduct(toDelete.id)
        updateListsView()
    }

    @FXML
    fun editProduct(actionEvent: ActionEvent) {
        val selectedProductCheck = listProducts.selectionModel.selectedItem
        if(selectedProductCheck != null) {
            val selectedProduct = selectedProductCheck as Product
            val newWindow = Stage()
            val loader = FXMLLoader(
                InternetShop::class.java.getResource(
                    "edit-product-form.fxml"
                )
            )
            val root: Parent = try {
                loader.load()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            newWindow.title = "Редагувати продукт"
            newWindow.scene = Scene(root, 520.0, 340.0)
            val editController: EditProductController = loader.getController()
            editController.setRepository(repository)
            editController.setMainController(this)
            editController.setProduct(selectedProduct) // Set the selected product for editing
            newWindow.show()
        }
    }

    fun getProductsIsNotTheStorage() {
        val products: List<Product> = repository.getProductsIsNotTheStorage()
        val productsList: ObservableList<Product> = FXCollections.observableList(products)
        listProducts.items = productsList
    }
}
