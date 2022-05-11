package mahrek.spVenus.business.abstracts;

import mahrek.spVenus.core.entities.Province;
import mahrek.spVenus.core.entities.dtos.ProvinceDto;
import mahrek.spVenus.core.entities.dtos.response.ProvinceListDto;
import mahrek.spVenus.core.utilities.results.DataResult;

import java.util.List;

public interface ProvinceService {
    DataResult<List<ProvinceDto>> getAllProvinceDto();
    DataResult<List<Province>> getAllProvinceListDto();
}
