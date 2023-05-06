package com.dddsample.movieproject.domain.discount.infrastructure;

import com.dddsample.movieproject.domain.discount.model.Discount;
import org.springframework.data.repository.Repository;

public interface DiscountRepository<T extends Discount, Long> extends Repository<T, Long> {
    T save(T discount);
}
