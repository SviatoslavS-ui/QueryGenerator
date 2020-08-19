package com.mainclass;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import libs.CsvParser;
import libs.ExcelParser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class MainSceneController {

    protected ExcelParser excelParser;
    protected CsvParser csvParser;
    protected String resultString;

    @FXML
    public void parseButtonClicked() {
        if (xlsxButton.isSelected()) {
            System.out.println("Translation button pressed ... Excel parser executing ...");
            excelParser = new ExcelParser();
            try {
                resultString = excelParser.parse(file_name.getText());
                output_area.setText(resultString);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Couldn't open a source file");
                output_area.setText("Couldn't open a source file");
            }
        } else {
            System.out.println("Translation button pressed ... CSV parser executing ...");
            csvParser = new CsvParser();
            try {
                resultString = csvParser.parse(file_name.getText());
                output_area.setText(resultString);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Couldn't open a source file");
                output_area.setText("Couldn't open a source file");
            }
        }
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
    @FXML
    public RadioButton xlsxButton;
    @FXML
    public RadioButton csvButton;
}
