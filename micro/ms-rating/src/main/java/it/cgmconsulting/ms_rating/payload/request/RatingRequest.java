package it.cgmconsulting.ms_rating.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class RatingRequest {

    @Min(1)
    private int postId;
    @Min(1) @Max(5)
    private byte rate;
}
