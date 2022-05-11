package mahrek.spVenus.core.entities;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Integer districtId;
    @Column(name = "district_name")
    private String districtName;
    @ManyToOne
    @JoinColumn(name = "province_no")
    @JsonIgnore
    private Province province;
}
