package mahrek.spVenus.core.entities.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Where;

@Data
@AllArgsConstructor
public class UserListResponseDto {
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer districtId;
    private String districtName;
    private String provinceNo;
    private String provinceName;
    private String role;
    private Boolean isActive;

}
