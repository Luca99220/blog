package it.cgmconsulting.ms_comment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private int id;
    private String comment;
    private String author;
    private LocalDateTime date;

}