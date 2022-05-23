package mahrek.spVenus.business.concretes;

import mahrek.spVenus.business.abstracts.StudentService;
import mahrek.spVenus.core.dataAccess.DistrictDao;
import mahrek.spVenus.core.dataAccess.UserDao;
import mahrek.spVenus.core.entities.User;
import mahrek.spVenus.core.entities.dtos.response.CurrentUserResponseDto;
import mahrek.spVenus.core.security.concretes.UserDetailsManager;
import mahrek.spVenus.core.utilities.converters.EntityDtoConverter;
import mahrek.spVenus.core.utilities.excelHelper.ExcelHelper;
import mahrek.spVenus.core.utilities.results.*;
import mahrek.spVenus.dataAccess.StudentDao;
import mahrek.spVenus.entities.concretes.Student;
import mahrek.spVenus.entities.concretes.dtos.request.StudentAddRequestDto;
import mahrek.spVenus.entities.concretes.dtos.request.StudentFilterRequestDto;
import mahrek.spVenus.entities.concretes.dtos.request.StudentUpdateRequestDto;
import mahrek.spVenus.entities.concretes.dtos.response.CurrentStudentResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.FindByStudentResponseDto;
import mahrek.spVenus.entities.concretes.dtos.response.StudentListResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StudentManager implements StudentService {
    @Autowired
    StudentDao studentDao;
    @Autowired
    UserDao userDao;
    @Autowired
    DistrictDao districtDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    private EntityDtoConverter<StudentAddRequestDto, User> studentAddRequestDtoToUserConverter = new EntityDtoConverter(User.class);
    private EntityDtoConverter<StudentUpdateRequestDto, User> studentUpdateRequestDtoToUserConverter = new EntityDtoConverter(User.class);


    @Override
    public DataResult<List<StudentListResponseDto>> findByStudentListResponseDto() {
        try{
            return new SuccessDataResult<List<StudentListResponseDto>>(studentDao.findByStudentListResponseDto());
        } catch (Exception ex) {
            return new ErrorDataResult<List<StudentListResponseDto>>("Bilinmeyen Bir Hata Oluştu");
        }
    }
    @Override
    public DataResult<List<StudentListResponseDto>> findByFilters(StudentFilterRequestDto studentFilterRequestDto){
        try {
//            return new SuccessDataResult<List<StudentListResponseDto>>("hata" +studentFilterRequestDto);
            return new SuccessDataResult<List<StudentListResponseDto>>(studentDao.findByFilters(studentFilterRequestDto));
        } catch (Exception ex){
            return new ErrorDataResult<List<StudentListResponseDto>>("Bilinmeyen Bir Hata Oluştu"+ex);
        }
    }
    @Override
    public DataResult<ByteArrayInputStream> exportToExcel(StudentFilterRequestDto studentFilterRequestDto){
        try {
//            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            ByteArrayInputStream in = ExcelHelper.studentToExcel(studentDao.findByExcelExport(studentFilterRequestDto));
            return new SuccessDataResult<ByteArrayInputStream>(in);
        } catch (Exception ex){
            return new ErrorDataResult<ByteArrayInputStream>("Bilinmeyen Bir Hata Oluştu"+ex);
        }
    }

    @Override
    @CacheEvict(value = "currentStudent", allEntries = true)
    public Result addStudent(StudentAddRequestDto studentAddRequestDto) {
        try {
            if(studentDao.existsByStudentNo(studentAddRequestDto.getStudentNo())){
                return new ErrorResult("Bu Student No Kullanılıyor");
            } else if(userDao.existsByEmail(studentAddRequestDto.getEmail())){
                return new ErrorResult("Bu Email Kullanılıyor");
            }
            User newUser = studentAddRequestDtoToUserConverter.convert(studentAddRequestDto);
            newUser.setPassword(passwordEncoder.encode(studentAddRequestDto.getStudentNo()));
            newUser.setIsPasswordChanged(true);
            newUser.setIsActive(studentAddRequestDto.getIsActive());
            userDao.save(newUser);
            Student newStudent = new Student();
            newStudent.setStudentNo(studentAddRequestDto.getStudentNo());
            newStudent.setUser(newUser);
            studentDao.save(newStudent);
            return new SuccessResult();
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Oluştu");
        }
    }

    @Override
    @CacheEvict(value = "currentStudent", allEntries = true)
    public Result updateStudent(Integer studentId, StudentUpdateRequestDto studentUpdateRequestDto) {
        try {
            Student oldStudent = studentDao.findByStudentId(studentId);
            if(Objects.isNull(oldStudent)){
                return new ErrorResult("Kullanıcı Bulunamadı");
            } else if(!oldStudent.getStudentNo().equals(studentUpdateRequestDto.getStudentNo())){
                // Eğer student No değiştirilmek isteniyor ise
                if(studentDao.existsByStudentNo(studentUpdateRequestDto.getStudentNo())){
                    return new ErrorResult("Bu Student No Kullanılıyor");
                }
            } else if(!oldStudent.getUser().getEmail().equals(studentUpdateRequestDto.getEmail())){
                // Eğer email değiştirilmek isteniyor ise
                if(userDao.existsByEmail(studentUpdateRequestDto.getEmail())){
                    return new ErrorResult("Bu Email Kullanılıyor");
                }
            }
            User updateUser = studentUpdateRequestDtoToUserConverter.convert(studentUpdateRequestDto);
            updateUser.setUserId(oldStudent.getUser().getUserId());
            updateUser.setPassword(oldStudent.getUser().getPassword());
            if(Objects.isNull(updateUser.getRole())){
                updateUser.setRole(oldStudent.getUser().getRole());
            }
            updateUser.setIsPasswordChanged(oldStudent.getUser().getIsPasswordChanged());
            updateUser.setIsActive(studentUpdateRequestDto.getIsActive());
//            updateUser.setDistrict(districtDao.findByDistrictId(studentUpdateRequestDto.getDistrictId()));
//            userDao.save(updateUser);
            Student updateStudent = new Student();
            updateStudent.setStudentId(studentId);
            updateStudent.setStudentNo(studentUpdateRequestDto.getStudentNo());
            updateStudent.setUser(updateUser);
            studentDao.save(updateStudent);
            return new SuccessResult();
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Oluştu");
        }
    }

    @Override
    @CacheEvict(value = "currentStudent", allEntries = true)
    public Result deleteStudent(Integer studentId) {
        try {
            studentDao.deleteById(studentId);
            return new SuccessResult();
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Oluştu");
        }
    }

    // @CacheEvict(value = "currentUser", allEntries = true)
    @Override
    @Cacheable(value = "currentStudent", key = "1")
    public DataResult<CurrentStudentResponseDto> currentStudent()  {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsManager userDetailsManager = (UserDetailsManager) auth.getPrincipal();
//            if(userDetailsManager.getRole().equals("1")){
//                return new SuccessDataResult<CurrentUserResponseDto>("Role 1");
//            }

            CurrentStudentResponseDto currentStudentResponseDto = studentDao.findByEmailToCurrentStudentResponseDto(userDetailsManager.getUsername());
            return new SuccessDataResult<CurrentStudentResponseDto>(currentStudentResponseDto);
        } catch (Exception ex){
            return new ErrorDataResult<CurrentStudentResponseDto>("Student Bilgisi Getirilemedi");
        }
    }

    @Override
    public DataResult<FindByStudentResponseDto> findByStudent(Integer studentId){
        try{
            return new SuccessDataResult<FindByStudentResponseDto>(studentDao.findByStudentIdToFindByStudentResponseDto(studentId));
        } catch (Exception ex){
            return new ErrorDataResult<FindByStudentResponseDto>("Student Bilgisi Getirilemedi");
        }
    }


}
