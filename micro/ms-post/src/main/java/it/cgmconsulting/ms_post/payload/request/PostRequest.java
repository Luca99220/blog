package it.cgmconsulting.ms_post.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class PostRequest {
    @NotBlank @Size(max = 100, min = 1)
    private String title;

    @Size(max = 255)
    private String postImage;

}
