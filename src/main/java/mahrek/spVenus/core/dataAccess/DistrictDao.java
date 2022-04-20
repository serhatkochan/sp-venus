package mahrek.spVenus.core.dataAccess;

import mahrek.spVenus.core.entities.District;
import mahrek.spVenus.core.entities.dtos.DistrictDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistrictDao extends JpaRepository<District, Integer> {
    @Query("Select new mahrek.spVenus.core.entities.dtos.DistrictDto"
            + "(d.districtId, d.districtName, p.provinceName, p.provinceNo)"
            + " From District d"
            + " Inner Join d.province p")
    List<DistrictDto> getAllDistrictDto();
}
