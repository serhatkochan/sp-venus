package mahrek.spVenus.api.controllers;

import mahrek.spVenus.business.abstracts.StudentService;
import mahrek.spVenus.entities.concretes.dtos.request.StudentAddRequestDto;
import mahrek.spVenus.entities.concretes.dtos.request.StudentUpdateRequestDto;
import mahrek.spVenus.entities.concretes.dtos.response.CurrentStudentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
