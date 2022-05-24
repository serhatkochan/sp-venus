package mahrek.spVenus.api.controllers;

import com.lowagie.text.DocumentException;
import mahrek.spVenus.business.abstracts.StudentService;
import mahrek.spVenus.entities.concretes.dtos.request.StudentAddRequestDto;
import mahrek.spVenus.entities.concretes.dtos.request.StudentFilterRequestDto;
import mahrek.spVenus.entities.concretes.dtos.request.StudentUpdateRequestDto;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListExcelResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;

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
    public ResponseEntity<?> exportToExcel(HttpServletResponse response, @RequestBody StudentFilterRequestDto studentFilterRequestDto) throws IOException {
        return ResponseEntity.ok(studentService.exportToExcel(response, studentFilterRequestDto));
    }
    @PostMapping("/exportToPdf")
    public ResponseEntity<?> exportToPDF(HttpServletResponse response, @RequestBody StudentFilterRequestDto studentFilterRequestDto) throws DocumentException, IOException {
        return ResponseEntity.ok(studentService.exportToPdf(response, studentFilterRequestDto));
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
