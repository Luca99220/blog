package it.cgmconsulting.banner.controller;

import it.cgmconsulting.banner.entity.Campaign;
import it.cgmconsulting.banner.entity.Company;
import it.cgmconsulting.banner.service.MainService;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    //API per chiamate esterne

    //POST/GET per entità company e campaign

    @PostMapping("/companies")
    public ResponseEntity<?>addCompany(@RequestParam @NotBlank @Size(min = 1,max = 255) String companyName){
        return ResponseEntity
                .status(201)
                .body(mainService.addCompany(companyName));
    }
    @GetMapping("/companies/{id}")
    public ResponseEntity<?> getCompany(@PathVariable int id){
        Optional<Company> company = mainService.getCompany(id);
        if (company.isPresent())
            return ResponseEntity.ok(company);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Company found");
    }

    @GetMapping("/companies")
    public ResponseEntity<?> getCompanies(
            @RequestParam(defaultValue = "0") int pageNumber, // numero di pagina da cui partire
            @RequestParam(defaultValue = "10") int pageSize, // numero di elementi per pagina
            @RequestParam(defaultValue = "companyName") String sortBy, // indica la colonna su cui eseguire l'ordinamento
            @RequestParam(defaultValue = "ASC") String direction // indica se l'ordinamento è ASC o DESC
    ){
        //elenco di company paginato
        List<Company> list = mainService.getCompanies(pageNumber, pageSize, sortBy, direction);
        if(list.isEmpty())
            return  ResponseEntity.status(404).body("no companies found");
         return  ResponseEntity.ok(list);
    }

    @PostMapping("/campaigns")
    public ResponseEntity<?> addCampaign(
            @RequestParam @NotNull @FutureOrPresent LocalDate startDate,
            @RequestParam @NotNull @FutureOrPresent LocalDate endDate,
            @RequestParam @Min(1) int companyId,
            @RequestParam @NotBlank @Size(max = 255, min=1) String product,
            @RequestParam MultipartFile file
    ){
        Map<Boolean, Object> response = mainService.addCampaign(startDate, endDate, companyId, product, file);
        if(response.containsKey(Boolean.FALSE))
            return ResponseEntity.status(400).body(response.get(Boolean.FALSE));
        return ResponseEntity.status(201).body(response.get(Boolean.TRUE));
    }

    @GetMapping("/campaigns")
    public ResponseEntity<?> getCampaigns(@RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(defaultValue = "endDate") String sortBy,
                                          @RequestParam(defaultValue = "DESC") String direction ){
        List<Campaign> list = mainService.getCampaigns(pageNumber, pageSize, sortBy, direction);
        if(list.isEmpty())
            return  ResponseEntity.status(404).body("no campaigns found");
        return  ResponseEntity.ok(list);
    }

    @GetMapping("/api/campaigns/{id}")
    public ResponseEntity<?> getBanner(@RequestHeader("Authorization") String id) {
        String banner = mainService.getBanner(id);
        if (banner != null)
            return ResponseEntity.status(HttpStatus.OK).body(banner);
        return ResponseEntity.status(404).body(null);
    }

}
