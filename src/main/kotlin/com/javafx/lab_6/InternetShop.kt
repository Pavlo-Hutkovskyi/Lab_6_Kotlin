package com.javafx.lab_6

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class InternetShop : Application() {

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(InternetShop::class.java.getResource("main-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 520.0, 340.0)
        stage.title = "Internet Shop"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(InternetShop::class.java)
}