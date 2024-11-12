package it.cgmconsulting.ms_post.controller;

import it.cgmconsulting.ms_post.entity.SectionTitle;
import it.cgmconsulting.ms_post.service.SectionTitleService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SectionTitleController {

    private final SectionTitleService sectionTitleService;

    // POST -> titolo sezione in maiuscolo (ADMIN)
    @PostMapping("/v1/section-titles")
    public ResponseEntity<?> addSectionTitle(@RequestParam @NotBlank @Size(max=50, min=2) String sectionTitle){
        return sectionTitleService.addSectionTitle(sectionTitle);
    }

    // GET ALL -> solo i visible (WRITER)
    @GetMapping("/v2/section-titles")
    public ResponseEntity<?> getAllVisibleSectionTitle(){
        return sectionTitleService.getAllVisibleSectionTitle();
    }

    // GET ALL -> visible e non visible (ADMIN)
    @GetMapping("/v1/section-titles")
    public ResponseEntity<?> getAllSectionTitle(){
        return sectionTitleService.getAllSectionTitle();
    }

    @PutMapping("/v1/section-titles/{id}")
    public ResponseEntity<?>updateTitle(
            @PathVariable @Min(1) @Max(127) byte id,
            @RequestParam @NotBlank @Size(max=50, min = 2) String newSectionTitle){
        return sectionTitleService.updateTitle(id, newSectionTitle);
    }

    @PatchMapping("/v1/section-titles/{id}")
    public ResponseEntity<?>switchVisibility(@PathVariable @Min(1) @Max(127) byte id){
        return sectionTitleService.switchVisibility(id);
    }
}
