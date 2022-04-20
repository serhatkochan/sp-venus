package mahrek.spVenus.core.entities;

import lombok.Data;
import mahrek.spVenus.entities.concretes.Student;
import org.hibernate.annotations.Where;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


//@Where(clause = "is_deleted != true")
@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
    @Column(name = "role")
    private String role;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "is_password_changed")
    private Boolean isPasswordChanged;

}
