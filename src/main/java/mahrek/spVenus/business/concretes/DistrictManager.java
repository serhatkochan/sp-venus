package mahrek.spVenus.business.concretes;

import mahrek.spVenus.business.abstracts.DistrictService;
import mahrek.spVenus.core.dataAccess.DistrictDao;
import mahrek.spVenus.core.entities.District;
import mahrek.spVenus.core.entities.dtos.DistrictDto;
import mahrek.spVenus.core.utilities.results.DataResult;
import mahrek.spVenus.core.utilities.results.ErrorDataResult;
import mahrek.spVenus.core.utilities.results.SuccessDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictManager implements DistrictService {

    @Autowired
    DistrictDao districtDao;

    @Override
    public DataResult<List<DistrictDto>> getAllDistrict() {
        try {
            return new SuccessDataResult<List<DistrictDto>>(districtDao.getAllDistrictDto());
        } catch (Exception ex){
            return new ErrorDataResult<List<DistrictDto>>("Bilinmeyen Bir Hata Oluştu");
        }
    }
}
