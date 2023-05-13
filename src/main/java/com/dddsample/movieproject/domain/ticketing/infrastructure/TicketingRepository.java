package com.dddsample.movieproject.domain.ticketing.infrastructure;

import com.dddsample.movieproject.domain.ticketing.model.Ticketing;
import org.springframework.data.repository.Repository;

public interface TicketingRepository extends Repository<Ticketing, Long> {

    Ticketing save(Ticketing ticketing);
}
