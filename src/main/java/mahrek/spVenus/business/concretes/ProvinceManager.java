package mahrek.spVenus.business.concretes;

import mahrek.spVenus.business.abstracts.ProvinceService;
import mahrek.spVenus.core.dataAccess.ProvinceDao;
import mahrek.spVenus.core.entities.dtos.ProvinceDto;
import mahrek.spVenus.core.utilities.results.DataResult;
import mahrek.spVenus.core.utilities.results.ErrorDataResult;
import mahrek.spVenus.core.utilities.results.SuccessDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceManager implements ProvinceService {

    @Autowired
    ProvinceDao provinceDao;

    @Override
    public DataResult<List<ProvinceDto>> getAllProvinceDto() {
        try {
            return new SuccessDataResult<List<ProvinceDto>>(provinceDao.getAllProvinceDto());
        } catch (Exception ex){
            return new ErrorDataResult<List<ProvinceDto>>("Bilinmeyen Bir Hata Oluştu");
        }
    }
}
