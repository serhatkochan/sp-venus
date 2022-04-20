package mahrek.spVenus.core.dataAccess;

import mahrek.spVenus.core.entities.User;
import mahrek.spVenus.core.entities.dtos.response.CurrentUserResponseDto;
import mahrek.spVenus.core.entities.dtos.response.UserListResponseDto;
import mahrek.spVenus.core.entities.dtos.response.UserLoginResponseDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByUserId(Integer userId);
    @Query("Select new mahrek.spVenus.core.entities.dtos.response.UserListResponseDto"
            + "(u.userId, u.email, u.firstName, u.lastName, u.phoneNumber"
            + ", d.districtId, d.districtName, d.province.provinceNo, d.province.provinceName"
            + ", u.role, u.isActive)"
            + " From User u"
            + " Inner Join u.district d")
    List<UserListResponseDto> findByUserListDto();
    @Query("Select new mahrek.spVenus.core.entities.dtos.response.UserLoginResponseDto"
            + "(u.userId, u.email, u.firstName, u.lastName, u.phoneNumber"
            + ", d.districtId, d.districtName, d.province.provinceNo, d.province.provinceName"
            + ", u.role, u.role)" // last u.role = tmp jwt
            + " From User u"
            + " Inner Join u.district d"
            + " where u.email = ?1")
    UserLoginResponseDto findByEmailToUserLoginResponseDto(String email);
    @Query("Select new mahrek.spVenus.core.entities.dtos.response.CurrentUserResponseDto"
            + "(u.userId, u.email, u.firstName, u.lastName, u.phoneNumber"
            + ", d.districtId, d.districtName, d.province.provinceNo, d.province.provinceName"
            + ", u.role)" // last u.role = tmp jwt
            + " From User u"
            + " Inner Join u.district d"
            + " where u.email = ?1")
    CurrentUserResponseDto findByEmailToCurrentUserResponseDto(String email);
}
