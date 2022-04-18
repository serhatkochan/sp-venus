package mahrek.spVenus.core.security.concretes;

import lombok.extern.slf4j.Slf4j;
import mahrek.spVenus.core.dataAccess.UserDao;
import mahrek.spVenus.core.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceManager implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userDao.findByEmail(email);
        return new UserDetailsManager(user);
//        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
    }
}
