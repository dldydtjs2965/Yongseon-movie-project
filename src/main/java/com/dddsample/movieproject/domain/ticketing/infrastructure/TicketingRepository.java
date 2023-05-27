package com.dddsample.movieproject.domain.ticketing.infrastructure;

import com.dddsample.movieproject.domain.ticketing.model.Ticketing;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface TicketingRepository extends Repository<Ticketing, Long> {

    Ticketing save(Ticketing ticketing);

    Optional<Ticketing> findById(Long ticketingId);
}
