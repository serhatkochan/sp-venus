package mahrek.spVenus.core.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id")
    private Integer provinceId;
    @Column(name = "province_name")
    private String provinceName;
    @Column(name = "province_no")
    private String provinceNo;

}
