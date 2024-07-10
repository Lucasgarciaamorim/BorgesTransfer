package com.borges.transferenciaborges.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ExcelDataLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExcelController {

    @FXML
    private Button loadButton;

    @FXML
    private TableView<List<String>> tableView;

    private final ExcelDataLoader model;

    public ExcelController() {
        model = new ExcelDataLoader();
    }

    @FXML
    private void initialize() {
        loadButton.setOnAction(e -> loadExcelFile((Stage) loadButton.getScene().getWindow()));
    }

    private void loadExcelFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                List<List<String>> data = model.loadExcelData(file);
                updateTableView(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTableView(List<List<String>> data) {
        tableView.getColumns().clear();
        tableView.getItems().clear();

        if (!data.isEmpty()) {
            int numberOfColumns = data.get(0).size();
            for (int i = 0; i < numberOfColumns; i++) {
                TableColumn<List<String>, String> column = new TableColumn<>("Column " + (i + 1));
                final int colIndex = i;
                column.setCellValueFactory(cellData -> {
                    List<String> row = cellData.getValue();
                    if (colIndex >= 0 && colIndex < row.size()) {
                        return new SimpleStringProperty(row.get(colIndex));
                    } else {
                        return new SimpleStringProperty(""); // Retorna uma string vazia se o índice estiver fora do intervalo
                    }
                });
                tableView.getColumns().add(column);
            }

            for (List<String> rowData : data) {
                if (rowData.size() == numberOfColumns) {
                    tableView.getItems().add(rowData);
                } else {
                    System.err.println("Linha com tamanho incorreto: " + rowData);
                }
            }
        }
    }
}

