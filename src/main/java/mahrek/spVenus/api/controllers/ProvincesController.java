package mahrek.spVenus.api.controllers;

import mahrek.spVenus.business.abstracts.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/provinces")
public class ProvincesController {

    @Autowired
    ProvinceService provinceService;

    @GetMapping("")
    public ResponseEntity<?> getAllProvince(){
        return ResponseEntity.ok(provinceService.getAllProvinceDto());
    }
}
