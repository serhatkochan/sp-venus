package mahrek.spVenus.core.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistrictDto {
    private Integer districtId;
    private String districtName;
    private String provinceName;
    private String provinceNo;
}
