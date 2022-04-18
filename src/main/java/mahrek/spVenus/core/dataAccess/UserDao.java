package mahrek.spVenus.core.dataAccess;

import mahrek.spVenus.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByUserId(Integer userId);
}
