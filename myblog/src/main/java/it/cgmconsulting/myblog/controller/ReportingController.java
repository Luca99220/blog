package it.cgmconsulting.myblog.controller;

import it.cgmconsulting.myblog.entity.enumeration.ReportingStatus;
import it.cgmconsulting.myblog.service.ReportingService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Validated

public class ReportingController {

    private final ReportingService reportingService;


    @PostMapping("/v1/reportings")
    @PreAuthorize("hasAuthority('MEMBER')")
    public ResponseEntity<String> createReport(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @Min(1) int commentId,
            @RequestParam @NotBlank @Size(min = 3, max = 30) String reason,//reason e startDate formano la pk per l'entità reason
            @RequestParam @NotNull LocalDate startDate
    ){
        return new ResponseEntity<String>(reportingService.createReport(userDetails, commentId, reason, startDate), HttpStatus.CREATED);
    }

    @PutMapping("/v1/reportings")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?>updateReport(
            @RequestParam @NotBlank @Size(min = 3, max = 30) String reason,//reason e startDate formano la pk per l'entità reason
            @RequestParam @NotNull LocalDate startDate,
            @RequestParam ReportingStatus status,
            @RequestParam @Min(1) int commentId
    ){
        return new ResponseEntity<String>(reportingService.updateReport(reason, startDate, status, commentId), HttpStatus.OK);

    }

    @GetMapping("/v1/reportings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getReports(
            @RequestParam(defaultValue = "0")int pageNumber,
            @RequestParam(defaultValue = "10")int pageSize,
            @RequestParam(defaultValue = "createdAt")String sortBy,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam ReportingStatus status
    ){
        return new ResponseEntity<>(reportingService.getReports(pageNumber, pageSize, sortBy, direction, status), HttpStatus.OK);

    }

    @GetMapping("/v1/reportings/{commentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getReportDetail(@PathVariable @Min(1) int commentId){
        return new ResponseEntity<>(reportingService.getReportDetail(commentId), HttpStatus.OK);
    }
}
