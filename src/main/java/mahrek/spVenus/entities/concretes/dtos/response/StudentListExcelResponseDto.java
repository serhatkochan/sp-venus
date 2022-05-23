package mahrek.spVenus.entities.concretes.dtos.response;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class StudentListExcelResponseDto {
    private String studentNo;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String districtName;
    private String provinceName;
    private String role;
    private Boolean isActive;
}
