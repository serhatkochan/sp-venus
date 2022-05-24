package mahrek.spVenus.api.controllers;

import mahrek.spVenus.business.abstracts.StudentService;
import mahrek.spVenus.core.utilities.excelHelper.ExcelHelper;
import mahrek.spVenus.entities.concretes.dtos.request.StudentAddRequestDto;
import mahrek.spVenus.entities.concretes.dtos.request.StudentFilterRequestDto;
import mahrek.spVenus.entities.concretes.dtos.request.StudentUpdateRequestDto;
import mahrek.spVenus.entities.concretes.dtos.response.CurrentStudentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("api/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("")
    public ResponseEntity<?> getAllStudent(){
        return ResponseEntity.ok(studentService.findByStudentListResponseDto());
    }
    @PostMapping("/findByFilters")
    public ResponseEntity<?> findByFilters(@RequestBody  StudentFilterRequestDto studentFilterRequestDto){
        return ResponseEntity.ok(studentService.findByFilters(studentFilterRequestDto));
    }
//    @PostMapping("/exportToExcel")
//    public ResponseEntity<Resource> exportToExcel(@RequestBody StudentFilterRequestDto studentFilterRequestDto){
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String filename = "student-data.xlsx";
//        InputStreamResource file = new InputStreamResource(studentService.exportToExcel(studentFilterRequestDto).getData());
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(file);
//    }
    @PostMapping("/exportToExcel")
    public void exportToExcel(HttpServletResponse response, @RequestBody StudentFilterRequestDto studentFilterRequestDto) throws IOException {
//        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = "student-list-" + currentDateTime;

        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); //IMPORTANT FOR React.js content-disposition get Name
        response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName+".xlsx");



        ExcelHelper excelHelper = new ExcelHelper(studentService.exportToExcel(studentFilterRequestDto).getData());
        excelHelper.export(response);
    }
    @PostMapping("/addStudent")
    public ResponseEntity<?> addStudent(@RequestBody StudentAddRequestDto studentAddRequestDto){
        return ResponseEntity.ok(studentService.addStudent(studentAddRequestDto));
    }
    @PutMapping("/updateStudent{studentId}")
    public ResponseEntity<?> updateStudent(@RequestParam("studentId") Integer studentId, @RequestBody StudentUpdateRequestDto studentUpdateRequestDto){
        return ResponseEntity.ok(studentService.updateStudent(studentId, studentUpdateRequestDto));
    }
    @DeleteMapping("/deleteStudent{studentId}")
    public ResponseEntity<?> deleteStudent(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }
    @GetMapping("/currentStudent")
    public ResponseEntity<?> currentStudent(){ // Principal principal
        return ResponseEntity.ok(studentService.currentStudent());
    }
    @GetMapping("/findByStudenId{studentId}")
    public ResponseEntity<?> findByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentService.findByStudent(studentId));
    }

}
