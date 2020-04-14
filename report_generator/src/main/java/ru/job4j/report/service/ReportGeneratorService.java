package ru.job4j.report.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import ru.job4j.person.domain.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ReportGeneratorService {

    /**
     * generates the table of persons in PDF.
     *
     * @param persons - list with persons.
     * @return PDF file.
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public File generateReport(List<Person> persons) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        File report = new File("report.pdf");
        PdfWriter.getInstance(document, new FileOutputStream(report));
        document.open();
        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);
        for (Person person : persons) {
            addRow(table, person);
        }
        document.add(table);
        document.close();
        return report;
    }

    /**
     * adds the table header
     *
     * @param table - the table
     */
    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Login", "Password")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * adds the row with person data.
     *
     * @param table  - the table.
     * @param person - the person.
     */
    private void addRow(PdfPTable table, Person person) {
        table.addCell(Integer.toString(person.getId()));
        table.addCell(person.getLogin());
        table.addCell(person.getPassword());
    }
}
