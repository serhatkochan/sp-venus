package mahrek.spVenus.business.abstracts;

import mahrek.spVenus.core.entities.District;
import mahrek.spVenus.core.entities.dtos.DistrictDto;
import mahrek.spVenus.core.utilities.results.DataResult;

import java.util.List;

public interface DistrictService {
    DataResult<List<DistrictDto>> getAllDistrict();
}
