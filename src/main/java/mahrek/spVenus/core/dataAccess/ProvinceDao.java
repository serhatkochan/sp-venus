package mahrek.spVenus.core.dataAccess;

import mahrek.spVenus.core.entities.Province;
import mahrek.spVenus.core.entities.dtos.ProvinceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProvinceDao extends JpaRepository<Province, Integer> {
    @Query("Select new mahrek.spVenus.core.entities.dtos.ProvinceDto"
            + "( p.provinceName, p.provinceNo)"
            + " From Province p")
    List<ProvinceDto> getAllProvinceDto();
}
