package com.elephant.alerts;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfExporter {

    public static void exportRoutePlan(PathResult result, File file) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(doc, page);

            float margin = 50;
            float y = page.getMediaBox().getHeight() - margin;

            cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
            cs.beginText();
            cs.newLineAtOffset(margin, y);
            cs.showText("Predictive Early-Warning & Dynamic Response System");
            cs.endText();

            y -= 30;
            cs.setFont(PDType1Font.HELVETICA_BOLD, 13);
            cs.beginText();
            cs.newLineAtOffset(margin, y);
            cs.showText("Safe Route Plan");
            cs.endText();

            y -= 20;
            cs.setFont(PDType1Font.HELVETICA, 11);
            cs.beginText();
            cs.newLineAtOffset(margin, y);
            String ts = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            cs.showText("Generated at: " + ts);
            cs.endText();

            y -= 25;
            cs.beginText();
            cs.newLineAtOffset(margin, y);
            cs.showText(String.format("Total risk-weighted distance: %.2f units",
                    result.getTotalWeight()));
            cs.endText();

            y -= 25;
            cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
            cs.beginText();
            cs.newLineAtOffset(margin, y);
            cs.showText("Route segments:");
            cs.endText();

            y -= 20;
            cs.setFont(PDType1Font.HELVETICA, 11);

            int i = 1;
            for (GraphNode n : result.getPath()) {
                if (y < margin + 40) {
                    cs.endText();
                    cs.close();

                    page = new PDPage(PDRectangle.A4);
                    doc.addPage(page);
                    cs = new PDPageContentStream(doc, page);
                    y = page.getMediaBox().getHeight() - margin;
                    cs.setFont(PDType1Font.HELVETICA, 11);
                    cs.beginText();
                    cs.newLineAtOffset(margin, y);
                } else {
                    cs.beginText();
                    cs.newLineAtOffset(margin, y);
                }

                String line = String.format("%d. %s (%.4f, %.4f)",
                        i++, n.getName(), n.getLat(), n.getLng());
                cs.showText(line);
                cs.endText();

                y -= 18;
            }

            cs.close();
            doc.save(file);
        }
    }
}
