package com.esprit.financialhub_desktop.services;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportExcelService {
    public static void exportToExcel(TableView<?> tableView, File file) throws IOException {
        Workbook workbook = WorkbookFactory.create(true); // Create a new Excel workbook

        Sheet sheet = workbook.createSheet("Table Data"); // Create a new sheet in the workbook

        // Get the table data
        ObservableList<?> items = tableView.getItems();

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < tableView.getColumns().size(); i++) {
            TableColumn<?, ?> column = tableView.getColumns().get(i);
            headerRow.createCell(i).setCellValue(column.getText());
        }

        // Create data rows
        for (int rowIndex = 0; rowIndex < items.size(); rowIndex++) {
            Row row = sheet.createRow(rowIndex + 1);
            for (int colIndex = 0; colIndex < tableView.getColumns().size(); colIndex++) {
                TableColumn<?, ?> column = tableView.getColumns().get(colIndex);
                Object cellValue = column.getCellData(rowIndex);
                if (cellValue != null) {
                    if (cellValue instanceof String) {
                        row.createCell(colIndex, CellType.STRING).setCellValue((String) cellValue);
                    } else if (cellValue instanceof Number) {
                        row.createCell(colIndex, CellType.NUMERIC).setCellValue(((Number) cellValue).doubleValue());
                    } else {
                        row.createCell(colIndex, CellType.STRING).setCellValue(cellValue.toString());
                    }
                }
            }
        }

        // Write the workbook to the file
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
