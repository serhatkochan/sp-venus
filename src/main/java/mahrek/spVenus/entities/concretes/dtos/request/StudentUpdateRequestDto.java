package mahrek.spVenus.entities.concretes.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentUpdateRequestDto {
    private String studentNo;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String phoneNumber;
    private Integer districtId;
    private String role;
}
