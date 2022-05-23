package mahrek.spVenus.dataAccess;

import mahrek.spVenus.core.entities.dtos.response.CurrentUserResponseDto;
import mahrek.spVenus.entities.concretes.Student;
import mahrek.spVenus.entities.concretes.dtos.request.StudentFilterRequestDto;
import mahrek.spVenus.entities.concretes.dtos.response.CurrentStudentResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.FindByStudentResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListExcelResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListResponseDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("Select new mahrek.spVenus.entities.concretes.dtos.response.StudentListResponseDto"
            + "(s.studentId, s.studentNo, u.userId, u.email, u.firstName, u.lastName, u.phoneNumber"
            + ", u.district.districtId, u.district.districtName, u.district.province.provinceNo, u.district.province.provinceName"
            + ", u.role, u.isActive)"
            + " From Student s"
            + " Inner Join s.user u"
            + " where"
            + " (:#{#studentFilterRequestDto.studentNo} is null OR s.studentNo LIKE %:#{#studentFilterRequestDto.studentNo}%)"
            + " AND (:#{#studentFilterRequestDto.email} is null OR u.email LIKE %:#{#studentFilterRequestDto.email}%)"
            + " AND (:#{#studentFilterRequestDto.firstName} is null OR u.firstName LIKE %:#{#studentFilterRequestDto.firstName}%)"
            + " AND (:#{#studentFilterRequestDto.lastName} is null OR u.lastName LIKE %:#{#studentFilterRequestDto.lastName}%)"
            + " AND (:#{#studentFilterRequestDto.phoneNumber} is null OR u.phoneNumber LIKE %:#{#studentFilterRequestDto.phoneNumber}%)"
            + " AND (:#{#studentFilterRequestDto.districtId} is null OR u.district.districtId = :#{#studentFilterRequestDto.districtId})"
            + " AND (:#{#studentFilterRequestDto.provinceNo} is null OR u.district.province.provinceNo = :#{#studentFilterRequestDto.provinceNo})")
    List<StudentListResponseDto> findByFilters(@Param("studentFilterRequestDto") StudentFilterRequestDto studentFilterRequestDto);
    @Query("Select new mahrek.spVenus.entities.concretes.dtos.response.StudentListExcelResponseDto"
            + "( s.studentNo, u.email, u.firstName, u.lastName, u.phoneNumber"
            + ", u.district.districtName, u.district.province.provinceName"
            + ", u.role, u.isActive)"
            + " From Student s"
            + " Inner Join s.user u"
            + " where"
            + " (:#{#studentFilterRequestDto.studentNo} is null OR s.studentNo LIKE %:#{#studentFilterRequestDto.studentNo}%)"
            + " AND (:#{#studentFilterRequestDto.email} is null OR u.email LIKE %:#{#studentFilterRequestDto.email}%)"
            + " AND (:#{#studentFilterRequestDto.firstName} is null OR u.firstName LIKE %:#{#studentFilterRequestDto.firstName}%)"
            + " AND (:#{#studentFilterRequestDto.lastName} is null OR u.lastName LIKE %:#{#studentFilterRequestDto.lastName}%)"
            + " AND (:#{#studentFilterRequestDto.phoneNumber} is null OR u.phoneNumber LIKE %:#{#studentFilterRequestDto.phoneNumber}%)"
            + " AND (:#{#studentFilterRequestDto.districtId} is null OR u.district.districtId = :#{#studentFilterRequestDto.districtId})"
            + " AND (:#{#studentFilterRequestDto.provinceNo} is null OR u.district.province.provinceNo = :#{#studentFilterRequestDto.provinceNo})")
    List<StudentListExcelResponseDto> findByExcelExport(@Param("studentFilterRequestDto") StudentFilterRequestDto studentFilterRequestDto);
    @Query("Select new mahrek.spVenus.entities.concretes.dtos.response.CurrentStudentResponseDto"
            + "(s.studentId, s.studentNo, u.userId, u.email, u.firstName, u.lastName, u.phoneNumber"
            + ", u.district.districtId, u.district.districtName, u.district.province.provinceNo, u.district.province.provinceName"
            + ", u.role, u.isActive)"
            + " From Student s"
            + " Inner Join s.user u"
            + " where u.email = ?1")
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
