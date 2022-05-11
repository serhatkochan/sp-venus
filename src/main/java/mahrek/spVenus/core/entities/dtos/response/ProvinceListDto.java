package mahrek.spVenus.core.entities.dtos.response;

import lombok.*;
import mahrek.spVenus.core.entities.District;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ProvinceListDto {
    private String provinceName;
    private String provinceNo;
    private ArrayList<District> districts;
}
