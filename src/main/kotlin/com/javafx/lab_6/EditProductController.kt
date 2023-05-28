package com.javafx.lab_6

import com.javafx.lab_6.data.Product
import com.javafx.lab_6.data.Repository
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.DatePicker
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.lang.Boolean
import java.time.LocalDate

class EditProductController {
    private var _repository: Repository? = null
    private var _mainController: MainController? = null
    private var _product: Product? = null

    @FXML
    var nameProduct: TextField? = null

    @FXML
    var category: TextField? = null

    @FXML
    var description: TextField? = null

    @FXML
    var price: TextField? = null

    @FXML
    var isOnStorage: RadioButton? = null

    @FXML
    var amount: TextField? = null

    @FXML
    var deliveryDate: DatePicker? = null
    fun setMainController(mainController: MainController?) {
        _mainController = mainController
    }

    fun setRepository(repository: Repository?) {
        _repository = repository
    }

    fun setProduct(product: Product?) {
        _product = product
        fillFieldsWithData()
    }

    fun updateProduct(actionEvent: ActionEvent) {
        val nameProduct_ = nameProduct!!.text
        val category_ = category!!.text
        val description_ = description!!.text
        val price_ = price!!.text
        val isOnStorage_ = isOnStorage!!.isSelected.toString()
        val amount_ = amount!!.text
        val deliveryDate_ = deliveryDate!!.value.toString()
        _product!!.nameProduct = nameProduct_
        _product!!.category = category_
        _product!!.description = description_
        _product!!.price = price_.toDouble()
        _product!!.isOnStorage = Boolean.parseBoolean(isOnStorage_)
        _product!!.amount = amount_.toInt()
        _product!!.deliveryDate = LocalDate.parse(deliveryDate_)
        _repository!!.updateProduct(_product!!.id, _product!!)
        val source = actionEvent.source as Node
        val stage = source.scene.window as Stage
        _mainController!!.updateListsView()
        stage.close()
    }

    private fun fillFieldsWithData() {
        nameProduct!!.text = _product!!.nameProduct
        category!!.text = _product!!.category
        description!!.text = _product!!.description
        price!!.text = _product!!.price.toString()
        isOnStorage!!.isSelected = _product!!.isOnStorage
        amount!!.text = _product!!.amount.toString()
        deliveryDate!!.value = _product!!.deliveryDate
    }
}
