package mahrek.spVenus.core.utilities.pdfHelper;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListExcelResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListResponseDto;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.lowagie.text.*;
import org.springframework.core.io.ClassPathResource;

public class StudentListPdfHelper {

    static String[] HEADERs = {"Student No", "Email", "First Name", "Last Name", "Phone Number", "District Name", "Province Name", "Role", "Is Active"};
    static String[] HEADER4 = {"Student No", "Email", "First Name", "Last Name"};
    static String[] HEADER5 = {"Phone Number", "District Name", "Province Name", "Role", "Is Active"};

    private List<StudentListExcelResponseDto> studentListExcelResponseDto;

    public StudentListPdfHelper(List<StudentListExcelResponseDto> studentListExcelResponseDto) {
        this.studentListExcelResponseDto = studentListExcelResponseDto;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        for (int col = 0; col < HEADERs.length; col++) {
            cell.setPhrase(new Phrase(HEADERs[col], font));
            table.addCell(cell);
        }
    }

    private void writeTableHeader4(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        for (int col = 0; col < HEADER4.length; col++) {
            cell.setPhrase(new Phrase(HEADER4[col], font));
            table.addCell(cell);
        }
    }

    private void writeTableData4(PdfPTable table) {
        for (StudentListExcelResponseDto student : studentListExcelResponseDto) {
            table.addCell(student.getStudentNo());
            table.addCell(student.getEmail());
            table.addCell(student.getFirstName());
            table.addCell(student.getLastName());
        }
    }

    private void writeTableHeader5(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        for (int col = 0; col < HEADER5.length; col++) {
            cell.setPhrase(new Phrase(HEADER5[col], font));
            table.addCell(cell);
        }
    }

    private void writeTableData5(PdfPTable table) {
        for (StudentListExcelResponseDto student : studentListExcelResponseDto) {
            table.addCell(student.getPhoneNumber());
            table.addCell(student.getDistrictName());
            table.addCell(student.getProvinceName());
            table.addCell(student.getRole());
            table.addCell(student.getIsActive() ? "Active" : "Not Active");
        }
    }

    private void writeTableData(PdfPTable table) {
        for (StudentListExcelResponseDto student : studentListExcelResponseDto) {
            table.addCell(student.getStudentNo());
            table.addCell(student.getEmail());
            table.addCell(student.getFirstName());
            table.addCell(student.getLastName());
            table.addCell(student.getPhoneNumber());
            table.addCell(student.getDistrictName());
            table.addCell(student.getProvinceName());
            table.addCell(student.getRole());
            table.addCell(student.getIsActive() ? "Active" : "Not Active");
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        File file = new ClassPathResource("static/images/mahrek-logo.png").getFile();
        Image image = Image.getInstance(file.getPath());
        image.setAlignment(Image.ALIGN_CENTER);
//        image.setUrl(new URL("https://www.mahrek.com.tr/"));
        document.add(image);


//        Font fontListOfStudent = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        fontListOfStudent.setSize(18);
//        fontListOfStudent.setColor(Color.RED);
//        Paragraph paragraphListOfStudent = new Paragraph("List of student", fontListOfStudent);
//        paragraphListOfStudent.setFont(fontListOfStudent);
//        document.add(paragraphListOfStudent);

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 1.5f, 3.0f, 3.0f, 3.0f, 3.0f});
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);


        document.newPage();
        document.add(image);

        PdfPTable table2 = new PdfPTable(4);
        table2.setWidthPercentage(100f);
        table2.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f});
        table2.setSpacingBefore(15);

        writeTableHeader4(table2);
        writeTableData4(table2);
        document.add(table2);

        PdfPTable table3 = new PdfPTable(5);
        table3.setWidthPercentage(100f);
        table3.setWidths(new float[]{2.5f, 3.0f, 3.0f, 1.5f, 1.5f});
        table3.setSpacingBefore(15);

        writeTableHeader5(table3);
        writeTableData5(table3);
        document.add(table3);

        document.addAuthor("Mahrek Tech");
        document.close();

    }
}
