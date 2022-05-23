package mahrek.spVenus.api.controllers;

import mahrek.spVenus.business.abstracts.StudentService;
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

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.core.io.Resource;

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
    @PostMapping("/exportToExcel")
    public ResponseEntity<Resource> exportToExcel(@RequestBody StudentFilterRequestDto studentFilterRequestDto){
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String filename = "student-data.xlsx";
        InputStreamResource file = new InputStreamResource(studentService.exportToExcel(studentFilterRequestDto).getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
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
