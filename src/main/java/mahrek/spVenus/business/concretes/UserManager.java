package mahrek.spVenus.business.concretes;

import mahrek.spVenus.business.abstracts.UserService;
import mahrek.spVenus.core.dataAccess.UserDao;
import mahrek.spVenus.core.entities.User;
import mahrek.spVenus.core.entities.dtos.request.*;
import mahrek.spVenus.core.entities.dtos.UserDto;
import mahrek.spVenus.core.entities.dtos.response.CurrentUserResponseDto;
import mahrek.spVenus.core.entities.dtos.response.UserListResponseDto;
import mahrek.spVenus.core.entities.dtos.response.UserLoginResponseDto;
import mahrek.spVenus.core.security.concretes.UserDetailsManager;
import mahrek.spVenus.core.security.jwt.JwtUtils;
import mahrek.spVenus.core.utilities.converters.EntityDtoConverter;
import mahrek.spVenus.core.utilities.results.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    private EntityDtoConverter<User, UserListResponseDto> userToUserListResponseDtoConverter = new EntityDtoConverter(UserListResponseDto.class);
    private EntityDtoConverter<UserAddRequestDto, User> userAddRequestDtoToUserConverter = new EntityDtoConverter(User.class);
    private EntityDtoConverter<User, UserLoginResponseDto> userToUserLoginResponseDtoConverter = new EntityDtoConverter(UserLoginResponseDto.class);
    private EntityDtoConverter<UserUpdateRequestDto, User> userUpdateRequestDtoToUserConverter = new EntityDtoConverter(User.class);
    private EntityDtoConverter<User, CurrentUserResponseDto> userToCurrentUserResponseDtoConverter = new EntityDtoConverter(CurrentUserResponseDto.class);

    @Override
    public DataResult<List<UserListResponseDto>> getAllUser() {
        try {
            List<UserListResponseDto> userListResponseDto = userDao.findAll()
                    .stream().map(userToUserListResponseDtoConverter::convert)
                    .collect(Collectors.toCollection(ArrayList::new));
            return new SuccessDataResult<List<UserListResponseDto>>(userListResponseDto, "Kullanıcılar Getirildi");
        } catch (Exception ex) {
            return new ErrorDataResult<List<UserListResponseDto>>("Başarısız");
        }
    }

    @Override
    public Result signup(UserAddRequestDto userAddRequestDto) {
        try {
            // Email kullanılıyor ise
            if (userDao.existsByEmail(userAddRequestDto.getEmail())) {
                return new ErrorResult( "Bu Email Kullanılıyor");
            } else {
                User newUser = userAddRequestDtoToUserConverter.convert(userAddRequestDto);
                newUser.setPassword(passwordEncoder.encode("123456789")); // varsayılan şifre
                newUser.setIsPasswordChanged(true); // şifresini sıfırlamalı
                newUser.setCreateDate(new Date());
                newUser.setUpdateDate(new Date());
                newUser.setIsDeleted(false); // başlangıçta silinmedi
                newUser.setIsActive(true); // başlangıçta aktif olsun
                userDao.save(newUser);
                return new SuccessResult("Kayıt Edildi");
            }

        } catch (Exception ex) {
            return new ErrorResult( "Başarısız");
        }
    }

    @Override
    public DataResult<UserLoginResponseDto> login(UserLoginRequestDto userLoginRequestDto) {
        try{
            User user = userDao.findByEmail(userLoginRequestDto.getEmail());
            if(Objects.isNull(user)){
                return new ErrorDataResult<UserLoginResponseDto>("Kullanıcı Bulunamadı");
            }
            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword()));
            } catch (Exception ex){
                return new ErrorDataResult<UserLoginResponseDto>("Yanlış Şifre");
            }
            if(!user.getIsActive()){
                return new ErrorDataResult<UserLoginResponseDto>("Kullanıcı Aktif Değil");
            } else if(user.getIsPasswordChanged()){
                return new ErrorDataResult<UserLoginResponseDto>("Kullanıcı Şifresi Değiştirilmeli");
            }

            String jwt = jwtUtils.generateJwtToken(user);
            UserLoginResponseDto userLoginResponseDto = userToUserLoginResponseDtoConverter.convert(user);
            userLoginResponseDto.setJwt(jwt);
            user.setLastLoginDate(new Date());
            userDao.save(user);
            return new SuccessDataResult<UserLoginResponseDto>(userLoginResponseDto, "Giriş Yapıldı");
        } catch (Exception ex){
            return new ErrorDataResult<UserLoginResponseDto>("Bilinmeyen Bir Hata Oluştu: " + ex);
        }
    }

    @Override
    public Result forgetPassword(UserForgetPasswordRequestDto userForgetPasswordRequestDto) {
        try {
            User user = userDao.findByEmail(userForgetPasswordRequestDto.getEmail());
            if(Objects.isNull(user)){
                return new ErrorResult("Kullanıcı Bulunamadı");
            }
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setIsPasswordChanged(true); // şifre değiştirmek zorunda olsun
            user.setUpdateDate(new Date());
            userDao.save(user);
            return new SuccessResult("Şifre Sıfırlandı");
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Oluştu");
        }
    }

    @Override
    public Result changePassword(UserChangePasswordRequestDto userChangePasswordRequestDto) {
        try{
            User user = userDao.findByEmail(userChangePasswordRequestDto.getEmail());
            if(Objects.isNull(user)){
                return new ErrorResult("Kullanıcı Bulunamadı");
            } else if(!user.getIsPasswordChanged()){
                return new ErrorResult("Şifre Değiştirilemez");
            }
            user.setPassword(passwordEncoder.encode(userChangePasswordRequestDto.getPassword()));
            user.setIsPasswordChanged(false); // şifre değiştirildi false
            user.setUpdateDate(new Date());
            userDao.save(user);
            return new SuccessResult("Şifre Değiştirildi");
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Oluştu");
        }
    }

    @Override
    public Result deleteUser(Integer userId) {
        try {
            User user = userDao.findByUserId(userId);
            if(Objects.isNull(user)){
                return new ErrorResult("Kullanıcı Bulunamadı");
            }
            user.setUpdateDate(new Date());
            user.setDeleteDate(new Date());
            user.setIsDeleted(true); // silindi
            user.setIsActive(false); // artık aktif değil
            userDao.save(user);
            return new SuccessResult("Kullanıcı Silindi");
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Oluştu");
        }
    }

    @Override
    public Result updateUser(Integer userId, UserUpdateRequestDto userUpdateRequestDto) {
        try {
            User oldUser = userDao.findByUserId(userId);
            if(Objects.isNull(oldUser)){
                return new ErrorResult("Kullanıcı Bulunamadı");
            }
            User findUser = userDao.findByEmail(userUpdateRequestDto.getEmail());
            if(!Objects.isNull(findUser)){
                if(findUser.getUserId() != userId){
                    return new ErrorResult("Bu Email Kullanımda");
                }
            }
            User newUser = userUpdateRequestDtoToUserConverter.convert(userUpdateRequestDto);
            newUser.setUserId(userId);
            newUser.setPassword(oldUser.getPassword());
            newUser.setIsDeleted(oldUser.getIsDeleted());
            newUser.setIsPasswordChanged(oldUser.getIsPasswordChanged());
            newUser.setCreateDate(oldUser.getCreateDate());
            newUser.setUpdateDate(new Date());
            newUser.setDeleteDate(oldUser.getDeleteDate());
            newUser.setLastLoginDate(oldUser.getLastLoginDate());
            userDao.save(newUser);
            return new SuccessResult("Kullanıcı Güncellendi");
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Oluştu");
        }
    }

    @Override
    public Result activeChangeUser(UserActiveRequestDto userActiveRequestDto) {
        try {
            User user = userDao.findByEmail(userActiveRequestDto.getEmail());
            if(Objects.isNull(user)){
                return new ErrorResult("Kullanıcı Bulunamadı");
            }
            user.setIsActive(!user.getIsActive());
            user.setUpdateDate(new Date());
            userDao.save(user);
            String result = user.getIsActive()?"Kullanıcı Aktif Edildi":"Kullanıcı Pasif Edildi";
            return new SuccessResult(result);
        } catch (Exception ex){
            return new ErrorResult("Bilinmeyen Bir Hata Oluştu");
        }
    }

    @Override
    public DataResult<CurrentUserResponseDto> currentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsManager userDetailsManager = (UserDetailsManager) auth.getPrincipal();
            User user = userDao.findByEmail(userDetailsManager.getUsername());
            CurrentUserResponseDto currentUserResponseDto = userToCurrentUserResponseDtoConverter.convert(user);
            return new SuccessDataResult<CurrentUserResponseDto>(currentUserResponseDto);
        } catch (Exception ex){
            return new ErrorDataResult<CurrentUserResponseDto>("Bilinmeyen Bir Hata Oluştu: " + ex);
        }
    }
}
