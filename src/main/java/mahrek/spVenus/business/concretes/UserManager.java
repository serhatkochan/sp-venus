package mahrek.spVenus.business.concretes;

import mahrek.spVenus.business.abstracts.UserService;
import mahrek.spVenus.core.dataAccess.UserDao;
import mahrek.spVenus.core.entities.User;
import mahrek.spVenus.core.entities.dtos.request.*;
import mahrek.spVenus.core.entities.dtos.response.CurrentUserResponseDto;
import mahrek.spVenus.core.entities.dtos.response.UserListResponseDto;
import mahrek.spVenus.core.entities.dtos.response.UserLoginResponseDto;
import mahrek.spVenus.core.security.concretes.UserDetailsManager;
import mahrek.spVenus.core.security.jwt.JwtUtils;
import mahrek.spVenus.core.utilities.converters.EntityDtoConverter;
import mahrek.spVenus.core.utilities.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;



    private EntityDtoConverter<UserAddRequestDto, User> userAddRequestDtoToUserConverter = new EntityDtoConverter(User.class);
    private EntityDtoConverter<UserUpdateRequestDto, User> userUpdateRequestDtoToUserConverter = new EntityDtoConverter(User.class);

    @Override
    public DataResult<List<UserListResponseDto>> getAllUser() {
        try {
            List<UserListResponseDto> userListResponseDtos = userDao.findByUserListDto();
            return new SuccessDataResult<List<UserListResponseDto>>(userListResponseDtos);
        } catch (Exception ex) {
            return new ErrorDataResult<List<UserListResponseDto>>("Bilinmeyen Bir Hata Olu??tu");
        }
    }

    @Override
    public Result singup(UserAddRequestDto userAddRequestDto) {
        try {
            // Email kullan??l??yor ise
            if (userDao.existsByEmail(userAddRequestDto.getEmail())) {
                return new ErrorResult( "Bu Email Kullan??l??yor");
            } else {
                User newUser = userAddRequestDtoToUserConverter.convert(userAddRequestDto);
                newUser.setPassword(passwordEncoder.encode("123456789")); // varsay??lan ??ifre
                newUser.setIsPasswordChanged(true); // ??ifresini s??f??rlamal??
                newUser.setIsActive(true); // ba??lang????ta aktif olsun
                userDao.save(newUser);
                return new SuccessResult();
            }
        } catch (Exception ex) {
            return new ErrorResult( "Bilinmeyen Bir Hata Olu??tu");
        }
    }

    @Override
    public DataResult<UserLoginResponseDto> login(UserLoginRequestDto userLoginRequestDto) {
        try{
            User user = userDao.findByEmail(userLoginRequestDto.getEmail());
            if(Objects.isNull(user)){
                return new ErrorDataResult<UserLoginResponseDto>("Kullan??c?? Bulunamad??");
            }
            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword()));
            } catch (Exception ex){
                return new ErrorDataResult<UserLoginResponseDto>("Yanl???? ??ifre");
            }
            if(!user.getIsActive()){
                return new ErrorDataResult<UserLoginResponseDto>("Kullan??c?? Aktif De??il");
            } else if(user.getIsPasswordChanged()){
                return new ErrorDataResult<UserLoginResponseDto>("Kullan??c?? ??ifresi De??i??tirilmeli");
            }

            String jwt = jwtUtils.generateJwtToken(user);
            UserLoginResponseDto userLoginResponseDto = userDao.findByEmailToUserLoginResponseDto(userLoginRequestDto.getEmail());
            userLoginResponseDto.setJwt(jwt);
            userDao.save(user);
            return new SuccessDataResult<UserLoginResponseDto>(userLoginResponseDto);
        } catch (Exception ex){
            return new ErrorDataResult<UserLoginResponseDto>("Bilinmeyen Bir Hata Olu??tu" + ex);
        }
    }

    @Override
    @CacheEvict(value = "currentStudent", allEntries = true)
    public Result logout() {
        return new SuccessResult();
    }

    @Override
    public Result forgetPassword(UserForgetPasswordRequestDto userForgetPasswordRequestDto) {
        try {
            User user = userDao.findByEmail(userForgetPasswordRequestDto.getEmail());
            if(Objects.isNull(user)){
                return new ErrorResult("Kullan??c?? Bulunamad??");
            }
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setIsPasswordChanged(true); // ??ifre de??i??tirmek zorunda olsun
            userDao.save(user);
            return new SuccessResult();
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Olu??tu");
        }
    }

    @Override
    public Result changePassword(UserChangePasswordRequestDto userChangePasswordRequestDto) {
        try{
            User user = userDao.findByEmail(userChangePasswordRequestDto.getEmail());
            if(Objects.isNull(user)){
                return new ErrorResult("Kullan??c?? Bulunamad??");
            } else if(!user.getIsPasswordChanged()){
                return new ErrorResult("??ifre De??i??tirilemez");
            }
            user.setPassword(passwordEncoder.encode(userChangePasswordRequestDto.getPassword()));
            user.setIsPasswordChanged(false); // ??ifre de??i??tirildi false
            userDao.save(user);
            return new SuccessResult();
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Olu??tu");
        }
    }

    @Override
    public Result deleteUser(Integer userId) {
        try {
            User user = userDao.findByUserId(userId);
            if(Objects.isNull(user)){
                return new ErrorResult("Kullan??c?? Bulunamad??");
            }
            user.setIsActive(false); // art??k aktif de??il
            userDao.save(user);
            return new SuccessResult("Kullan??c?? Silindi");
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Olu??tu");
        }
    }

    @Override
    public Result updateUser(Integer userId, UserUpdateRequestDto userUpdateRequestDto) {
        try {
            User oldUser = userDao.findByUserId(userId);
            if(Objects.isNull(oldUser)){
                return new ErrorResult("Kullan??c?? Bulunamad??");
            }
            User findUser = userDao.findByEmail(userUpdateRequestDto.getEmail());
            if(!Objects.isNull(findUser)){
                if(findUser.getUserId() != userId){
                    return new ErrorResult("Bu Email Kullan??l??yor");
                }
            }
            User updateUser = userUpdateRequestDtoToUserConverter.convert(userUpdateRequestDto);
            updateUser.setUserId(userId);
            updateUser.setPassword(oldUser.getPassword());
            updateUser.setIsPasswordChanged(oldUser.getIsPasswordChanged());
            updateUser.setIsActive(oldUser.getIsActive());
            userDao.save(updateUser);
            return new SuccessResult();
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Olu??tu");
        }
    }

    @Override // Kullan??c??n?? aktiflik durumu de??i??ir
    public Result activeChangeUser(UserActiveRequestDto userActiveRequestDto) {
        try {
            User user = userDao.findByEmail(userActiveRequestDto.getEmail());
            if(Objects.isNull(user)){
                return new ErrorResult("Kullan??c?? Bulunamad??");
            }
            user.setIsActive(!user.getIsActive());
            userDao.save(user);
            String result = user.getIsActive()?"Kullan??c?? Aktif Edildi":"Kullan??c?? Pasif Edildi";
            return new SuccessResult(result);
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Olu??tu");
        }
    }

    @Override // aktif kullan??c??y?? verir
    public DataResult<CurrentUserResponseDto> currentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsManager userDetailsManager = (UserDetailsManager) auth.getPrincipal();
//            if(userDetailsManager.getRole().equals("1")){
//                return new SuccessDataResult<CurrentUserResponseDto>("Role 1");
//            }
            CurrentUserResponseDto currentUserResponseDto = userDao.findByEmailToCurrentUserResponseDto(userDetailsManager.getUsername());
            return new SuccessDataResult<CurrentUserResponseDto>(currentUserResponseDto);
        } catch (Exception ex){
            return new ErrorDataResult<CurrentUserResponseDto>("Kullan??c?? Bilgisi Getirilemedi");
        }
    }


}
