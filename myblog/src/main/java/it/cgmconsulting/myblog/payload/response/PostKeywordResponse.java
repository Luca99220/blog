package it.cgmconsulting.myblog.payload.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter @NoArgsConstructor @EqualsAndHashCode(callSuper = true)
public class PostKeywordResponse extends PostResponse{

    private String content;

    public PostKeywordResponse(int id, String title, String overview, String image, short totComments, double average, String content) {
        super(id, title, overview, image, totComments, average);
        this.content = content;
    }
}

