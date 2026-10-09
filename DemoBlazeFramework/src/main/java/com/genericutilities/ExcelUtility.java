package com.genericutilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

    public String getDataFromExcel(
            String sheetName,
            int rowNumber,
            int cellNumber) throws IOException {

        FileInputStream fis =
                new FileInputStream(
                        "‪C:/Users/lenovo/Documents/DemoBlaze_Tetcase.xlsx");

        Workbook workbook =
                WorkbookFactory.create(fis);

        String data =
                workbook.getSheet(sheetName)
                        .getRow(rowNumber)
                        .getCell(cellNumber)
                        .toString();

        workbook.close();
        fis.close();

        return data;
    }


    public int getRowCount(
            String sheetName) throws IOException {

        FileInputStream fis =
                new FileInputStream(
                        "‪C:/Users/lenovo/Documents/DemoBlaze_Tetcase.xlsx");

        Workbook workbook =
                WorkbookFactory.create(fis);

        int rowCount =
                workbook.getSheet(sheetName)
                        .getLastRowNum();

        workbook.close();
        fis.close();

        return rowCount;
    }


    public void setDataIntoExcel(
            String sheetName,
            int rowNumber,
            int cellNumber,
            String data) throws IOException {

        String path =
                "‪C:/Users/lenovo/Documents/DemoBlaze_Tetcase.xlsx";

        FileInputStream fis =
                new FileInputStream(path);

        Workbook workbook =
                WorkbookFactory.create(fis);

        workbook.getSheet(sheetName)
                .getRow(rowNumber)
                .createCell(cellNumber)
                .setCellValue(data);

        fis.close();

        FileOutputStream fos =
                new FileOutputStream(path);

        workbook.write(fos);

        workbook.close();
        fos.close();
    }
}