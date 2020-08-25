package com.example.demo.view.excel;

import com.example.demo.domain.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class StudentExcel {

    private static final Logger logger = LoggerFactory.getLogger(StudentExcel.class);

    public ByteArrayInputStream getListStudents(List<Student> students) {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Sheet sheet = workbook.createSheet("Estudiantes");

            //HEADER
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Row headerRow = sheet.createRow(0);

            Cell cell = headerRow.createCell(0);
            cell.setCellValue("N°");
            cell.setCellStyle(headerCellStyle);

            cell = headerRow.createCell(1);
            cell.setCellValue("Nombre y Apellido");
            cell.setCellStyle(headerCellStyle);

            cell = headerRow.createCell(2);
            cell.setCellValue("Ciudad");
            cell.setCellStyle(headerCellStyle);

            cell = headerRow.createCell(3);
            cell.setCellValue("Género");
            cell.setCellStyle(headerCellStyle);

            //CONTENT
            CellStyle contentCellStyle = workbook.createCellStyle();
            contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
            contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            contentCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            contentCellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());

            for (int i=0; i<students.size(); i++){
                Row contentRow = sheet.createRow(i + 1);

                Cell contentCell = contentRow.createCell(0);
                contentCell.setCellValue(students.get(i).getId());
                contentCell.setCellStyle(contentCellStyle);

                contentCell = contentRow.createCell(1);
                contentCell.setCellValue(students.get(i).getLastName() + ", " + students.get(i).getName());
                contentCell.setCellStyle(contentCellStyle);

                contentCell = contentRow.createCell(2);
                contentCell.setCellValue(students.get(i).getCity().getName());
                contentCell.setCellStyle(contentCellStyle);

                contentCell = contentRow.createCell(3);
                contentCell.setCellValue((students.get(i).getSex() == "M") ? "Masculino" : "Femenino");
//                contentCellStyle.setFillForegroundColor(
//                        (students.get(i).getSex() == "M") ? IndexedColors.LIGHT_BLUE.getIndex() : IndexedColors.CORAL.getIndex());

                contentCell.setCellStyle(contentCellStyle);
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            workbook.write(out);
        } catch (IOException ex) {
            logger.error("Error occurred: ", ex);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
