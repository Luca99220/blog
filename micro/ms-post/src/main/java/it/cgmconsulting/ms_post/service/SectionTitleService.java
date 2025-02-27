package it.cgmconsulting.ms_post.service;

import it.cgmconsulting.ms_post.entity.SectionTitle;
import it.cgmconsulting.ms_post.exception.GenericException;
import it.cgmconsulting.ms_post.exception.ResourceNotFoundException;
import it.cgmconsulting.ms_post.repository.SectionTitleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class SectionTitleService {

    private final SectionTitleRepository sectionTitleRepository;

    public ResponseEntity<?> addSectionTitle(String sectionTitle) {
        sectionTitle = sectionTitle.toUpperCase();
        if(sectionTitleRepository.existsBySectionTitle(sectionTitle))
            throw new GenericException("This section title already exists", HttpStatus.CONFLICT);

        sectionTitleRepository.save(new SectionTitle(sectionTitle));
        return ResponseEntity.status(201).body("Section title created successfully");
    }

    public ResponseEntity<?> getAllVisibleSectionTitle(){
        return ResponseEntity.status(HttpStatus.OK).body(sectionTitleRepository.getAllVisible());
    }

    public ResponseEntity<?> getAllSectionTitle(){
        return ResponseEntity.status(HttpStatus.OK).body(sectionTitleRepository.findAll());
    }
    @Transactional
    public ResponseEntity<?>updateTitle(byte id, String newSectionTitle){
        if (sectionTitleRepository.existsBySectionTitleAndIdNot(newSectionTitle, id))
            throw new GenericException("Section title already exists ", HttpStatus.CONFLICT);
        SectionTitle st = findById(id);
        st.setSectionTitle(newSectionTitle);
        return ResponseEntity.ok(st);
    }

    protected SectionTitle findById(byte id){
        return sectionTitleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Section title", "id", id));
    }

    @Transactional
    public ResponseEntity<?> switchVisibility(byte id) {
        SectionTitle st = findById(id);
        st.setVisible(!st.isVisible());
        return ResponseEntity.ok(st);
    }
}
