package it.cgmconsulting.myblog.controller;

import it.cgmconsulting.myblog.payload.request.CommentRequest;
import it.cgmconsulting.myblog.payload.request.CommentUpdateRequest;
import it.cgmconsulting.myblog.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/v1/comments")
    @PreAuthorize("hasAuthority('MEMBER')")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest request, @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(commentService.createComment(request, userDetails), HttpStatus.CREATED);
    }

    @PatchMapping("/v1/comments")@PreAuthorize("hasAnyAuthority('MEMBER')")
    public ResponseEntity<?> updateComment(@RequestBody @Valid CommentUpdateRequest request, @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(commentService.updateComment(request, userDetails), HttpStatus.OK);}

    @DeleteMapping("/v1/comments/{id}")
    @PreAuthorize("hasAuthority('MEMBER')")
    public ResponseEntity<?>deleteComment(@PathVariable @Min(1)int id, @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(commentService.deleteComment(id, userDetails), HttpStatus.OK);
    }
    @GetMapping("/v0/comments/{postId}")
    public ResponseEntity<?> getComments(@PathVariable @Min(1)int postId){
        return new ResponseEntity<>(commentService.getComments(postId), HttpStatus.OK);
    }
    /*
    public List<CommentResponse> getComments() {
        List<Comment> comments = commentRepository.getComments(LocalDateTime.now().minusSeconds(timeToUpdate));

        return comments.stream()
                .map(c -> new CommentResponse(
                        c.getId(),
                        c.getContent(),
                        c.getUserId().getUsername(),
                        c.getCreatedAt(),
                        c.getParent() != null ? c.getParent().getId() : null))
                .collect(Collectors.toList());
     */
}
// Get List<Comment> /v0/comments
// verificare se censurati
// creati almeno 1 minuto prima della get
// ordinati per updatedAt DESC