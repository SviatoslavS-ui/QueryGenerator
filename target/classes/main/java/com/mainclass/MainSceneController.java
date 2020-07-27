package com.mainclass;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import libs.ExcelParser;

public class MainSceneController {
    protected ExcelParser excelParser;
    protected String resultString;


    @FXML
    public void parseButtonClicked() {
        System.out.println("Translation button pressed ...");
        excelParser = new ExcelParser();
        resultString = excelParser.parse(file_name.getText());
        output_area.setText(resultString);
    }

    @FXML
    public TextField file_name;
    @FXML
    public TextArea output_area;
}
