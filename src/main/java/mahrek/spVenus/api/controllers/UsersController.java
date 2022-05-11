package mahrek.spVenus.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import mahrek.spVenus.business.abstracts.UserService;
import mahrek.spVenus.core.entities.dtos.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/users")
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> singup(@RequestBody UserAddRequestDto userAddDto){
        return ResponseEntity.ok(userService.singup(userAddDto));
    }
    @PostMapping("/login")
    @CacheEvict("currentUser")
    public ResponseEntity<?> logIn(@RequestBody UserLoginRequestDto userLoginRequestDto){
        return ResponseEntity.ok(userService.login(userLoginRequestDto));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok(userService.logout());
    }
    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody UserForgetPasswordRequestDto userForgetPasswordRequestDto){
        return ResponseEntity.ok(userService.forgetPassword(userForgetPasswordRequestDto));
    }
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody UserChangePasswordRequestDto userChangePasswordRequestDto){
        return ResponseEntity.ok(userService.changePassword(userChangePasswordRequestDto));
    }
    @DeleteMapping("/deleteUser{userId}")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") Integer userId){
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
    @PutMapping("/updateUser{userId}")
    public ResponseEntity<?> updateUser(@RequestParam("userId") Integer userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        return ResponseEntity.ok(userService.updateUser(userId, userUpdateRequestDto));
    }
    @PutMapping("/activeChangeUser")
    public ResponseEntity<?> activeChangeUser(@RequestBody UserActiveRequestDto userActiveRequestDto){
        return ResponseEntity.ok(userService.activeChangeUser(userActiveRequestDto));
    }

    @GetMapping("/currentUser")
    public ResponseEntity<?> currentUser() {
        return ResponseEntity.ok(userService.currentUser());
    }

}
