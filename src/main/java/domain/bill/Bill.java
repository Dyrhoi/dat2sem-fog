package domain.bill;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import domain.carport.OrderMaterial;
import domain.material.dao.MaterialDAO;
import domain.order.Order;
import infrastructure.Database;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

public class Bill {

    public Bill() throws IOException {
    }

    public void generatePDF(Order order, HttpServletResponse resp) throws IOException {
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(resp.getOutputStream()));
        // Initialize document
        Document document = new Document(pdf);
        document.add(new Paragraph("Stykliste for ordre: " + order.getUuid()));
        Table table = new Table(UnitValue.createPercentArray(new float[]{40,10,50})).useAllAvailableWidth();
        tableSetup(table);
        addSpace(table);

        table.setFontSize(10);
        getOrderMaterials(order, table);

        document.add(table);
        document.close();
    }

    private void addTableHeader(Table table) {
        Stream.of("Navn", "Antal", "Beskrivelse")
            .forEach(columnTitle -> {
                Cell header = new Cell();
                header.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                header.setBorder(new SolidBorder(ColorConstants.BLACK, 0));
                header.add(new Paragraph(columnTitle));
                header.setHeight(14);
                header.setFontSize(12);
                table.addCell(header);
            });
    }

    public void addRow(Table table, String name, int quantity, String description) {
        table.addCell(name);
        table.addCell(String.valueOf(quantity));
        table.addCell(description);
        table.startNewRow();
    }

    public void tableSetup(Table table) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.COURIER);
        table.setFont(font);
        addTableHeader(table);
    }

    public void getOrderMaterials(Order order, Table table) {
        MaterialDAO md = new MaterialDAO(new Database());
        for (int i = 1; i <= 4; i++) {
            boolean desc = false;
            for (OrderMaterial orderMaterial : md.getOrderMaterials(order)) {
                if (orderMaterial.getConstructionMaterial().getMaterial_types_id() == i) {
                    if (!desc) {
                        addDescriptionHeader(table, orderMaterial.getConstructionMaterial().getMaterialType());
                        desc = true;
                    }
                    String name = orderMaterial.getConstructionMaterial().getName();
                    int quantity = orderMaterial.getQuantity();
                    String description = orderMaterial.getConstructionMaterial().getDescription();
                    addRow(table, name, quantity, description);
                }
            }
            addSpace(table);
        }
    }

    public void addDescriptionHeader(Table table, String description) {
        Paragraph p = new Paragraph().add(description).setBold().setFontSize(12);
        table.addCell(p);
        table.addCell("");
        table.addCell("");
    }

    public void addSpace(Table table) {
        for (int i = 0; i < 3; i++) {
            Cell blankCell = new Cell();
            blankCell.setBorder(Border.NO_BORDER);
            blankCell.setHeight(20);
            table.addCell(blankCell);
        }
    }
}
