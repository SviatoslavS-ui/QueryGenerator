package com.mainclass;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import libs.ExcelParser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

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
    public void copyToClipboardButton() {
        String clipboardString = output_area.getText();
        StringSelection stringSelection = new StringSelection(clipboardString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        System.out.println("Copy to clipboard button pressed ...");
    }

    @FXML
    public TextField file_name;
    @FXML
    public TextArea output_area;
}
