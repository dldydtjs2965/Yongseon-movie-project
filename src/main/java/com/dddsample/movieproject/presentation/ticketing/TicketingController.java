package com.dddsample.movieproject.presentation.ticketing;

import com.dddsample.movieproject.presentation.ticketing.request.TicketingRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/screening/{screeningId}/ticketing")
public class TicketingController {

    @PostMapping("")
    public ResponseEntity ticketing(@PathVariable Long screeningId, @RequestBody @Valid TicketingRequestDto ticketingRequest) {
        return ResponseEntity.ok().build();
    }
}
