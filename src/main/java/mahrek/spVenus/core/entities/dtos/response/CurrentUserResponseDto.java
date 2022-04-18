package mahrek.spVenus.core.entities.dtos.response;

import lombok.Data;

@Data
public class CurrentUserResponseDto {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
}
