package mahrek.spVenus.entities.concretes.dtos.response;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class FindByStudentResponseDto {
    private Integer studentId;
    private String studentNo;
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
