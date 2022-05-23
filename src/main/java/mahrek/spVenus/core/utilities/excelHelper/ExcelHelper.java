package mahrek.spVenus.core.utilities.excelHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import mahrek.spVenus.entities.concretes.dtos.response.StudentListExcelResponseDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Student No", "Email", "First Name", "Last Name" , "Phone Number", "District Name", "Province Name", "Role", "Is Active"};
    static String SHEET = "Tutorials";
    public static ByteArrayInputStream studentToExcel(List<StudentListExcelResponseDto> studentListExcelResponseDto) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }
            int rowIdx = 1;
            for (StudentListExcelResponseDto student : studentListExcelResponseDto) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(student.getStudentNo());
                row.createCell(1).setCellValue(student.getEmail());
                row.createCell(2).setCellValue(student.getFirstName());
                row.createCell(3).setCellValue(student.getLastName());
                row.createCell(4).setCellValue(student.getPhoneNumber());
                row.createCell(5).setCellValue(student.getDistrictName());
                row.createCell(6).setCellValue(student.getProvinceName());
                row.createCell(7).setCellValue(student.getRole());
                row.createCell(8).setCellValue(student.getIsActive()?"Active":"Not Active");
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}