package mahrek.spVenus.core.utilities.excelHelper;
import java.io.IOException;
import java.util.List;

import mahrek.spVenus.entities.concretes.dtos.response.StudentListExcelResponseDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class StudentListExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Student No", "Email", "First Name", "Last Name" , "Phone Number", "District Name", "Province Name", "Role", "Is Active"};
    static String SHEET = "Students";

    private Workbook workbook;
    private Sheet sheet;
    private List<StudentListExcelResponseDto> studentListExcelResponseDto;

    public StudentListExcelHelper(List<StudentListExcelResponseDto> studentListExcelResponseDto){
        this.studentListExcelResponseDto = studentListExcelResponseDto;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(SHEET);
    }

    private void writeHeaderRow(){
        Row headerRow = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        for (int col = 0; col < HEADERs.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(HEADERs[col]);
            cell.setCellStyle(style);
        }
    }
    private void writeDataRows(){
        int rowIdx = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (StudentListExcelResponseDto student : studentListExcelResponseDto) {
            Row row = sheet.createRow(rowIdx++);


            Cell cell = row.createCell(0);
            cell.setCellValue(student.getStudentNo());
            sheet.autoSizeColumn(0);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(student.getEmail());
            sheet.autoSizeColumn(1);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(student.getFirstName());
            sheet.autoSizeColumn(2);
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(student.getLastName());
            sheet.autoSizeColumn(3);
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(student.getPhoneNumber());
            sheet.autoSizeColumn(4);
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(student.getDistrictName());
            sheet.autoSizeColumn(5);
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(student.getProvinceName());
            sheet.autoSizeColumn(6);
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(student.getRole());
            sheet.autoSizeColumn(7);
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue(student.getIsActive()?"Active":"Not Active");
            sheet.autoSizeColumn(8);
            cell.setCellStyle(style);
        }
    }
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();
        ServletOutputStream outputStream =  response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}