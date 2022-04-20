package mahrek.spVenus.api.controllers;

import mahrek.spVenus.business.abstracts.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/districts")
public class DistrictsController {

    @Autowired
    DistrictService districtService;

    @GetMapping("")
    public ResponseEntity<?> getAllDistrict(){
        return ResponseEntity.ok(districtService.getAllDistrict());
    }

}
