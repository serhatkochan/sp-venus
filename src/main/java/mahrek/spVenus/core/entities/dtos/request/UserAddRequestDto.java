package mahrek.spVenus.core.entities.dtos.request;

import lombok.Data;

@Data
public class UserAddRequestDto {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
}
