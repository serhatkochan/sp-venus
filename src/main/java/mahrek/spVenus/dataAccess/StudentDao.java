package mahrek.spVenus.dataAccess;

import mahrek.spVenus.core.entities.dtos.response.CurrentUserResponseDto;
import mahrek.spVenus.entities.concretes.Student;
import mahrek.spVenus.entities.concretes.dtos.response.CurrentStudentResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.FindByStudentResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListResponseDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentDao extends JpaRepository<Student, Integer> {
    Boolean existsByStudentNo(String studentNo);
    Student findByStudentId(Integer studentId);
    @Query("Select new mahrek.spVenus.entities.concretes.dtos.response.StudentListResponseDto"
            + "(s.studentId, s.studentNo, u.userId, u.email, u.firstName, u.lastName, u.phoneNumber"
            + ", u.district.districtId, u.district.districtName, u.district.province.provinceNo, u.district.province.provinceName"
            + ", u.role, u.isActive)"
            + " From Student s"
            + " Inner Join s.user u")
    List<StudentListResponseDto> findByStudentListResponseDto();
    @Query("Select new mahrek.spVenus.entities.concretes.dtos.response.CurrentStudentResponseDto"
            + "(s.studentId, s.studentNo, u.userId, u.email, u.firstName, u.lastName, u.phoneNumber"
            + ", u.district.districtId, u.district.districtName, u.district.province.provinceNo, u.district.province.provinceName"
            + ", u.role, u.isActive)"
            + " From Student s"
            + " Inner Join s.user u"
            + " where u.email = ?1")
    @Cacheable(value = "currentStudent", key = "1")
    CurrentStudentResponseDto findByEmailToCurrentStudentResponseDto(String email);
    @Query("Select new mahrek.spVenus.entities.concretes.dtos.response.FindByStudentResponseDto"
            + "(s.studentId, s.studentNo, u.userId, u.email, u.firstName, u.lastName, u.phoneNumber"
            + ", u.district.districtId, u.district.districtName, u.district.province.provinceNo, u.district.province.provinceName"
            + ", u.role, u.isActive)"
            + " From Student s"
            + " Inner Join s.user u"
            + " where s.studentId = ?1")
    FindByStudentResponseDto findByStudentIdToFindByStudentResponseDto(Integer studentId);

}
