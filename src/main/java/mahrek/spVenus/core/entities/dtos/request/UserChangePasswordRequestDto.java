package mahrek.spVenus.core.entities.dtos.request;

import lombok.Data;

@Data
public class UserChangePasswordRequestDto {
    private String email;
    private String password;
}
