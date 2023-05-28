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

class AddProductController {
    private var _repository: Repository? = null
    private var _mainController: MainController? = null

    fun set_mainController(_mainController: MainController?) {
        this._mainController = _mainController
    }

    fun set_repository(_repository: Repository?) {
        this._repository = _repository
    }

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
    fun addProductToFile(actionEvent: ActionEvent) {
        val nameProduct_ = nameProduct!!.text
        val category_ = category!!.text
        val description_ = description!!.text
        val price_ = price!!.text
        val isOnStorage_ = isOnStorage!!.text
        val amount_ = amount!!.text
        val deliveryDate_ = deliveryDate!!.value.toString()
        val newProduct = Product(
            nameProduct_,
            category_,
            description_,
            price_.toDouble(),
            Boolean.parseBoolean(isOnStorage_),
            amount_.toInt(),
            LocalDate.parse(deliveryDate_)
        )
        _repository!!.addProduct(newProduct)
        val source = actionEvent.source as Node
        val stage = source.scene.window as Stage
        _mainController!!.updateListsView()
        stage.close()
    }
}
