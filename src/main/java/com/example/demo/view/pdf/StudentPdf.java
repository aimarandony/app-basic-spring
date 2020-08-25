package com.example.demo.view.pdf;

import com.example.demo.domain.Student;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentPdf {

    private static final Logger logger = LoggerFactory.getLogger(StudentPdf.class);

    private static final Color DARK_COLOR = new Color(0, 36, 51);
    private static final String FONT_FAMILY = FontFactory.HELVETICA;

    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    String date_now = formatter.format(new Date());

    public ByteArrayOutputStream getListStudents(List<Student> studentList) {

        // ORIENTATION HORIZONTAL: PageSize.A4.rotate()
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfPTable table = new PdfPTable(4);
            table.setSpacingBefore(20);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 3, 3});

            Font headFont = FontFactory.getFont(FONT_FAMILY);
            headFont.setColor(new Color(255, 255, 255));
            PdfPCell hcell;

            hcell = new PdfPCell(new Phrase("N°", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(DARK_COLOR);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Nombre y Apellido", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(DARK_COLOR);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Ciudad", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(DARK_COLOR);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Género", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(DARK_COLOR);
            table.addCell(hcell);

            Font cellFont = new Font();
            cellFont.setColor(DARK_COLOR);

            for (Student student : studentList) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(student.getId().toString(), cellFont));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(student.getLastName() + ", " + student.getName(), cellFont));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(student.getCity().getName()), cellFont));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((student.getSex().equals("M")) ? "Masculino" : "Femenino", cellFont));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor((student.getSex().equals("M")) ? new Color(146, 255, 250) : new Color(255, 158, 253));
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();

            document.addTitle("ESTUDIANTES");

            Path pathPhoto = Paths.get("src/main/resources/static").resolve("logo_wolf.png").toAbsolutePath();
            Image photo = Image.getInstance(String.valueOf(pathPhoto));
            photo.scaleToFit(60, 60);

            Paragraph header = new Paragraph();
            header.add(new Chunk(photo, 0, -60));
            document.add(header);

            Paragraph header1 = new Paragraph("Usuario: Alejandro Carlos Mendoza",
                    FontFactory.getFont(FONT_FAMILY, 8, DARK_COLOR));
            header1.setAlignment(Element.ALIGN_RIGHT);
            document.add(header1);


            Paragraph header2 = new Paragraph("Fecha: " + date_now,
                    FontFactory.getFont(FONT_FAMILY, 8, DARK_COLOR));
            header2.setAlignment(Element.ALIGN_RIGHT);
            document.add(header2);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            Paragraph title = new Paragraph("Reportes Personalizados",
                    FontFactory.getFont(FONT_FAMILY, 25, Font.BOLD, DARK_COLOR));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subtitle = new Paragraph("Lista de Estudiantes",
                    FontFactory.getFont(FONT_FAMILY, 15, Font.UNDERLINE, DARK_COLOR));
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            document.add(table);

            PdfPTable tableFooter = new PdfPTable(1);
            tableFooter.setSpacingBefore(20);
            tableFooter.setWidthPercentage(100);

            Phrase footer = new Phrase("Texto con Borde",
                    FontFactory.getFont(FONT_FAMILY, 15, DARK_COLOR));

            PdfPCell cellFooter = new PdfPCell(footer);
            cellFooter.setPadding(20);
            cellFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellFooter.setVerticalAlignment(Element.ALIGN_CENTER);
            tableFooter.addCell(cellFooter);

            document.add(tableFooter);

            document.close();
        } catch (DocumentException | IOException ex) {
            logger.error("Error occurred: ", ex);
        }
        return out;
    }
}
