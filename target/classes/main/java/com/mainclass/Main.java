package com.mainclass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/query_generator.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        MainSceneController controller = new MainSceneController();

        loader.setController(controller);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(" (c) created and maintained by Svyat Shu");

        primaryStage.show();
    }
}
