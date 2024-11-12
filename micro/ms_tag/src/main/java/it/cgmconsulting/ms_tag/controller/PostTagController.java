package it.cgmconsulting.ms_tag.controller;

import it.cgmconsulting.ms_tag.service.PostTagService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class PostTagController {
    private final PostTagService postTagService;
    //post per associare i tags ad un post

    @PostMapping("/v2/{postId}")
    public ResponseEntity<?> addTagsToPost(
            @PathVariable @Min(1) int postId,
            @RequestParam Set<String> tags){
        return postTagService.addTagsToPost(postId, tags);
    }
    //patch per modificare i tag associati ad un post
    @PatchMapping("/v2/{postId}")
    public ResponseEntity<?> updateTagsToPost(
            @PathVariable @Min(1) int postId,
            @RequestParam Set<String> tags){
        return postTagService.updateTagsToPost(postId, tags);
    }
    //get per ottenere i tags associati ad un post
    @GetMapping("/v99/{postId}")
    public ResponseEntity<?> getTagsByPost(@PathVariable @Min(1) int postId){
        return postTagService.getTagsByPost(postId);
    }

    //endpoint richiamato da ms-post
}
