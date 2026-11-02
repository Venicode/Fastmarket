package org.example.Servicos.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.Servicos.print.Print;

import java.awt.print.PrinterException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreatePDF {
    private String title;
    private List<String> bodyList;
    private String nameFile;

    public void printPdf() throws IOException, DocumentException, PrinterException {
      Document document = new Document();
      PdfWriter.getInstance(document,new FileOutputStream("comprovante-venimarket.pdf"));
      document.open();
      document.add(new Paragraph(this.title));
      document.add(new Paragraph("--------------------"));
      document.add(new Paragraph("Comprovante de Venda"));
      document.add(new Paragraph("----------------------------"));
        for (String body: bodyList) {
            document.add(new Paragraph(""));
            document.add(new Paragraph(body));
        }

      document.close();
      document.setPageSize(PageSize.LETTER);
      document.setMargins(36, 72, 108, 180);
      document.setMarginMirroring(true);
        Print print = new Print();
        print.printPdf("comprovante-venimarket.pdf");
    }

}
