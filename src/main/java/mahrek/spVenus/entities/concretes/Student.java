package mahrek.spVenus.entities.concretes;

import lombok.Data;
import mahrek.spVenus.core.entities.User;

import javax.persistence.*;

@Entity
@Data
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name ="student_no")
    private String studentNo;
    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

}
