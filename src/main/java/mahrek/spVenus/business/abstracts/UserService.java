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
    Result signUp(UserAddRequestDto userAddRequestDto);
    DataResult<UserLoginResponseDto> logIn(UserLoginRequestDto userLoginRequestDto);
    Result logOut();
    Result forgetPassword(UserForgetPasswordRequestDto userForgetPasswordRequestDto);
    Result changePassword(UserChangePasswordRequestDto userChangePasswordRequestDto);
    Result deleteUser(Integer userId);
    Result updateUser(Integer userId, UserUpdateRequestDto userUpdateRequestDto);
    Result activeChangeUser(UserActiveRequestDto userActiveRequestDto);
    DataResult<CurrentUserResponseDto> currentUser();

}
