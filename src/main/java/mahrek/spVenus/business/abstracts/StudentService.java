package mahrek.spVenus.business.abstracts;

import mahrek.spVenus.core.utilities.results.DataResult;
import mahrek.spVenus.core.utilities.results.Result;
import mahrek.spVenus.entities.concretes.dtos.request.StudentAddRequestDto;
import mahrek.spVenus.entities.concretes.dtos.request.StudentFilterRequestDto;
import mahrek.spVenus.entities.concretes.dtos.request.StudentUpdateRequestDto;
import mahrek.spVenus.entities.concretes.dtos.response.CurrentStudentResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.FindByStudentResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListExcelResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListResponseDto;
import org.springframework.cache.annotation.Cacheable;

import java.io.ByteArrayInputStream;
import java.util.List;


public interface StudentService {
    DataResult<List<StudentListResponseDto>> findByStudentListResponseDto();
    DataResult<List<StudentListResponseDto>> findByFilters(StudentFilterRequestDto studentFilterRequestDto);
    DataResult<List<StudentListExcelResponseDto>> exportToExcel(StudentFilterRequestDto studentFilterRequestDto);
    Result addStudent(StudentAddRequestDto studentAddRequestDto);
    Result updateStudent(Integer studentId, StudentUpdateRequestDto studentUpdateRequestDto);
    Result deleteStudent(Integer studentId);
    DataResult<CurrentStudentResponseDto> currentStudent();
    DataResult<FindByStudentResponseDto> findByStudent(Integer studentId);
}
