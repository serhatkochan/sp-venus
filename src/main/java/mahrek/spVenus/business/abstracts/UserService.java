package mahrek.spVenus.business.abstracts;


import mahrek.spVenus.core.entities.dtos.request.*;
import mahrek.spVenus.core.entities.dtos.response.CurrentUserResponseDto;
import mahrek.spVenus.core.entities.dtos.response.UserListResponseDto;
import mahrek.spVenus.core.entities.dtos.response.UserLoginResponseDto;
import mahrek.spVenus.core.utilities.results.DataResult;
import mahrek.spVenus.core.utilities.results.Result;

import java.util.List;

public interface UserService {
    DataResult<List<UserListResponseDto>> getAllUser();
    Result singup(UserAddRequestDto userAddRequestDto);
    DataResult<UserLoginResponseDto> login(UserLoginRequestDto userLoginRequestDto);
    Result logout();
    Result forgetPassword(UserForgetPasswordRequestDto userForgetPasswordRequestDto);
    Result changePassword(UserChangePasswordRequestDto userChangePasswordRequestDto);
    Result deleteUser(Integer userId);
    Result updateUser(Integer userId, UserUpdateRequestDto userUpdateRequestDto);
    Result activeChangeUser(UserActiveRequestDto userActiveRequestDto);
    DataResult<CurrentUserResponseDto> currentUser();

}
