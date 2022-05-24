package mahrek.spVenus.core.utilities.pdfHelper;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListExcelResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListResponseDto;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import com.lowagie.text.*;

public class StudentListPdfHelper {

    static String[] HEADERs = { "Student No", "Email", "First Name", "Last Name" , "Phone Number", "District Name", "Province Name", "Role", "Is Active"};

    private List<StudentListExcelResponseDto> studentListExcelResponseDto;

    public StudentListPdfHelper(List<StudentListExcelResponseDto> studentListExcelResponseDto){
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
            table.addCell(student.getIsActive()?"Active":"Not Active");
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
//
//        Paragraph p = new Paragraph("List of Student", font);
//        p.setAlignment(Paragraph.ALIGN_CENTER);
//
//        document.add(p);
       document.add(new Paragraph("List of student", font));

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f, 3.0f, 3.0f, 3.0f, 3.0f});
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
