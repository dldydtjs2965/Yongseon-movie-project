package com.dddsample.movieproject.presentation.ticketing;

import com.dddsample.movieproject.domain.ticketing.application.TicketingService;
import com.dddsample.movieproject.domain.user.service.UserDetailsProvider;
import com.dddsample.movieproject.presentation.ticketing.request.TicketingRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/screening/{screeningId}/ticketing")
public class TicketingController {

    private final TicketingService ticketingService;
    private final UserDetailsProvider userDetailsProvider;
    @PostMapping("")
    public ResponseEntity ticketing(@PathVariable Long screeningId, @RequestBody @Valid TicketingRequestDto ticketingRequest) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{ticketingId}/refund")
    public ResponseEntity refund(@PathVariable Long ticketingId, @PathVariable Long screeningId) {
        ticketingService.refund(ticketingId);
        return ResponseEntity.ok().build();
    }
}
